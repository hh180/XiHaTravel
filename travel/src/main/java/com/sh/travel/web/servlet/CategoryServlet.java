package com.sh.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.travel.domain.Category;
import com.sh.travel.service.CategoryService;
import com.sh.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{
    private CategoryService service = new CategoryServiceImpl();
    /**
     * 查询所有
     * @param req
     * @param resp
     */
    public void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //1、调用service查询所有
        List<Category> cs = service.findAll();
        //2、序列化json返回
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(),cs);

    }
}
