package com.kapcb.framework.security.model;

/**
 * <a>Title: ValidateCode </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ValidateCode <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/11 21:19
 */
public interface ValidateCode {

    int getTtl();

    void setTtl(int ttl);

    int getWidth();

    void setWidth(int width);

    int getHeight();

    void setHeight(int height);

    int getLength();

    void setLength(int length);

    int getCharType();

    void setCharType(int charType);

    String getType();

    void setType(String type);
}
