package com.sh.travel.service.impl;

import com.sh.travel.dao.FavoriteDao;
import com.sh.travel.dao.impl.FavoriteDaoImpl;
import com.sh.travel.domain.Favorite;
import com.sh.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    //声明FavoriteDao对象
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findByridAndUid(rid, uid);
        //判断favorite是否为空，不为空则返回
        return favorite!=null?true:false;
    }

    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.addFavorite(rid,uid);
    }


}
