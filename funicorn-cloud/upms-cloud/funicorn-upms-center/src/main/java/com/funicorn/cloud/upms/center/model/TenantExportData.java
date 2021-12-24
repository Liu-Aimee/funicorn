package com.funicorn.cloud.upms.center.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author Aimee
 * @since 2021/11/15 17:09
 */
@Data
public class TenantExportData {

    @ExcelProperty(index = 0,value = "租户id")
    private String id;

    @ExcelProperty(index = 1,value = "租户名称")
    private String tenantName;
}
