package com.sh.travel.dao.impl;

import com.sh.travel.dao.CategoryDao;
import com.sh.travel.domain.Category;
import com.sh.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    //声明template
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<Category> findAll() {
        //1、定义sql
        String sql = "select * from tab_category";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
