package com.sh.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.travel.domain.ResultInfo;
import com.sh.travel.domain.User;
import com.sh.travel.service.UserService;
import com.sh.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 登录servlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
