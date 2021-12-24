package com.funicorn.cloud.chart.center.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.funicorn.basic.common.base.model.HttpReqData;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.base.util.HttpUtil;
import com.funicorn.basic.common.base.util.JsonUtil;
import com.funicorn.cloud.chart.center.dto.FunctionHttpDTO;
import com.funicorn.cloud.chart.center.entity.FunctionHttp;
import com.funicorn.cloud.chart.center.entity.RequestParam;
import com.funicorn.cloud.chart.center.entity.ResponseProp;
import com.funicorn.cloud.chart.center.exception.DatavException;
import com.funicorn.cloud.chart.center.exception.ErrorCode;
import com.funicorn.cloud.chart.center.mapper.FunctionHttpMapper;
import com.funicorn.cloud.chart.center.service.FunctionHttpService;
import com.funicorn.cloud.chart.center.service.RequestParamService;
import com.funicorn.cloud.chart.center.service.ResponsePropService;
import com.funicorn.cloud.chart.center.vo.FunctionHttpVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口函数表 服务实现类
 * </p>
 *
 * @author Aimee
 * @since 2021-09-06
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FunctionHttpServiceImpl extends ServiceImpl<FunctionHttpMapper, FunctionHttp> implements FunctionHttpService {

    @Resource
    private RequestParamService requestParamService;
    @Resource
    private ResponsePropService responsePropService;

    @Override
    public IPage<FunctionHttpVO> page(FunctionHttpDTO functionHttpDTO) {
        IPage<FunctionHttpVO> iPage = baseMapper.queryPage(new Page<>(functionHttpDTO.getCurrent(),functionHttpDTO.getSize()),functionHttpDTO);

        if (iPage==null || iPage.getRecords()==null || iPage.getRecords().isEmpty()){
            return iPage;
        }

        iPage.getRecords().forEach(functionHttpVO -> {
            //入参
            List<RequestParam> params = requestParamService.list(Wrappers.<RequestParam>lambdaQuery()
                    .eq(RequestParam::getFunctionId,functionHttpVO.getId()));
            functionHttpVO.setParams(params);
            //出参
            List<ResponseProp> props = responsePropService.list(Wrappers.<ResponseProp>lambdaQuery()
                    .eq(ResponseProp::getFunctionId,functionHttpVO.getId()));
            functionHttpVO.setProps(props);
        });
        return iPage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> testHttp(FunctionHttpDTO functionHttpDTO) {
        Map<String, Object> params = new HashMap<>(8);
        if (functionHttpDTO.getParams()!=null && !functionHttpDTO.getParams().isEmpty()){
            functionHttpDTO.getParams().forEach(requestParam -> params.put(requestParam.getName(),requestParam.getDefaultValue()));
        }

        HttpReqData requestData = new HttpReqData();
        requestData.setConnectTimeout(functionHttpDTO.getConnectTimeOut());
        requestData.setReadTimeout(functionHttpDTO.getReadTimeOut());
        requestData.setRequestMethod(functionHttpDTO.getMethod());
        requestData.setUrl(functionHttpDTO.getUrl());
        requestData.setParam(params);
        requestData.setHeaderMap(HttpUtil.getFireFoxHeader());

        try {
            Result<?> responseData = HttpUtil.doRequest(requestData, Result.class);
            if (responseData==null || !responseData.isSuccess()){
                throw new DatavException(ErrorCode.REQUEST_FAILED,
                        responseData==null || StringUtils.isBlank(responseData.getMessage()) ? "无结果集返回!" : responseData.getMessage());
            }
            if (responseData.getData()!=null){
                List<Map<String, Object>> result = new ArrayList<>();
                List<Map<String, Object>> responseResult = (List<Map<String, Object>>) responseData.getData();
                responseResult.forEach(data->{
                    Map<String, Object> resultMap = new HashMap<>(functionHttpDTO.getProps().size());
                    functionHttpDTO.getProps().forEach(responseProp -> resultMap.put(responseProp.getName(),data.get(responseProp.getName())));
                    result.add(resultMap);
                });

                return result;
            }else {
                throw new DatavException(ErrorCode.REQUEST_FAILED, "无结果集返回!");
            }
        } catch (Exception e) {
            throw new DatavException(ErrorCode.REQUEST_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public void addFunctionHttp(FunctionHttpDTO functionHttpDTO) {
        FunctionHttp functionHttp = JsonUtil.object2Object(functionHttpDTO,FunctionHttp.class);
        baseMapper.insert(functionHttp);

        //入参
        if (functionHttpDTO.getParams()!=null && !functionHttpDTO.getParams().isEmpty()){
            functionHttpDTO.getParams().forEach(requestParam -> requestParam.setFunctionId(functionHttp.getId()));
            requestParamService.saveBatch(functionHttpDTO.getParams());
        }
        //出参
        if (functionHttpDTO.getProps()!=null && !functionHttpDTO.getProps().isEmpty()){
            functionHttpDTO.getProps().forEach(responseProp -> responseProp.setFunctionId(functionHttp.getId()));
            responsePropService.saveBatch(functionHttpDTO.getProps());
        }
    }

    @Override
    public void updateFunctionHttp(FunctionHttpDTO functionHttpDTO) {
        FunctionHttp functionHttp = JsonUtil.object2Object(functionHttpDTO,FunctionHttp.class);
        baseMapper.updateById(functionHttp);

        //入参
        requestParamService.remove(Wrappers.<RequestParam>lambdaQuery().eq(RequestParam::getFunctionId,functionHttp.getId()));
        if (functionHttpDTO.getParams()!=null && !functionHttpDTO.getParams().isEmpty()){
            functionHttpDTO.getParams().forEach(requestParam -> requestParam.setFunctionId(functionHttp.getId()));
            requestParamService.saveBatch(functionHttpDTO.getParams());
        }

        //出参
        responsePropService.remove(Wrappers.<ResponseProp>lambdaQuery().eq(ResponseProp::getFunctionId,functionHttp.getId()));
        if (functionHttpDTO.getProps()!=null && !functionHttpDTO.getProps().isEmpty()){
            functionHttpDTO.getProps().forEach(responseProp -> responseProp.setFunctionId(functionHttp.getId()));
            responsePropService.saveBatch(functionHttpDTO.getProps());
        }
    }
}
