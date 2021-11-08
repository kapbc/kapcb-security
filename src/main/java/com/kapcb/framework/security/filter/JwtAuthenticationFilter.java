package com.kapcb.framework.security.filter;

import com.kapcb.framework.security.util.JwtTokenUtil;
import com.kapcb.framework.web.enums.ResultCode;
import com.kapcb.framework.web.exception.BusinessException;
import com.kapcb.framework.web.model.result.CommonResult;
import kapcb.framework.web.constants.enums.StringPool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: JwtAuthenticationFilter </a>
 * <a>Author: Kapcb <a>
 * <a>Description: JwtAuthenticationFilter <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:20
 */
public abstract class JwtAuthenticationFilter extends OncePerRequestFilter {

    public abstract UserDetailsService getUserDetailService();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader(StringPool.HTTP_REQUEST_AUTHORIZATION.value());

        if (StringUtils.isNoneBlank(authorization) && authorization.startsWith(StringPool.AUTHORIZATION_BEARER.value())) {
            String accessToken = authorization.substring(StringPool.AUTHORIZATION_BEARER.value().length());
            String username = JwtTokenUtil.getUsername(accessToken);
            if (StringUtils.isBlank(accessToken) && StringUtils.isBlank(username)) {
                throw new BusinessException(ResultCode.FAILED);
            }
            UserDetails userDetails = getUserDetailService().loadUserByUsername(username);
            if (JwtTokenUtil.validateToken(accessToken, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
