package com.sh.travel.dao.impl;

import com.alibaba.druid.util.JdbcUtils;
import com.sh.travel.dao.FavoriteDao;
import com.sh.travel.domain.Favorite;
import com.sh.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    //声明template
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findByridAndUid(int rid, int uid) {
        Favorite favorite = null;
        try{
           //1、定义sql
           String sql = " select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
       }catch (Exception e){
          favorite = null;
       }
        return favorite;
    }

    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?";
        Integer i = template.queryForObject(sql, Integer.class, rid);
        return i;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        //1、定义sql
        String sql = "insert into tab_favorite values (?,?,?) ";
        //执行sql
        int i = template.update(sql, rid, new Date(), uid);
        if(i>0){
            System.out.println("添加成功");
        }
    }

}
