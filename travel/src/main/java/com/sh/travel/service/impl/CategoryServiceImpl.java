package com.sh.travel.service.impl;

import com.sh.travel.dao.CategoryDao;
import com.sh.travel.dao.impl.CategoryDaoImpl;
import com.sh.travel.domain.Category;
import com.sh.travel.service.CategoryService;
import com.sh.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        /**
         * 从redis中查询
         */
        //1、获取jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //2、可使用sortedset排序查询
        //Set<String> categorys = jedis.zrange("category", 0, -1);
        //查询sortdst中的分数(cid)和值(cname)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        //定义一个list集合的category用来存放查询数据库的返回值
        List<Category> cs = null;
        //3、判断查询的集合是否为空
        if(categorys==null||categorys.size()==0){
            System.out.println("从数据库中查询...");
            //4、如果为空需要从数据库中查询，再将数据存入redis
            cs = categoryDao.findAll();
            //5、将集合数据存入redis
            for (int i = 0; i <cs.size() ; i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
            System.out.println("从redis中查询...");
            //6、如果不为空，则set的数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple:categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int) tuple.getScore());
                cs.add(category);
            }
        }

        return cs;
    }
}
