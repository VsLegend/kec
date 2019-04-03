package pers.wong.kec.common;

import pers.wong.kec.common.enums.ResultEnum;

/**
 * @author Wangjunwei
 * @Date 1/24/2019 3:48 PM
 * @Description
 */
public class BusinessException extends RuntimeException {

  private Integer code;

  public BusinessException() {}

  public BusinessException(ResultEnum resultEnum) {
    super(resultEnum.getMessage());
    this.code = resultEnum.getCode();
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }
}
