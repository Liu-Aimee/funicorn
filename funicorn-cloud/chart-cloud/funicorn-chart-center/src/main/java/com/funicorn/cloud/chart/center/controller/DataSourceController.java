package com.funicorn.cloud.chart.center.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.funicorn.basic.common.base.model.Result;
import com.funicorn.basic.common.security.util.SecurityUtil;
import com.funicorn.cloud.chart.center.constant.ChartConstant;
import com.funicorn.cloud.chart.center.dto.DataSourcePageDTO;
import com.funicorn.cloud.chart.center.entity.DataSource;
import com.funicorn.cloud.chart.center.service.DataSourceService;
import com.funicorn.cloud.chart.center.util.DBUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Connection;
import java.util.List;


/**
 * 数据源配置
 * @author Aimee
 * @since 2021/12/03
 */
@RestController
@AllArgsConstructor
@RequestMapping("/DataSource")
public class DataSourceController {

    @Resource
    private final DataSourceService dataSourceService;

    /**
     * 分页查询
     * @param dataSourcePageDTO 入参
     * @return Result
     */
    @GetMapping("/page")
    public Result<?> page(DataSourcePageDTO dataSourcePageDTO) {
        LambdaQueryWrapper<DataSource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DataSource::getCreatedBy, SecurityUtil.getCurrentUser().getUsername());
        queryWrapper.eq(DataSource::getIsDelete,ChartConstant.NOT_DELETED);
        if (StringUtils.isNotBlank(dataSourcePageDTO.getTitle())) {
            queryWrapper.like(DataSource::getTitle,dataSourcePageDTO.getTitle());
        }
        IPage<DataSource> pages = dataSourceService.page(new Page<>(dataSourcePageDTO.getCurrent(),dataSourcePageDTO.getSize()),queryWrapper);
        return Result.ok(pages);
    }

    /**
     * 测试连接
     * @return Result
     */
    @PostMapping("/testConnect")
    public Result<?> testConnect(@RequestBody DataSource dataSource){
        dataSourceService.testConnect(dataSource);
        return Result.ok();
    }

    /**
     * 查询数据库所有表
     * @return Result
     */
    @GetMapping(value = "/QueryTables/{dsId}")
    public Result<?> queryTables(@PathVariable Long dsId) {

        DataSource datavDs = dataSourceService.getById(dsId);
        if (datavDs!=null){
            Connection connection = DBUtil.getConnection(datavDs.getId());
            if (connection!=null){
                List<String> tables = DBUtil.getAllTables(connection);
                return Result.ok(tables);
            }
        }
        return Result.ok();
    }

    /**
     * 查询数据库表有字段
     * @return Result
     */
    @GetMapping(value = "/getColumnByTableName")
    public Result<?> getColumnByTableName(@RequestParam("dsId") Long dsId,@RequestParam("tableName") String tableName) {

        DataSource datavDs = dataSourceService.getById(dsId);
        if (datavDs!=null){
            Connection connection = DBUtil.getConnection(datavDs.getId());
            if (connection!=null){
                List<String> tables = DBUtil.getColumnByTableName(connection,tableName);
                return Result.ok(tables);
            }
        }
        return Result.ok();
    }

    /**
     * 新增数据源配置
     * @return Result
     */
    @PostMapping("/add")
    public Result<?> save(@RequestBody DataSource dataSource) {
        dataSourceService.saveDataSource(dataSource);
        return Result.ok(dataSource);
    }

    /**
     * 修改数据源配置
     * @return Result
     */
    @PostMapping("/update")
    public Result<?> updateById(@RequestBody DataSource dataSource) {
        dataSourceService.updateById(dataSource);
        return Result.ok();
    }

    /**
     * 通过id删除数据源配置
     * @param id 数据源id
     * @return Result
     */
    @DeleteMapping("/{id}")
    public Result<?> removeById(@PathVariable String id) {
        DataSource dataSource = new DataSource();
        dataSource.setId(id);
        dataSource.setIsDelete(ChartConstant.IS_DELETED);
        dataSourceService.updateById(dataSource);
        return Result.ok();
    }

}
