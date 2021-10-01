package com.sh.travel.service;

import com.sh.travel.domain.User;

public interface UserService {
    /**
     *注册用户
     * @param user
     * @return
     */
    public boolean regist(User user);

    /**
     *根据用户的激活码查询到用户，改变激活码的状态为"Y"
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录
     * @param user
     * @return
     */
    public User login(User user);
}
