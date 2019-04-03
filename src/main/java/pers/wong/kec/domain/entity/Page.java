package pers.wong.kec.domain.entity;

import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 1/25/2019 5:33 PM
 * @Description
 */

@Data
public class Page {

  /**
   * 每页记录数
   */
  private Integer pageSize = 10;
  /**
   * 当前页面
   */
  private Integer pageNum = 1;
}
