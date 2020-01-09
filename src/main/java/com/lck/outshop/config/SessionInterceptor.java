package com.lck.outshop.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        登陆页面,首页放行不做权限认证
        if("/".equals(request.getRequestURI())||"login".equals(request.getRequestURI())){
            return true;
        }
        Object userId = request.getSession().getAttribute("userId");
        if(userId==null){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
