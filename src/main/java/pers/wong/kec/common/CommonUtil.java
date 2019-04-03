package pers.wong.kec.common;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import pers.wong.kec.common.enums.ResultEnum;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 14:59
 * @Description
 */
public class CommonUtil implements PasswordEncoder {

  /** 加密盐-双重md5 */
  private static final String SALT = "kecheng51234.s1twfgefhsjg.siytrgsw.25syehsdie.2346537123";

  /** 重力因子 */
  private static final double G = 1.2;

  /**
   * md5 加密
   *
   * @param passWord
   * @return
   */
  public static String encodePassWord(String passWord) {
    // 双重加密
    passWord = passWord + SALT;
    String md5Password = DigestUtils.md5DigestAsHex(passWord.getBytes());
    return md5Password;
  }

  /**
   * md5 解密
   *
   * @param passWord 密码
   * @param encodePassWord 加密码
   * @return Boolean 匹配返回true
   */
  public static Boolean matches(String passWord, String encodePassWord) {
    return encodePassWord(passWord).equals(encodePassWord);
  }

  //UUID
  public static String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  //
  public static boolean isEmptyOrNull(String o) {
    if (null == o || o.equals("")) {
      return true;
    }
    return false;
  }

  //返回当前用户id
  public static String getCurrentUserId(HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_ADMIN_KEY);
    if (id == null) {
      id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
      if (id == null) {
        return null;
      }
    }
    return id.toString();
  }

  //返回当前登录的管理员id
  public static String getCurrentAdminId(HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_ADMIN_KEY);
    if (id == null) {
      return null;
    }
    return id.toString();
  }

  @Override
  public String encode(CharSequence charSequence) {
    return this.encodePassWord(charSequence.toString());
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return this.matches(charSequence.toString(), s);
  }

  //热点算法的分数计算
  public static double getPopularScore(double follow, double hours) {
    return (follow - 1) / Math.pow((hours + 2), G);
  }
}
