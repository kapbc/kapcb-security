package com.kapcb.framework.security.handler;

import com.kapcb.framework.web.model.result.CommonResult;
import com.kapcb.framework.web.util.ResponseUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: KapcbLogoutSuccessHandler </a>
 * <a>Author: Kapcb <a>
 * <a>Description: KapcbLogoutSuccessHandler <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:24
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ResponseUtil.setUpJSONResponse(httpServletResponse, CommonResult.success());
    }
}
