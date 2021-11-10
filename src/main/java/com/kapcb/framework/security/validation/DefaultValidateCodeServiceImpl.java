package com.kapcb.framework.security.validation;

import com.kapcb.framework.security.model.ValidateCodeModel;
import com.wf.captcha.base.Captcha;

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
        String code = request.getParameter("code");

        return false;
    }

    @Override
    public void verify(String key, String code) {

    }

}
