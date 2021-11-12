package com.kapcb.framework.security.handler;

import com.kapcb.framework.common.constants.enums.ResultCode;
import com.kapcb.framework.common.result.CommonResult;
import com.kapcb.framework.web.util.ResponseUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: LoginAuthenticationFailureHandler </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LoginAuthenticationFailureHandler <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:24
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message = "authentication fail, please connect with administrator!";
        if (e instanceof BadCredentialsException) {
            message = "username or password error!";
        }
        if (e instanceof LockedException) {
            message = "access account is locked!";
        }
        ResponseUtil.setUpJSONResponse(httpServletResponse, CommonResult.authenticationFailure(message));
    }

}
