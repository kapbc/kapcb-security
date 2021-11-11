package com.kapcb.framework.security.exception;

import com.kapcb.framework.common.result.IResultCode;
import com.kapcb.framework.web.exception.BusinessException;

/**
 * <a>Title: ValidateCodeException </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ValidateCodeException <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/10 22:25
 */
public class ValidateCodeException extends BusinessException {

    private static final long serialVersionUID = -8061840813006875083L;

    public ValidateCodeException(String message) {
        super(message);
    }

    public ValidateCodeException(IResultCode resultCode) {
        super(resultCode);
    }

    public ValidateCodeException(Throwable throwable) {
        super(throwable);
    }

    public ValidateCodeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
