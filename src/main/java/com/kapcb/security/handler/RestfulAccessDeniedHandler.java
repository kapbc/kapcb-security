package com.kapcb.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

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
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {

    }
}
