package com.kapcb.framework.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kapcb.framework.web.enums.ResultCode;
import com.kapcb.framework.web.exception.BusinessException;
import kapcb.framework.web.constants.enums.StringPool;
import kapcb.framework.web.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * <a>Title: CustomAuthenticationFilter </a>
 * <a>Author: Kapcb <a>
 * <a>Description: CustomAuthenticationFilter <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:25
 */
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            log.info("request method must be post request");
            throw new BusinessException(ResultCode.FAILED);
        }
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType())) {
            String username = StringPool.EMPTY_STRING.value();
            String password = StringPool.EMPTY_STRING.value();

            try (InputStream inputStream = request.getInputStream()) {

            } catch (Exception e) {
                log.error("get username and password from request's input stream error, error message is : {}", e.getMessage());
                throw new BusinessException(ResultCode.FAILED);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username.trim(), password.trim());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
