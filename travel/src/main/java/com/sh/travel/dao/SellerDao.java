package com.sh.travel.dao;

import com.sh.travel.domain.Route;
import com.sh.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据route表中的sid查询商家信息
     * @param sid
     * @return
     */
    public Seller findBysid(int sid);
}
