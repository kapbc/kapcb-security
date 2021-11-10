package com.kapcb.framework.security.validation;

import com.kapcb.framework.security.model.ValidateCodeModel;
import com.wf.captcha.base.Captcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a>Title: IValidateCodeService </a>
 * <a>Author: Kapcb <a>
 * <a>Description: IValidateCodeService <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/10 21:57
 */
public interface IValidateCodeService {

    boolean create(HttpServletRequest request, HttpServletResponse response);

    void verify(String key, String code);

}
