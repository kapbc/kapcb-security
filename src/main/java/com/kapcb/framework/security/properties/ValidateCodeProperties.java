package com.kapcb.framework.security.properties;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.model.ValidateCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * <a>Title: ValidateCodeProperties </a>
 * <a>Author: Kapcb <a>
 * <a>Description: ValidateCodeProperties <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/11 21:06
 */
@Data
@ConfigurationProperties(prefix = "kapcb.security.validate.code", ignoreInvalidFields = true)
public class ValidateCodeProperties implements ValidateCode, Serializable {

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

    private String type = StringPool.IMAGE_SUFFIX_PNG.value();
}
