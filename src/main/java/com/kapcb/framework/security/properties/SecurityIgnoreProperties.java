package com.kapcb.framework.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * <a>Title: SecurityIgnoreProperties </a>
 * <a>Author: Kapcb <a>
 * <a>Description: SecurityIgnoreProperties <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:26
 */
@Data
@ConfigurationProperties("kapcb.security.ignore")
public class SecurityIgnoreProperties {

    private List<String> ignoreUrlList = new ArrayList<>();

}
