package com.kapcb.framework.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <a>Title: ValidateCodeModel </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ValidateCodeModel <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/10 21:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateCodeModel implements Serializable {

    private static final long serialVersionUID = 721986356072994025L;

    private int ttl = 60;

    private int width = 130;

    private int height = 48;

    private int length = 4;

    /**
     * 验证码类型
     * 1. 数字+字母
     * 2. 纯数字
     * 3. 纯字母
     */
    private int charType;
}
