package com.kapcb.framework.security.validation;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.exception.ValidateCodeException;
import com.kapcb.framework.security.properties.ValidateCodeProperties;
import com.kapcb.framework.security.util.CaptchaUtil;
import com.kapcb.framework.web.util.ResponseUtil;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <a>Title: DefaultValidateCodeServiceImpl </a>
 * <a>Author: Kapcb <a>
 * <a>Description: DefaultValidateCodeServiceImpl <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/10 22:03
 */
@Slf4j
@Component
public class DefaultValidateCodeServiceImpl implements IValidateCodeService {

    @Resource
    private ValidateCodeProperties validateCodeProperties;

    @Override
    public boolean create(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String authenticationKey = request.getParameter(StringPool.AUTHENTICATION_VERIFICATION_CODE_KEY.value());
//        if (StringUtils.isBlank(authenticationKey)) {
//            log.error("authentication verification code key is null or empty");
//            throw new ValidateCodeException("authentication verification code key can not be null or empty");
//        }
        ResponseUtil.setHeader(response, validateCodeProperties.getType());
        Captcha captcha = CaptchaUtil.create(validateCodeProperties);
        captcha.out(response.getOutputStream());
        return true;
    }

    @Override
    public void verify(String key, String code) {

    }

}
