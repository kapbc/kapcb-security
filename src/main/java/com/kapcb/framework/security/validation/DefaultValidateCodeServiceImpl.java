package com.kapcb.framework.security.validation;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.exception.ValidateCodeException;
import com.kapcb.framework.security.model.ValidateCodeModel;
import com.wf.captcha.base.Captcha;
import ma.glasnost.orika.impl.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a>Title: DefaultValidateCodeServiceImpl </a>
 * <a>Author: Kapcb <a>
 * <a>Description: DefaultValidateCodeServiceImpl <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/10 22:03
 */
public class DefaultValidateCodeServiceImpl implements IValidateCodeService {


    @Override
    public boolean create(HttpServletRequest request, HttpServletResponse response) {
        String authenticationKey = request.getParameter(StringPool.AUTHENTICATION_VERIFICATION_CODE_KEY.value());
        if (StringUtils.isBlank(authenticationKey)) {
            throw new ValidateCodeException("");
        }

        return false;
    }

    @Override
    public void verify(String key, String code) {

    }

}
