package com.sh.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.travel.domain.PageBean;
import com.sh.travel.domain.Route;
import com.sh.travel.domain.User;
import com.sh.travel.service.FavoriteService;
import com.sh.travel.service.RoutService;
import com.sh.travel.service.impl.FavoriteServiceImpl;
import com.sh.travel.service.impl.RoutServciceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RoutService routService = new RoutServciceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1、接受参数
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");
        //接受线路名称
        String rname = req.getParameter("rname");


        int cid = 0;//类别
        //处理参数
        if(cidStr!=null&&cidStr.length()>0&&!"null".equals(cidStr)){//首页直接查询时，cid=null
            cid = Integer.parseInt(cidStr);
        }

        //当前页码，如果不传递，则默认为第一页
        int currentPage = 0;
        if(currentPageStr!=null&&currentPageStr.length()>0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }
        System.out.println("当前页码"+currentPage);
        //每页显示条数，如果不传递，默认显示五条记录
        int pageSize = 0;
        if(pageSizeStr!=null&&pageSizeStr.length()>0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        System.out.println("每页显示条数"+pageSize);
        //3、调用service查询PageBean对象
        PageBean<Route> pb = routService.pageQuery(cid,currentPage,pageSize,rname);

        //4、将pagegBean对象华为json,返回
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(pb);
//        resp.setContentType("application/json;charset=utf-8");
//        resp.getWriter().write(json);
         //4. 将pageBean对象序列化为json，返回
         writeValue(pb,resp);

    }

    /**
     * 根据id查询一个线路图
     * @param req
     * @param resp
     * @throws IOException
     */
    public void findOne(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1、接受id
        String rid = req.getParameter("rid");

        //2、调用service查询route对象
        Route route = routService.findOne(rid);
        System.out.println("count-------"+route.getCount());
        //3、转为json写回客户端
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(route);
//        resp.setContentType("application/json;charset=utf-8");
//        resp.getWriter().write(json);
        writeValue(route,resp);
    }

    /**
     * 根据rid,uid判断用户是否收藏
     * @param req
     * @param resp
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1、获取详情页面收藏按钮的rid
        String rids = req.getParameter("rid");
        int rid = Integer.parseInt(rids);

        //2、根据session获取用户信息
        User user = (User) req.getSession().getAttribute("user");
        //3、判断用户是否登录
        int uid = 0;//用户没登录
        if(user!=null){
            //用户已经登录
            //获取用户的uid
             uid = user.getUid();
        }
        //4、调用favoriteService,查询是否有收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        //将java对象转为json并写回客户端
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(flag);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 添加收藏
     * @param req
     * @param resp
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1、获取rid
        String rids = req.getParameter("rid");
        int rid = Integer.parseInt(rids);
        //2、根据rid查询用户user
        User user = (User) req.getSession().getAttribute("user");
        int uid;//用户id
        //判断用户是否登录
        if(user!=null){
            //用户已经登录获取用户的uid
            uid = user.getUid();
        }else {
            //用户没有登录
            return;
        }
        //3、调用FavoriteService添加收藏
         favoriteService.addFavorite(rid,uid);


    }


}
