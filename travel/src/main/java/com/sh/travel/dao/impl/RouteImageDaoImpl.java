package com.sh.travel.dao.impl;

import com.sh.travel.dao.RouteImgDao;
import com.sh.travel.domain.RouteImg;
import com.sh.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImgDao {
    //声明template
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<RouteImg> findByRid(int rid) {
        //1、定义sql
        String sql = "select * from tab_route_img where rid = ?";
        //2、执行sql
        List<RouteImg> routeImgList = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);

        return routeImgList;
    }
}
