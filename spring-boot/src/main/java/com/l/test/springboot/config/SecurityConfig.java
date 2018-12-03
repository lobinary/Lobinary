package com.l.test.springboot.config;

import com.l.test.springboot.interceptor.SecurityInterceptor;
import com.l.test.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

//业务核心
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService); //user Details Service验证
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //        // http.authorizeRequests()每个匹配器按照它们被声明的顺序被考虑。
        http
            .authorizeRequests()
            // 所有用户均可访问的资源
            .antMatchers("/card/main/**").permitAll()
            // ROLE_USER的权限才能访问的资源
            .antMatchers("/card/**").hasRole("USER")
            // 任何尚未匹配的URL只需要验证用户即可访问
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .defaultSuccessUrl("/card/home")
            .successForwardUrl("/card/home")
            // 指定登录页面,授予所有用户访问登录页面
//            .loginPage("/user/login")
//            .loginProcessingUrl("/user/login-process")
//            .usernameParameter("username")
//            .passwordParameter("password")
            //设置默认登录成功跳转页面,错误回到login界面
            .failureUrl("/user/login?error").permitAll()
            .and()
            //开启cookie保存用户数据
            .rememberMe()
            //设置cookie有效期
            .tokenValiditySeconds(60 * 60 * 24 * 7)
            //设置cookie的私钥
            .key("security")
            .and()
            .logout()
            .permitAll();
        //登录拦截器
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class)
                //springsecurity4自动开启csrf(跨站请求伪造)与restful冲突
                .csrf().disable();
    }
}