package com.sh.travel.dao;

import com.sh.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询所有种类
     * @return
     */
    public List<Category> findAll();
}
