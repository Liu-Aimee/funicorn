package com.funicorn.cloud.task.center.config;

import com.funicorn.basic.common.security.util.SecurityUtil;
import org.flowable.ui.common.tenant.TenantProvider;

/**
 * @author Aimee
 * @since 2021/12/13 14:18
 */
public class CustomizeTenantProvider implements TenantProvider {

    @Override
    public String getTenantId() {
        return SecurityUtil.getCurrentUser().getTenantId();
    }
}
