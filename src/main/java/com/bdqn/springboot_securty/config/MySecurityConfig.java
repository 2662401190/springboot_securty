package com.bdqn.springboot_securty.config;

import com.bdqn.springboot_securty.util.CustomPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求的授权规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动装配的登录功能
        http.formLogin().loginPage("/userlogin");
        // 开启自动配置的注销功能
        //注销成功 跳转到 首页
        http.logout().logoutSuccessUrl("/");

        //开启记住我的功能
        http.rememberMe();
    }
    /**
     * 定制认证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("VIP1", "VIP2", "VIP3")
            .and()
                .withUser("lisi").password("123").roles("VIP1","VIP2")
             .and()
                .withUser("hewei").password("123").roles("VIP1")
       //  springBoot 5  新加的
        .and().passwordEncoder(new CustomPasswordEncoder());

    }
}
