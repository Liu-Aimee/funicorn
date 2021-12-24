package com.funicorn.cloud.chart.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.model.HttpReqData;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.HttpUtil;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.chart.center.constant.DataSetType;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.DataSetDTO;
import com.funicorn.cloud.chart.center.entity.*;
import com.funicorn.cloud.chart.center.exception.DatavException;
import com.funicorn.cloud.chart.center.exception.ErrorCode;
import com.funicorn.cloud.chart.center.mapper.DataSetMapper;
import com.funicorn.cloud.chart.center.mapper.DataSourceMapper;
import com.funicorn.cloud.chart.center.service.*;
import com.funicorn.cloud.chart.center.util.DBUtil;
import com.funicorn.cloud.chart.center.vo.DataSetVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务实现类
 * @author Aimee
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DataSetServiceImpl extends ServiceImpl<DataSetMapper, DataSet> implements DataSetService {

    @Resource
    private FunctionSqlService functionSqlService;
    @Resource
    private RequestParamService requestParamService;
    @Resource
    private ResponsePropService responsePropService;
    @Resource
    private DataSourceMapper dataSourceMapper;
    @Resource
    private FunctionHttpService functionHttpService;

    @Override
    public DataSet saveDataSet(DataSetDTO dataSetDTO) {

        //保存数据集
        DataSet dataSet = JsonUtil.object2Object(dataSetDTO,DataSet.class);
        baseMapper.insert(dataSet);
        return dataSet;
    }

    /**
     * TODO 待完善
     * */
    @Override
    public void updateDataSet(DataSetDTO dataSetDto) {
        DataSet datavDataSet = JsonUtil.object2Object(dataSetDto,DataSet.class);
        baseMapper.updateById(datavDataSet);
    }

    @Override
    public IPage<DataSetVO> getPage(DataSetDTO dataSetDTO) {
        Page<DataSetVO> page = new Page<>(dataSetDTO.getCurrent(),dataSetDTO.getSize());

        IPage<DataSetVO> iPage = baseMapper.queryPage(page,dataSetDTO);
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()){
            return iPage;
        }

        iPage.getRecords().forEach(dataSetVO -> {
            if (DataSetType.SQL.getType().equals(dataSetVO.getType())){
                FunctionSql functionSql = functionSqlService.getOne(Wrappers.<FunctionSql>lambdaQuery()
                        .eq(FunctionSql::getId,dataSetVO.getFunctionId()).eq(FunctionSql::getIsDelete, ChartConstant.NOT_DELETED));
                dataSetVO.setFunctionSql(functionSql);
            }

            //入参
            List<RequestParam> requestParams = requestParamService.list(Wrappers.<RequestParam>lambdaQuery()
                    .eq(RequestParam::getFunctionId,dataSetVO.getFunctionId()).eq(RequestParam::getIsDelete, ChartConstant.NOT_DELETED));
            dataSetVO.setParams(requestParams);
            //出参
            List<ResponseProp> responseProps = responsePropService.list(Wrappers.<ResponseProp>lambdaQuery()
                    .eq(ResponseProp::getFunctionId,dataSetVO.getFunctionId()).eq(ResponseProp::getIsDelete, ChartConstant.NOT_DELETED));
            dataSetVO.setProps(responseProps);

        });
        return iPage;
    }

    @SuppressWarnings("all")
    @Override
    public List<Map<String, Object>> getResponseProps(DataSetDTO dataSetDTO) {

        Map<String, Object> params = new HashMap<>(8);
        //入参
        List<RequestParam> requestParamList = requestParamService.list(Wrappers.<RequestParam>lambdaQuery()
                .eq(RequestParam::getFunctionId,dataSetDTO.getFunctionId()).eq(RequestParam::getIsDelete, ChartConstant.NOT_DELETED));
        if (requestParamList!=null && !requestParamList.isEmpty()){
            Map<String, Object> paramMap = new HashMap<>();
            if(StringUtils.isNotBlank(dataSetDTO.getParams())){
                paramMap = JsonUtil.json2Object(dataSetDTO.getParams(),Map.class);
            }

            for (RequestParam requestParam:requestParamList) {
                if (paramMap.containsKey(requestParam.getName())){
                    params.put(requestParam.getName(),paramMap.get(requestParam.getName()));
                }else {
                    params.put(requestParam.getName(),requestParam.getDefaultValue());
                }
            }
        }

        //出参
        List<ResponseProp> props = responsePropService.list(Wrappers.<ResponseProp>query().lambda().eq(ResponseProp::getFunctionId,dataSetDTO.getFunctionId()));

        if (DataSetType.SQL.getType().equals(dataSetDTO.getType())){
            FunctionSql functionSql = functionSqlService.getOne(Wrappers.<FunctionSql>query().lambda()
                    .eq(FunctionSql::getId,dataSetDTO.getFunctionId()).eq(FunctionSql::getIsDelete, ChartConstant.NOT_DELETED));
            if (functionSql==null){
                throw new DatavException(ErrorCode.NOT_FOUND_FUNCTION,"type:" + dataSetDTO.getType() + " id:" + dataSetDTO.getFunctionId());
            }

            DataSource dataSource = dataSourceMapper.selectById(functionSql.getDatasourceId());
            if (dataSource==null || ChartConstant.IS_DELETED.equals(dataSource.getIsDelete())){
                throw new DatavException(ErrorCode.NOT_FOUND_DATA_SOURCE,functionSql.getDatasourceId());
            }
            Connection connection = DBUtil.getConnection(dataSource.getId());
            if (connection==null){
                throw new DatavException(ErrorCode.SQL_CONNECTION_FAIL);
            }

            return DBUtil.selectObj(connection, functionSql.getSqlContent(),props);
        }else if (DataSetType.HTTP.getType().equals(dataSetDTO.getType())){
            FunctionHttp functionHttp = functionHttpService.getOne(Wrappers.<FunctionHttp>lambdaQuery()
                    .eq(FunctionHttp::getId,dataSetDTO.getFunctionId()).eq(FunctionHttp::getIsDelete, ChartConstant.NOT_DELETED));
            if (functionHttp==null){
                throw new DatavException(ErrorCode.NOT_FOUND_FUNCTION,"type:" + dataSetDTO.getType() + " id:" + dataSetDTO.getFunctionId());
            }

            HttpReqData requestData = new HttpReqData();
            requestData.setConnectTimeout(functionHttp.getConnectTimeOut());
            requestData.setReadTimeout(functionHttp.getReadTimeOut());
            requestData.setRequestMethod(functionHttp.getMethod());
            requestData.setUrl(functionHttp.getUrl());
            requestData.setParam(params);
            requestData.setHeaderMap(HttpUtil.getFireFoxHeader());

            try {
                Result responseData = HttpUtil.doRequest(requestData, Result.class);
                if (responseData==null || !responseData.isSuccess()){
                    throw new DatavException(ErrorCode.REQUEST_FAILED,
                            responseData==null || StringUtils.isBlank(responseData.getMessage()) ? "无结果集返回!" : responseData.getMessage());
                }
                return (List<Map<String, Object>>) responseData.getData();
            } catch (Exception e) {
                throw new DatavException(ErrorCode.REQUEST_EXCEPTION, e.getMessage());
            }
        }

        return null;
    }
}