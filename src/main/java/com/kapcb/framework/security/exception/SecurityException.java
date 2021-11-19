package com.kapcb.framework.security.exception;

import com.kapcb.framework.common.result.IResultCode;

/**
 * <a>Title: SecurityException </a>
 * <a>Author: Kapcb <a>
 * <a>Description: SecurityException <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/19 22:23
 */
public class SecurityException extends RuntimeException {

    private IResultCode resultCode;

    private static final long serialVersionUID = -8061840813006875083L;

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(Throwable throwable) {
        super(throwable);
    }

    public SecurityException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SecurityException(IResultCode resultCode) {
        super(resultCode.msg());
        this.resultCode = resultCode;
    }

    public IResultCode getResultCode() {
        return this.resultCode;
    }

}
