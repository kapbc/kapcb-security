package com.kapcb.framework.security.util;

import com.kapcb.framework.common.constants.enums.StringPool;
import com.kapcb.framework.security.model.ValidateCode;
import com.kapcb.framework.web.exception.BusinessException;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

/**
 * <a>Title: CaptchaUtil </a>
 * <a>Author: Kapcb <a>
 * <a>Description: CaptchaUtil <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/11 21:12
 */
@Slf4j
@UtilityClass
public class CaptchaUtil {

    public static Captcha create(ValidateCode validateCode) {
        if (Objects.isNull(validateCode) || StringUtils.isBlank(validateCode.getType())) {
            log.error("validate code can not be null");
            throw new BusinessException("");
        }
        Captcha captcha = matchCaptcha(validateCode);
        captcha.setCharType(validateCode.getCharType());
        return captcha;
    }

    private static Captcha matchCaptcha(ValidateCode validateCode) {
        return Match(validateCode.getType()).of(
                Case($(StringPool.IMAGE_SUFFIX_GIF.value()), new GifCaptcha(validateCode.getWidth(), validateCode.getLength(), validateCode.getHeight())),
                Case($(StringPool.IMAGE_SUFFIX_PNG.value()), new SpecCaptcha(validateCode.getWidth(), validateCode.getLength(), validateCode.getHeight())));
    }
}
