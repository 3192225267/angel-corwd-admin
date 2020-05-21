package com.angel.crowd.mvc.interceptor;

import com.angel.crowd.constant.CrowdConstant;
import com.angel.crowd.entity.Admin;
import com.angel.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Bader
 * @create 2020--03--29 15:23
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session=httpServletRequest.getSession();
      Admin admin=(Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
      if(admin==null){

        throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
      }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
