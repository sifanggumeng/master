package com.nowcoder.community.controller.interceptor;

import com.nowcoder.community.entity.LoginTicket;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CookieUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor { //该拦截器用于持有并使用已登录用户的信息

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    // 在Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取ticket
        String ticket = CookieUtil.getValue(request, "ticket");
        if (ticket != null) {
            //查询LoginTicket
            LoginTicket loginTicket = userService.findLoginTicket(ticket);
            //检查LoginTicket是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                //根据LoginTicket查询用户
                User user = userService.findUserById(loginTicket.getUserId());
                //在本次请求中持有用户
                hostHolder.setUser(user);

                // 构建用户认证的结果,并存入SecurityContext,以便于Security进行授权.
                Authentication authentication  =
                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), userService.getAuthorities(user.getId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
            }
        }
        return true;
    }

    // 在Controller之后,模版引擎之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //在模板视图上显示用户数据
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    // 在TemplateEngine之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //在请求结束时清理用户数据
        hostHolder.clear();
    }
}
