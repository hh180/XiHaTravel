package com.sh.travel.dao.impl;

import com.sh.travel.dao.UserDao;
import com.sh.travel.domain.User;
import com.sh.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.jws.soap.SOAPBinding;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //声明JdbcTemplate
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {

        User user = null;
        try {
            //1.定义sql
            String sql = "select * from tab_user where username = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }

        return user;

    }

    @Override
    public void save(User user) {
        //1、定义sql
        //由于前端页面提交的数据只有这7个，所以只填写7个
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) " +
                "values (?,?,?,?,?,?,?,?,?)";
        //2、执行sql
        //由于前面的registServlet已经将map集合中的属性封装到user中
        template.update(sql,
                user.getUsername(),user.getPassword(),
                user.getName(),user.getBirthday(),
                user.getSex(),user.getTelephone(),
                user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try{
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid= ? ";
        template.update(sql,user.getUid());
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try{
            //1、定义sql
            String sql = "select * from tab_user where username=? and password=?";
            //2、执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
        }catch (Exception e){
            return null;
        }
        return user;
    }
}
