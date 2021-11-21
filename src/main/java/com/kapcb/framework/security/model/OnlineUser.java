package com.kapcb.framework.security.model;

/**
 * <a>Title: OnlineUser </a>
 * <a>Author: Kapcb <a>
 * <a>Description: OnlineUser <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/21 14:06
 */
public interface OnlineUser {

    Long getUserId();

    String getUsername();

    String getKey();

    String getIp();

    String getCity();

    String getBrowser();
}
