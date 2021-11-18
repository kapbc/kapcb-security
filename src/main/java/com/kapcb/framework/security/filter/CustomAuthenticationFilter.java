package com.kapcb.framework.security.filter;

import com.kapcb.framework.common.constants.enums.ResultCode;
import com.kapcb.framework.common.result.CommonResult;
import com.kapcb.framework.common.util.JsonUtil;
import com.kapcb.framework.security.exception.ValidateCodeException;
import com.kapcb.framework.security.model.AuthenticationModel;
import com.kapcb.framework.security.validation.IValidateCodeService;
import com.kapcb.framework.web.exception.BusinessException;
import com.kapcb.framework.web.util.ResponseUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    
    // 认证类型
    private static final String GRANT_TYPE = "grant_type";
    // 密码模式
    private static final String PASSWORD = "password";

    private static AntPathRequestMatcher requestMatcher;

    @Resource
    private IValidateCodeService validateCodeService;

    @PostConstruct
    void init() {
        requestMatcher = new AntPathRequestMatcher("access_token", HttpMethod.POST.name());
    }

    
    
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!HttpMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            log.info("request method must be post request");
            throw new BusinessException(ResultCode.FAILED);
        }
        if (StringUtils.equals(request.getParameter(GRANT_TYPE), PASSWORD) && MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType())) {
            try {
                validateCode(request);
            } catch (Exception e) {
                log.error("validate code error, error message is : {}", e.getMessage());
                ResponseUtil.setUpJSONResponse(response, CommonResult.validateFailed(e.getMessage()));
                return null;
            }
            AuthenticationModel authenticationModel;
            try (InputStream inputStream = request.getInputStream()) {
                authenticationModel = JsonUtil.readValue(inputStream, AuthenticationModel.class);
            } catch (Exception e) {
                log.error("get username and password from request's input stream error, error message is : {}", e.getMessage());
                throw new BusinessException("username or password error!");
            }
            if (Objects.isNull(authenticationModel) || StringUtils.isBlank(authenticationModel.getUsername()) || StringUtils.isBlank(authenticationModel.getPassword())) {
                log.error("authentication model is null. username or password is null");
                throw new BusinessException("username or password error!");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationModel.getUsername().trim(), authenticationModel.getPassword().trim());
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    private void validateCode(HttpServletRequest httpServletRequest) throws ValidateCodeException {
        validateCodeService.verify(httpServletRequest.getParameter("key"), httpServletRequest.getParameter("code"));
    }
}
