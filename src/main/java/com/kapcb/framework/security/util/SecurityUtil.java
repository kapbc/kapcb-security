package com.kapcb.framework.security.util;

import cn.hutool.json.JSONObject;
import com.kapcb.framework.common.constants.enums.ResultCode;
import com.kapcb.framework.security.exception.SecurityException;
import com.kapcb.framework.web.context.ApplicationContextHolder;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <a>Title: SecurityUtil </a>
 * <a>Author: Kapcb <a>
 * <a>Description: SecurityUtil <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/19 22:08
 */
@Slf4j
@UtilityClass
public class SecurityUtil {

    private static volatile UserDetailsService userDetailsService;

    public static UserDetails getUserDetail() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new SecurityException(ResultCode.LOGIN_STATUS_EXPIRED);
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            if (Objects.isNull(userDetailsService)) {
                userDetailsService = ApplicationContextHolder.getBean(UserDetailsService.class);
            }
            if (Objects.nonNull(userDetails)) {
                return userDetailsService.loadUserByUsername(userDetails.getUsername());
            }
            throw new SecurityException(ResultCode.CAN_NOT_FOUND_LOGIN_INFO);
        }
        throw new SecurityException(ResultCode.CAN_NOT_FOUND_LOGIN_INFO);
    }

    public static String getUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.isNull(authentication)) {
            throw new SecurityException(ResultCode.LOGIN_STATUS_EXPIRED);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = null;
        if (Objects.nonNull(userDetails)) {
            username = userDetails.getUsername();
        }
        return username;
    }

    public static Long getUserId() {
        Object obj = getUserDetail();
        JSONObject jsonObject = new JSONObject(obj);
        return jsonObject.get("userId", Long.class);
    }

    public static Boolean checkPermission(String... permissions) {
        boolean result = false;
        if (ArrayUtils.isNotEmpty(permissions)) {
            // ?????????????????????????????????
            Collection<? extends GrantedAuthority> authorities = getUserDetail().getAuthorities();
            if (CollectionUtils.isNotEmpty(authorities)) {
                List<String> permissionEls = authorities.stream().map(GrantedAuthority::getAuthority).distinct().collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(permissionEls)) {
                    result = permissionEls.contains("admin") || Arrays.stream(permissions).allMatch(permissionEls::contains);
                }
            }
        }
        return result;
    }

}
