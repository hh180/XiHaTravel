package com.sh.travel.service.impl;

import com.sh.travel.dao.FavoriteDao;
import com.sh.travel.dao.RouteDao;
import com.sh.travel.dao.RouteImgDao;
import com.sh.travel.dao.SellerDao;
import com.sh.travel.dao.impl.FavoriteDaoImpl;
import com.sh.travel.dao.impl.RouteDaoImpl;
import com.sh.travel.dao.impl.RouteImageDaoImpl;
import com.sh.travel.dao.impl.SellerDaoImpl;
import com.sh.travel.domain.PageBean;
import com.sh.travel.domain.Route;
import com.sh.travel.domain.RouteImg;
import com.sh.travel.domain.Seller;
import com.sh.travel.service.RoutService;

import java.util.List;

public class RoutServciceImpl implements RoutService {
    //声明RouteDao
    private RouteDao routeDao = new RouteDaoImpl();
    //声明RouteDaoImgDao
    private RouteImgDao routeImgDao = new RouteImageDaoImpl();
    //声明SellerDao
    private SellerDao sellerDao = new SellerDaoImpl();
    //声明FavoriteDao对象
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {

        //封装PageBean
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示的条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        System.out.println("totalCount"+totalCount);
        pb.setTotalCount(totalCount);
        //设置当前页显示的数据集合
        int start = (currentPage-1)*pageSize;//开始记录
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize)+1;
        pb.setTotalPage(totalPage);
        System.out.println("totalPage"+totalPage);
        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //1、根据route表的rid查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));
        //2、根据route的id查询图片集合信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //将集合设置到route对象中
        route.setRouteImgList(routeImgList);
        //3、根据route表的sid(商家的id)查询商家对象
        Seller seller = sellerDao.findBysid(route.getSid());
        //将商家信息设置到route对象中
        route.setSeller(seller);
        //4. 查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);

        return route;
    }

}
