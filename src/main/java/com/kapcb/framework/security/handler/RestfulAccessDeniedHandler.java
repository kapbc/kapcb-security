package com.kapcb.framework.security.handler;

import com.kapcb.framework.common.result.CommonResult;
import com.kapcb.framework.security.exception.ValidateCodeException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: RestfulAccessDeniedHandler </a>
 * <a>Author: Kapcb <a>
 * <a>Description: RestfulAccessDeniedHandler <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:24
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResponseUtil.setUpJSONResponse(httpServletResponse, CommonResult.unauthorized());
    }
}
