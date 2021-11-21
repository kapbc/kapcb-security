package com.kapcb.framework.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
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
public class AuthUserDTO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String validationCode;

    @Override
    public String toString() {
        return "AuthUserDTO{" +
                "username='" + username + '\'' +
                ", password= ******'" + '\'' +
                ", validationCode='" + validationCode + '\'' +
                '}';
    }
}
