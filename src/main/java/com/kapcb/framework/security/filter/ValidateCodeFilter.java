package com.kapcb.framework.security.filter;

import com.kapcb.framework.common.result.CommonResult;
import com.kapcb.framework.security.exception.ValidateCodeException;
import com.kapcb.framework.security.validation.IValidateCodeService;
import com.kapcb.framework.web.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: ValidateCodeFilter </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ValidateCodeFilter <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/13 14:19
 */
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    private static AntPathRequestMatcher requestMatcher;

    @Resource
    private IValidateCodeService validateCodeService;

    @PostConstruct
    void init() {
        requestMatcher = new AntPathRequestMatcher("access_token", HttpMethod.POST.name());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (requestMatcher.matches(httpServletRequest) && StringUtils.equals(httpServletRequest.getParameter(GRANT_TYPE), PASSWORD)) {
            try {
                validateCode(httpServletRequest);
            } catch (Exception e) {
                log.error("validate code error, error message is : {}", e.getMessage());
                ResponseUtil.setUpJSONResponse(httpServletResponse, CommonResult.validateFailed(e.getMessage()));
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void validateCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        validateCodeService.verify(httpServletRequest.getParameter("key"), httpServletRequest.getParameter("code"));
    }
}
