package com.funicorn.cloud.upms.center.vo;

import com.funicorn.cloud.upms.center.entity.App;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Aimee
 * @since 2022/3/29 9:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppVO extends App {

    /**
     * 0:已禁用 1:已启用 2:申请开通中 3:拒绝开通
     * */
    private Integer tenantStatus;

    /**
     * 待审批数量
     * */
    private Integer applyCount;
}
