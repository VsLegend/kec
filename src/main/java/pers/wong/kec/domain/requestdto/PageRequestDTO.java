package pers.wong.kec.domain.requestdto;

import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/2/27 14:59
 * @Description
 */

@Data
public class PageRequestDTO {

  /**
   * 第几页
   */
  private Integer pageNum;

  /**
   * 每页条数
   */
  private Integer pageSize;
}
