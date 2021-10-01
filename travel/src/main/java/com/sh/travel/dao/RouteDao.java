package com.sh.travel.dao;

import com.sh.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    /**
     * 查询总记录数
     * @param cid rname
     * @return
     */
    public int findTotalCount(int cid,String rname);

    /**
     * 查询每一页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize,String rname);

    /**
     * 根据route表的rid查询线路图
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
