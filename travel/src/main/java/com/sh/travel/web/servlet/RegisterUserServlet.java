package com.sh.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.travel.domain.ResultInfo;
import com.sh.travel.domain.User;
import com.sh.travel.service.UserService;
import com.sh.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registerUserServlet")
public class RegisterUserServlet extends HttpServlet implements Servlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        UserService service = new UserServiceImpl();
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
