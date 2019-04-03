package pers.wong.kec.common;

import pers.wong.kec.common.enums.ResultEnum;

/**
 * @author Wangjunwei
 * @Date 1/24/2019 3:24 PM
 * @Description 通用返回类
 */
public class Result {
  private Integer code;
  private String message;
  private Object data;

  public Result() {
  }

  public Result(Object data) {
    this.code = ResultEnum.SUCCESS.getCode();
    this.data = data;
  }

  public Result(Integer code, Object data) {
    this.code = code;
    this.data = data;
  }

  //成功，无返回值
  public static Result success() {
    return new Result((Object)null);
  }

  //返回值
  public static Result success(Object data) {
    return new Result(data);
  }

  //失败
  public static Result fail(Exception e) {
    return new Result(e.getMessage());
  }

  public static Result fail(BusinessException e) {
    return new Result(e.getCode(), e.getMessage());
  }

  public static Result failed(ResultEnum errorCode, String message) {
    return new Result(errorCode.getCode(), message);
  }

  public Result(Integer code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "APIResult{code=" + this.code + ", data=" + this.data + ", message='" + this.message + '\'' + '}';
  }
}

