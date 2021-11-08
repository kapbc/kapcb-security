package com.kapcb.framework.security.handler;

import com.kapcb.framework.security.util.JwtTokenUtil;
import com.kapcb.framework.web.model.result.CommonResult;
import com.kapcb.framework.web.util.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: LoginAuthenticationSuccessHandler </a>
 * <a>Author: Kapcb <a>
 * <a>Description: LoginAuthenticationSuccessHandler <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:24
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = JwtTokenUtil.generateToke(userDetails.getUsername());
//        String refreshToken = "";
        ResponseUtil.setUpJSONResponse(httpServletResponse, CommonResult.success(accessToken));
    }
}