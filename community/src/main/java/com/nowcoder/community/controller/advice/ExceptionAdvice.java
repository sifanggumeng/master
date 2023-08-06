package com.nowcoder.community.controller.advice;

import com.nowcoder.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常: " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            logger.error(element.toString());
        }
        //获取请求头中x-requested-with字段的内容
        String xRequestedWith = request.getHeader("x-requested-with");
        //异步请求（jQuery方式的异步请求请求头中x-requested-with字段的内容是"XMLHttpRequest"，正常请求是HTML格式的）
        if ("XMLHttpRequest".equals(xRequestedWith)) {
            //向浏览器返回一个字符串，浏览器会自动转化成json对象
            //response.setContentType("application/json;charset=utf-8");
            //向浏览器返回一个普通字符串，浏览器得到一个需要人为地将字符串转化成json对象（即jQuery中的$.parseJSON(data)方法）
            response.setContentType("application/plain;charset=utf-8");
            //获取输出流输出一个字符串
            PrintWriter writer = response.getWriter();
            //异步请求返回JSON字符串
            writer.write(CommunityUtil.getJSONString(1, "服务器异常!"));
        } else {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

}
