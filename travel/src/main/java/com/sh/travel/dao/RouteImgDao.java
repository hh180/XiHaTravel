package com.sh.travel.dao;

import com.sh.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据route表的rid查询图片集合信息
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);

}
