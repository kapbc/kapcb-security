package com.kapcb.framework.security.validation;

import com.kapcb.framework.security.model.ValidateCodeModel;
import com.wf.captcha.base.Captcha;

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

    boolean create(Captcha captcha);

    void verify(String key, String code);

}
