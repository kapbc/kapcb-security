package com.kapcb.framework.security.filter;

import com.alibaba.fastjson.JSON;
import com.kapcb.framework.common.constants.enums.ResultCode;
import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.common.util.JsonUtil;
import com.kapcb.framework.security.model.AuthenticationModel;
import com.kapcb.framework.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Objects;

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
@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            log.info("request method must be post request");
            throw new BusinessException(ResultCode.FAILED);
        }
        if (MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType())) {
            AuthenticationModel authenticationModel;
            try (InputStream inputStream = request.getInputStream()) {
                authenticationModel = JsonUtil.readValue(inputStream, AuthenticationModel.class);
            } catch (Exception e) {
                log.error("get username and password from request's input stream error, error message is : {}", e.getMessage());
                throw new BusinessException(ResultCode.FAILED);
            }
            if (Objects.isNull(authenticationModel) || StringUtils.isBlank(authenticationModel.getUsername()) || StringUtils.isBlank(authenticationModel.getPassword())) {
                log.error("authentication model is null. username or password is null");
                throw new BusinessException(ResultCode.FAILED);
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationModel.getUsername().trim(), authenticationModel.getPassword().trim());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
