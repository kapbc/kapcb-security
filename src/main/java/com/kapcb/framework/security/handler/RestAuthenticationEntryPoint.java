package com.kapcb.framework.security.handler;

import com.kapcb.framework.common.result.CommonResult;
import com.kapcb.framework.web.util.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: RestAuthenticationEntryPoint </a>
 * <a>Author: Kapcb <a>
 * <a>Description: RestAuthenticationEntryPoint <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:25
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.setUpJSONResponse(httpServletResponse, CommonResult.forbidden());
    }
}
