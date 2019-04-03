package pers.wong.kec.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Wangjunwei
 * @Date 2018/12/28 11:13
 * @Description 重写spring mvc配置类，用于自定义mvc配置
 */
//标注为配置类
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

  /**
   * 自定义拦截器，添加拦截路径和排除路径
   * @param registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 多个拦截器组成一个拦截器链
    // addPathPatterns 用于添加拦截规则
    // excludePathPatterns 用户排除拦截
    registry.addInterceptor(new UseerKecHandleInterceptor())
        .addPathPatterns("/entrance/needInfo/**");
    registry.addInterceptor(new AdminKecHandleInterceptor()).addPathPatterns("/backstage/**")
        .addPathPatterns("/module/**").addPathPatterns("/post/deletePost/**")
        .addPathPatterns("/userControl/**").addPathPatterns("/news/**")
    .excludePathPatterns("/backstage/login");
  }

  /**
   * 重写静态资源访问地址
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }

  /**
   * /**的意思是所有文件，包括文件夹中的子文件
   * /*是所有文件，不包含子文件
   * /是web项目的根目录
   * 这里是在用户访问根目录时，跳转到/index下
   * @param registry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }
}
