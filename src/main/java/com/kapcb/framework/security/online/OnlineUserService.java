package com.kapcb.framework.security.online;

import com.kapcb.framework.common.page.Page;
import com.kapcb.framework.security.model.OnlineUser;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <a>Title: OnlineUserService </a>
 * <a>Author: Kapcb <a>
 * <a>Description: OnlineUserService <a>
 *
 * @author Kapcb
 * @version 1.0.0
 * @date 2021/11/21 13:13
 */
public interface OnlineUserService {

    void save(String token, UserDetails userDetails, HttpServletRequest httpServletRequest);

    List<OnlineUser> getAllOnlineUser(String filter, int platformType);

    Map<String, Object> getAllOnlineUser(String filter, int platformType, Page page);

    void kickOut(String key) throws Exception;

    void kickOutMobile(String key) throws Exception;

    void logOut(String token);

    OnlineUser getOne(String key);

    /**
     * 检查用户之前是否已经登录, 已经登录则强制将之前登录的账号T下线
     *
     * @param username    String
     * @param ignoreToken String
     */
    void checkSingleLogin(String username, String ignoreToken);
}
