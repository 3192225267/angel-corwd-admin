package com.angel.crowd.mvc.config;

import com.angel.crowd.exception.AccessForbiddenException;
import com.angel.crowd.exception.LoginAcctAlreadyInUseException;
import com.angel.crowd.exception.LoginFailedException;
import com.angel.crowd.util.CrowdUtil;
import com.angel.crowd.util.ResultEntity;
import com.google.gson.Gson;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bader
 * @create 2020--03--28 14:00
 * 异常映射类
 */
//@ControllerAdvice 表示当前类是一个基于注解的异常处理类
@ControllerAdvice
public class CrowdExceptionResolver {

    //登陆失败时的异常
    @ExceptionHandler
    public ModelAndView LoginExcetinon(
            //实际捕获的异常
            LoginFailedException exception,
            //当前请求对象
            HttpServletRequest request,

            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可

        String viewName="admin-login";
        //11 返回modelAndView对象
        return commonResolveException(exception,request,response,viewName);
    }

    @ExceptionHandler
    public ModelAndView BadCredentialsException(
            //实际捕获的异常
            BadCredentialsException exception,
            //当前请求对象
            HttpServletRequest request,

            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName="admin-login";
        //11 返回modelAndView对象
        return commonResolveException(exception,request,response,viewName);
    }

   //用户状态过期时异常
    @ExceptionHandler
    public ModelAndView AccessForbiddenException(
            //实际捕获的异常
            AccessForbiddenException exception,
            //当前请求对象
            HttpServletRequest request,

            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName="admin-login";
        //11 返回modelAndView对象
        return commonResolveException(exception,request,response,viewName);
    }

    //用户账号重复时异常
    @ExceptionHandler
    public ModelAndView LoginAcctAlreadyInUseException(
            //实际捕获的异常
            LoginAcctAlreadyInUseException exception,
            //当前请求对象
            HttpServletRequest request,

            HttpServletResponse response
    ) throws IOException {
        // 只是指定当前异常对应的页面即可
        String viewName="admin-add";
        //11 返回modelAndView对象
        return commonResolveException(exception,request,response,viewName);
    }

    /*** 核心异常处理方法 *
     *  @param exception SpringMVC 捕获到的异常对象 *
     *  @param request 为了判断当前请求是“普通请求”还是“Ajax 请求”
     *   需要传入原生 request 对象 * @param response 为了能够将 JSON 字符串作为当前请求的响应数 据返回给浏览器 *
     *  @param viewName 指定要前往的视图名称 *
     *  @return ModelAndView * @throws IOException */
    private ModelAndView commonResolveException(  //实际捕获的异常
                                                  Exception exception,
                                                  //当前请求对象
                                                  HttpServletRequest request,

                                                  HttpServletResponse response,
                                                  //要去的视图名称
                                                  String viewName
                                                ) throws IOException {

        //1.判断当前请求类型
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        //2.如果是Ajax请求
        if (judgeResult) {

            //3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //4.创建Gson对象
            Gson gson = new Gson();
            //5.将ResultEntity转换为json类型
            String json = gson.toJson(resultEntity);

            //6.将json字符串作为响应体返回给浏览器
            response.getWriter().write(json);
            //7.由于上面已经通过原生的response对象返回了响应，所以不提供ModelandView对象
            return null;
        }
        //8.如果不是Ajax请求则创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        // 9.将Exception存入模型
        modelAndView.addObject("exception", exception);

        //10 设置对应视图名称
        modelAndView.setViewName(viewName);

        return modelAndView;
    }
}
