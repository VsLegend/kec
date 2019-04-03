package pers.wong.kec.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Wangjunwei
 * @Date 2019/1/8 17:48
 * @Description 项目登录等其它安全配置
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //开始security注解
public class KeCSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    super.configure(auth);
    //
//    auth.jdbcAuthentication().dataSource(dataSource)
//        //用户名，密码，是否可用
//        .usersByUsernameQuery("select studentNo, password, IF(`status` = '0', TRUE, FALSE) "
//            + "FROM `user` "
//            + "where studentNo = ?;")
//        //用户所授予的权限
//        .authoritiesByUsernameQuery("SELECT studentNo, IF(`messageType` = '2', 'ROLE_ADMIN', 'ROLE_USER') "
//            + "FROM `user` "
//            + "where studentNo = ?;")
//        .passwordEncoder(new CommonUtil());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
    //解决静态资源被拦截问题,也可以设置不需要鉴权的页面
    web.ignoring().antMatchers("/static/**", "classpath:/static/");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    super.configure(http); //这里父类的方法，里面是所有的页面都需要跳转到登录界面
//    http.authorizeRequests()
//        //如下地址所有人都有权限进入
//        .antMatchers("/", "/index", "/entrance/**",
//            "/user/login", "/user/signUp", "/backstage/login").permitAll()
//        //其他地址均需验证权限
//        .anyRequest().authenticated()
//        .and()
//        .formLogin()
//        .loginPage("/entrance/login")
//        .defaultSuccessUrl("/index")
//        .permitAll();
//    暂时关闭CSRF
    http.logout().logoutSuccessUrl("/entrance/login");
    http.csrf().disable();
  }
}
