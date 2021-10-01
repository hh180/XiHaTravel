package com.sh.travel.dao;

import com.sh.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据rid,uid查询对应的favorite对象
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByridAndUid(int rid,int uid);

    /**
     * 根据rid查询收藏的次数
     * @param rid
     * @return
     */
    public int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    public void addFavorite(int rid, int uid);
}
