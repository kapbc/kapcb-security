package com.kapcb.framework.security.validation;

import com.kapcb.framework.security.exception.ValidateCodeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    boolean create(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void verify(String key, String code) throws ValidateCodeException;

}
