package com.kapcb.framework.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <a>Title: AnonymousAccess </a>
 * <a>Author: Kapcb <a>
 * <a>Description: AnonymousAccess <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/21 15:43
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
