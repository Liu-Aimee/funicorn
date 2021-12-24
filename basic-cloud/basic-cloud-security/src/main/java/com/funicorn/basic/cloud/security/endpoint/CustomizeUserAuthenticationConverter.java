package com.funicorn.basic.cloud.security.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @author Aimee
 * @since 2021/11/10 15:22
 * @deprecated
 */
@Slf4j
public class CustomizeUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

    /**
     * 用户信息key
     * */
    private static final String USER_INFO_KEY = "currentUser";
    /**
     * 默认权限
     * */
    private Collection<? extends GrantedAuthority> defaultAuthorities;
    /**
     * 用户查询服务
     * */
    private UserDetailsService userDetailsService;

    /**
     * 设置用户查询服务
     * */
    public CustomizeUserAuthenticationConverter userDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        super.setUserDetailsService(userDetailsService);
        return this;
    }

    @Override
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                .arrayToCommaDelimitedString(defaultAuthorities));
        super.setDefaultAuthorities(defaultAuthorities);
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        log.info("{}",map);
        if (map.containsKey(USER_INFO_KEY)) {
            Object principal = map.get(USER_INFO_KEY);
            log.info(principal.toString());
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            if (userDetailsService != null) {
                UserDetails user = userDetailsService.loadUserByUsername((String) map.get(USER_INFO_KEY));
                authorities = user.getAuthorities();
                principal = user;
            }
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return null;
    }

    /**
     * 获取权限
     * @param map 入参
     * @return 权限数组
     * */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
