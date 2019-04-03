package pers.wong.kec.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author Wangjunwei
 * @Date 2019/3/12 16:59
 * @Description 用户拦截器配置
 * 拦截器和过滤器：
 * 执行顺序： 先filter 后 interceptor
 * 过滤前-拦截前-action执行-拦截后-过滤后
 */
public class UseerKecHandleInterceptor extends HandlerInterceptorAdapter {

  /**
   * 登录session key
   */
  public final static String SESSION_KEY = "userId";

  /**
   * 进入controller层之前拦截请求
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession();
    if (session.getAttribute(SESSION_KEY) != null)
      return true;

    // 跳转登录
    String url = "/entrance/login";
    response.sendRedirect(url);
    return false;
  }
}
