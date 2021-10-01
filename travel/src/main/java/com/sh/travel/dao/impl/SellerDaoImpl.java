package com.sh.travel.dao.impl;

import com.sh.travel.dao.SellerDao;
import com.sh.travel.domain.Route;
import com.sh.travel.domain.Seller;
import com.sh.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    //声明template对象
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Seller findBysid(int sid) {
        //定义sql
        String sql = "select * from tab_seller where sid = ?";
        //执行sql
        Seller seller = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return seller;
    }
}
