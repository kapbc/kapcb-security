package com.kapcb.framework.security.filter;

import com.kapcb.framework.common.constants.enums.ResultCode;
import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.common.util.JwtTokenUtil;
import com.kapcb.framework.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * <a>Title: JwtAuthenticationFilter </a>
 * <a>Author: Kapcb <a>
 * <a>Description: JwtAuthenticationFilter <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:20
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorization) && authorization.startsWith(StringPool.AUTHORIZATION_BEARER.value())) {
            String accessToken = authorization.substring(StringPool.AUTHORIZATION_BEARER.value().length());
            if (StringUtils.isBlank(accessToken) && StringUtils.isBlank(JwtTokenUtil.getUsername(accessToken))) {
                log.error("access token or username is null or empty, access token is : {}", accessToken);
                throw new BusinessException(ResultCode.FAILED);
            }
            if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(JwtTokenUtil.getUsername(accessToken));
                if (Objects.nonNull(userDetails) && JwtTokenUtil.validateToken(accessToken, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    
}
