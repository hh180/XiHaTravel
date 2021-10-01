package com.sh.travel.service;

import com.sh.travel.dao.FavoriteDao;
import com.sh.travel.dao.impl.FavoriteDaoImpl;

public interface FavoriteService {
    /**
     * 查询是否有favorite对象
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(int rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void addFavorite(int rid, int uid);
}
