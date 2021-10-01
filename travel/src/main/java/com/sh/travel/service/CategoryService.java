package com.sh.travel.service;

import com.sh.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有种类
     * @return
     */
    public List<Category> findAll();
}
