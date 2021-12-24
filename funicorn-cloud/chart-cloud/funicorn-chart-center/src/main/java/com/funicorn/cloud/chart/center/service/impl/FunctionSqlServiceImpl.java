package com.funicorn.cloud.chart.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.chart.center.dto.FunctionSqlDTO;
import com.funicorn.cloud.chart.center.entity.FunctionSql;
import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import com.funicorn.cloud.chart.center.exception.DatavException;
import com.funicorn.cloud.chart.center.exception.ErrorCode;
import com.funicorn.cloud.chart.center.mapper.FunctionSqlMapper;
import com.funicorn.cloud.chart.center.service.FunctionSqlService;
import com.funicorn.cloud.chart.center.service.RequestParamService;
import com.funicorn.cloud.chart.center.service.ResponsePropService;
import com.funicorn.cloud.chart.center.util.DBUtil;
import com.funicorn.cloud.chart.center.vo.FunctionSqlVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.List;
import java.util.Map;


/**
 * 业务实现类
 * @author Aimee
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FunctionSqlServiceImpl extends ServiceImpl<FunctionSqlMapper, FunctionSql> implements FunctionSqlService {

    @Resource
    private RequestParamService requestParamService;
    @Resource
    private ResponsePropService responsePropService;

    @Override
    public IPage<FunctionSqlVO> page(FunctionSqlDTO functionSqlDTO) {
        Page<FunctionSqlVO> page = new Page<>(functionSqlDTO.getCurrent()==null ? 1: functionSqlDTO.getCurrent(),
                functionSqlDTO.getSize()==null ? 10 : functionSqlDTO.getSize());
        IPage<FunctionSqlVO> iPage = baseMapper.queryPage(page,functionSqlDTO);
        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()){
            return iPage;
        }

        iPage.getRecords().forEach(functionSqlVO -> {
            //入参
            List<RequestParam> params = requestParamService.list(Wrappers.<RequestParam>lambdaQuery()
                    .eq(RequestParam::getFunctionId,functionSqlVO.getId()));
            functionSqlVO.setParams(params);
            //出参
            List<ResponseProp> props = responsePropService.list(Wrappers.<ResponseProp>lambdaQuery()
                    .eq(ResponseProp::getFunctionId,functionSqlVO.getId()));
            functionSqlVO.setProps(props);
        });
        return iPage;
    }

    @Override
    public List<Map<String,Object>> testSql(FunctionSqlDTO functionSqlDTO) {
        Connection connection = DBUtil.getConnection(functionSqlDTO.getDatasourceId());
        if (connection==null){
            throw new DatavException(ErrorCode.SQL_CONNECTION_FAIL);
        }
        return DBUtil.selectObj(connection,functionSqlDTO.getSqlContent(),functionSqlDTO.getProps());
    }

    @Override
    public FunctionSqlDTO add(FunctionSqlDTO functionSqlDTO) {
        FunctionSql functionSql = JsonUtil.object2Object(functionSqlDTO,FunctionSql.class);
        baseMapper.insert(functionSql);
        //入参
        if (functionSqlDTO.getParams()!=null && !functionSqlDTO.getParams().isEmpty()){
            functionSqlDTO.getParams().forEach(requestParam -> requestParam.setFunctionId(functionSql.getId()));
            requestParamService.saveBatch(functionSqlDTO.getParams());
        }
        //出参
        if (functionSqlDTO.getProps()!=null && !functionSqlDTO.getProps().isEmpty()){
            functionSqlDTO.getProps().forEach(responseProp -> responseProp.setFunctionId(functionSql.getId()));
            responsePropService.saveBatch(functionSqlDTO.getProps());
        }

        functionSqlDTO.setId(functionSql.getId());
        return functionSqlDTO;
    }

    @Override
    public void update(FunctionSqlDTO functionSqlDTO) {
        FunctionSql functionSql = JsonUtil.object2Object(functionSqlDTO,FunctionSql.class);
        baseMapper.updateById(functionSql);

        //入参
        requestParamService.remove(Wrappers.<RequestParam>lambdaQuery().eq(RequestParam::getFunctionId,functionSql.getId()));
        if (functionSqlDTO.getParams()!=null && !functionSqlDTO.getParams().isEmpty()){
            functionSqlDTO.getParams().forEach(requestParam -> requestParam.setFunctionId(functionSql.getId()));
            requestParamService.saveBatch(functionSqlDTO.getParams());
        }

        //出参
        responsePropService.remove(Wrappers.<ResponseProp>lambdaQuery().eq(ResponseProp::getFunctionId,functionSql.getId()));
        if (functionSqlDTO.getProps()!=null && !functionSqlDTO.getProps().isEmpty()){
            functionSqlDTO.getProps().forEach(responseProp -> responseProp.setFunctionId(functionSql.getId()));
            responsePropService.saveBatch(functionSqlDTO.getProps());
        }
    }
}