package com.angel.crowd.mvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 刘振河
 * @create 2020--04--14 15:53
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public void configure(AuthenticationManagerBuilder build) throws Exception {
        // 临时使用内存登录
//       build.inMemoryAuthentication().withUser("tom").password("123123").roles("ADMIN");

        // 正式使用基于数据库的认证
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity security) throws Exception {
    security.authorizeRequests()
            .antMatchers("/admin/to/login/page.html") // 针对登录页进行设置
            .permitAll()
            .antMatchers("/bootstrap/**")
            .permitAll()
            .antMatchers("/fonts/**")
            .permitAll()
            .antMatchers("/css/**")
            .permitAll()
            .antMatchers("/img/**")
            .permitAll()
            .antMatchers("/layer/**")
            .permitAll()
            .antMatchers("/jquery/**")
            .permitAll()
            .antMatchers("/script/**")
            .permitAll()
            .antMatchers("/assets/**")
            .permitAll()
            .antMatchers("/script/**")  // 释放静态资源
            .permitAll()
            .antMatchers("/admin/page.html")	// 针对分页显示Admin数据设定访问控制
//            	.hasRole("经理")    // 访问者必须拥有经理角色
            .access("hasRole('经理') OR hasAuthority('user:get')")
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                    request.setAttribute("exception",null);
                    request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request,response);
                }
            })
            .and()
            .csrf() //  防跨站请求伪造
            .disable()  // 禁用
            .formLogin()
            .loginPage("/admin/to/login/page.html") // 指定登录页面
            .loginProcessingUrl("/security/do/login.html")   // 登录请求地址
            .defaultSuccessUrl("/admin/to/main/page.html")  // 登陆成功后去的地址
            .usernameParameter("loginAcct") // 账号请求参数名称
            .passwordParameter("userPswd")  // 密码请求参数名称
            .and()
            .logout()
            .logoutUrl("/do/logout")
            .logoutSuccessUrl("/index.jsp")

    ;

    }
}
