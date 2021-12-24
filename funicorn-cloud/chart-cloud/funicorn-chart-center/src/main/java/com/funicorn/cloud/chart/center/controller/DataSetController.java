package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.cloud.chart.center.constant.DataSetType;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.DataSetDTO;
import com.funicorn.cloud.chart.center.entity.DataSet;
import com.funicorn.cloud.chart.center.entity.FunctionHttp;
import com.funicorn.cloud.chart.center.entity.FunctionSql;
import com.funicorn.cloud.chart.center.service.DataSetService;
import com.funicorn.cloud.chart.center.service.FunctionHttpService;
import com.funicorn.cloud.chart.center.service.FunctionSqlService;
import com.funicorn.cloud.chart.center.vo.DataSetVO;
import com.funicorn.cloud.chart.center.vo.FunctionVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据集配置
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@RequestMapping("/DataSet")
public class DataSetController {

    @Resource
    private DataSetService dataSetService;
    @Resource
    private FunctionSqlService functionSqlService;
    @Resource
    private FunctionHttpService functionHttpService;

    /**
     * 查询所有数据集
     * @return Result
     */
    @GetMapping("/list")
    public Result<List<DataSet>> list() {
        return Result.ok(dataSetService.list(Wrappers.<DataSet>lambdaQuery().eq(DataSet::getIsDelete, ChartConstant.NOT_DELETED)));
    }

    /**
     * 根据类型查询所有函数
     * @param type 类型 SQL/JSON/HTTP
     * @return Result
     */
    @GetMapping("/getFunction")
    public Result<List<FunctionVO>> getFunction(@RequestParam String type){
        List<FunctionVO> list = new ArrayList<>();
        if (DataSetType.SQL.getType().equals(type)){
            List<FunctionSql> functionSqlList = functionSqlService.list();
            if (functionSqlList!=null && !functionSqlList.isEmpty()){
                functionSqlList.forEach(functionSql -> {
                    FunctionVO functionVO = new FunctionVO();
                    functionVO.setId(functionSql.getId());
                    functionVO.setName(functionSql.getName());
                    list.add(functionVO);
                });
            }
        }else if (DataSetType.HTTP.getType().equals(type)){
            List<FunctionHttp> functionHttpList = functionHttpService.list();
            if (functionHttpList!=null && !functionHttpList.isEmpty()){
                functionHttpList.forEach(functionSql -> {
                    FunctionVO functionVO = new FunctionVO();
                    functionVO.setId(functionSql.getId());
                    functionVO.setName(functionSql.getName());
                    list.add(functionVO);
                });
            }
        }
        return Result.ok(list);
    }

    /**
     * 分页查询
     * @param dataSetDTO 参数
     * @return Result
     */
    @GetMapping("/page")
    public Result<IPage<DataSetVO>> page(DataSetDTO dataSetDTO) {
        IPage<DataSetVO> iPage = dataSetService.getPage(dataSetDTO);
        return Result.ok(iPage);
    }

    /**
     * 查询数据集类型
     * @return Result
     */
    @GetMapping("/getDataSetType")
    public Result<List<Map<String, String>>> getDataSetType(){
        return Result.ok(DataSetType.typeList);
    }

    /**
     * 新增数据集
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> save(@RequestBody DataSetDTO dataSetDTO) {
        dataSetService.saveDataSet(dataSetDTO);
        return Result.ok("新增成功");
    }

    /**
     * 修改数据集
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> update(@RequestBody DataSetDTO dataSetDTO) {
        dataSetService.updateDataSet(dataSetDTO);
        return Result.ok("已修改");
    }

    /**
     * 通过id删除
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result<?> removeById(@PathVariable String id) {
        dataSetService.updateById(DataSet.builder().id(id).isDelete("1").build());
        return Result.ok("已删除");
    }
}
