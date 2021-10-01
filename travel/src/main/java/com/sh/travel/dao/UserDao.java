package com.sh.travel.dao;

import com.sh.travel.domain.User;

public interface UserDao {
    /**
     * 查询用户名
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 保存用户
     * @param user
     */
    public void save(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    public User findByCode(String code);

    /**
     * 修改指定用户激活码的状态
     * @param user
     */
    public void updateStatus(User user);

    /**
     * 根据用户名，密码查讯用户
     * @param username
     * @param password
     * @return
     */
    public User findByUsernameAndPassword(String username, String password);
}
