package pers.wong.kec.common.enums;

/**
 * @author Wangjunwei
 * @Date 1/24/2019 3:26 PM
 * @Description
 */
public enum ResultEnum {

  /**
   * 登录错误
   */
  UNEXCEPTED(9999, "未知异常"),
  SUCCESS(1000, "成功"),
  USER_NOT_LOGIN(1001, "用户未登录"),
  AUTH_TOKEN_EXPIRE(1002, "认证令牌过期"),
  ILLEGAL_PARAMETER(1003, "非法参数"),
  ACCESS_DENIED(1004, "权限不足"),
  RESULT_EMPTY(1005, "结果为空"),
  FAIL_DATABASE(1006, "操作数据库失败"),
  HEADER_PARAMETER_VALIDATE_FAIL(1007, "头参校验失败"),
  USER_LOGIN_OUT(1008, "用户退出登录"),
  USER_ALREADY_LOGIN(1009, "用户已经在其他地方重新登录"),
  STUDENT_NUMBER_BEEN_OCCUPIED(1010, "学号已注册"),
  USER_PASSWORD_ERROR(1011, "密码输入错误"),
  USER_NOT_SIGN_UP(1012, "用户未注册"),
  USER_HAS_BEEN_FORBIDDEN(1013, "用户已被封号"),
  USER_NAME_IS_NULL(1014, "用户名为空"),

  /**
   * 主贴错误
   */
  POST_MODULE_IS_NULL(2001, "发帖的板块id为空"),
  POST_HAS_BEEN_DELETED(2002, "主贴已被删除"),
  MODULE_HAS_BEEN_DELETED(2003, "板块已被删除"),
  ;

  private Integer code;
  private String message;

  ResultEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
