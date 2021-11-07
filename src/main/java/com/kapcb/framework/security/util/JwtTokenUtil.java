package com.kapcb.framework.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <a>Title: JwtTokenUtil </a>
 * <a>Author: Kapcb <a>
 * <a>Description: JwtTokenUtil <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/6 16:08
 */
@Slf4j
@UtilityClass
public class JwtTokenUtil {

    private static final String USER_ID = "id";
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final Calendar CALENDAR = Calendar.getInstance();

    public static String generateToke(String username) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, CALENDAR.getTime());
        return generateJwtToken(claims);
    }

    public static Claims getClaims(String token) {
        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("jwt token can not be null or empty");
        }
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey("kapcb")
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("get claims from jwt token error, error message is : {}", e.getMessage());
        }
        return claims;
    }

    public static String getUsername(String token) {
        String username = null;
        try {
            Claims claims = getClaims(token);
            if (Objects.nonNull(claims)) {
                username = claims.getSubject();
            }
        } catch (Exception e) {
            log.error("get username from token error, error message is : {}", e.getMessage());
        }
        return username;
    }

    public static boolean validateToken(String token, String username) {
        return expired(token) && StringUtils.equals(getUsername(token), username);
    }

    public static boolean expired(String token) {
        Date expirationDate = getExpirationDate(token);
        if (Objects.isNull(expirationDate)) {
            throw new IllegalArgumentException("jwt expiration can not be null or empty");
        }
        return expirationDate.before(CALENDAR.getTime());
    }

    private Date getExpirationDate(String token) {
        Date expiration = null;
        try {
            Claims claims = getClaims(token);
            if (Objects.nonNull(claims)) {
                expiration = claims.getExpiration();
            }
        } catch (Exception e) {
            log.error("get expiration from token error, error message is : {}", e.getMessage());
        }
        return expiration;
    }

    private static String generateJwtToken(Map<String, Object> claims) {
        if (MapUtils.isEmpty(claims)) {
            throw new IllegalArgumentException("claims can not be null or empty");
        }
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, "kapcb")
                .compact();
    }

    private static Date generateExpirationDate() {
        CALENDAR.setTimeInMillis(System.currentTimeMillis() + (60 * 60 * 24 * 1000));
        return CALENDAR.getTime();
    }

}
