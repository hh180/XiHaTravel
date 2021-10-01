package com.sh.travel.service;

import com.sh.travel.domain.PageBean;
import com.sh.travel.domain.Route;

public interface RoutService {
    /**
     * 分页
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据id查询一个旅游线路
     * @param rid
     * @return
     */
    public Route findOne(String rid);

}
