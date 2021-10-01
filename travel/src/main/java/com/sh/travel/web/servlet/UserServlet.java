package com.sh.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.travel.domain.ResultInfo;
import com.sh.travel.domain.User;
import com.sh.travel.service.UserService;
import com.sh.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    //声明UserService对象
    private UserService service = new UserServiceImpl();

    /**
     * 注册
     * @param req
     * @param resp
     * @throws IOException
     */
    public void register(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("我是/Userservlet/register方法");
        //验证校验
        //获取客户端输入的验证码
        String check = req.getParameter("check");
        //从session中获取服务器端验证码
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //比较
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            //验证码错误
            ResultInfo info = new ResultInfo();
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }
        System.out.println("进入servlet");
        //1、获取数据
        Map<String, String[]> map = req.getParameterMap();
        //2、封装对象
        User user = new User();
        try {
            //将map集合中的属性封装到user中
            BeanUtils.populate(user,map);
            System.out.println(user.getUsername());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3、调用service完成注册
        System.out.println("进入regist");
        boolean flag = service.regist(user);
        System.out.println("返回"+flag);
        //后端返回前端数据对象
        ResultInfo info = new ResultInfo();
        //4、响应结果
        if(flag){
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回到客户端
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);
    }

    /**
     * 登录
     * @param req
     * @param resp
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("我是/Userservlet/Login方法");
        //获取前台验证码的值
        String check = req.getParameter("check");
        //创建一个session
        HttpSession session = req.getSession();
        //获取后台的验证码
        String check_code = (String) session.getAttribute("CHECKCODE_SERVER");
        //确保一个验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //判断验证码
        if(check==null||!check.equalsIgnoreCase(check_code)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将java对象转为json字符串
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //响应类型设置为json
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;
        }
        //1、获取用户名和密码
        Map<String, String[]> map = req.getParameterMap();
        //2、封装User对象
        User user = new User();
        try {
            //将map的数据封装到user
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3、调用Service查询
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //4、判断用户对象是否为null
        if(u==null){
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
            System.out.println("没有用户");
        }
        //判断用户是否激活
        if(u!=null&&!"Y".equals(u.getStatus())){
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您尚未激活");
            System.out.println("没有激活");
        }
        //判断用户是否登录成功
        if(u!=null&&"Y".equals(u.getStatus())){
            //用户登录成功
            info.setFlag(true);
            System.out.println("登录成功");
            //将用户放在session中，便于后面实现获得session后，将用户名显示到index.html
            req.getSession().setAttribute("user",u);
        }
//        //将info对象序列化为json
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(info);
//        //将json数据写回到客户端
//        resp.setContentType("application/json;charset=utf-8");
//        resp.getWriter().write(json);

        //响应数据
        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),info);
    }

    /**
     * 在index.html页面显示用户名
     * @param req
     * @param resp
     * @throws IOException
     */
    public void findOne(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //从session中获取登录用户
        Object user = req.getSession().getAttribute("user");
        //将用户写回到客户端
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),user);
    }

    /**
     * 退出
     * @param req
     * @param resp
     * @throws IOException
     */
    public void exit(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //1、销毁session
        req.getSession().invalidate();
        //2、跳转到登录页面
        resp.sendRedirect(req.getContextPath()+"/login.html");
    }
    /**
     * 邮箱激活
     * @param req
     * @param resp
     * @throws IOException
     */
    public void active(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //获取激活码
        String code = req.getParameter("code");
        if (code != null) {
            //2、调用service完成激活
            //改变用户激活码的状态
            boolean flag = service.active(code);
            //3、判断标记
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='http://localhost:8080/travel_war/login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员";
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().write(msg);
        }
    }
}
