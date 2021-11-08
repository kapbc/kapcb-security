package com.kapcb.framework.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <a>Title: Authentication </a>
 * <a>Author: Kapcb <a>
 * <a>Description: Authentication <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/8 22:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationModel implements Serializable {

    private String username;

    private String password;
}
