package com.sh.travel.service.impl;

import com.sh.travel.dao.UserDao;
import com.sh.travel.dao.impl.UserDaoImpl;
import com.sh.travel.domain.User;
import com.sh.travel.service.UserService;
import com.sh.travel.util.MailUtils;
import com.sh.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    //声明UserDao
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean regist(User user) {
        System.out.println("regist前");
        //根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        System.out.println("regist"+u);
        //判断u是否为null
        if(u!=null){
            //用户名存在，注册失败
            return false;
        }
        //设置激活码,唯一的字符串,用户的唯一标识
        user.setCode(UuidUtil.getUuid());
        //设置激活状态
        user.setStatus("N");
        //2、保存用户
        userDao.save(user);
        //3、激活邮件发送，邮件正文，将激活码放在邮件里，就可以知道这份邮件是那个用户的
        //<a href='login.html'>登录</a>
        String content = "<a href='http://localhost:8080/travel_war/user/active?code="+user.getCode()+"'>点击嘻哈旅游网</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean active(String code) {
        //1、根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if(user!=null){
            //2、调用修改激活状态的方法
            userDao.updateStatus(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {

        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
