package pers.wong.kec.domain.requestDTO;

import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/2/27 14:49
 * @Description
 */

@Data
public class SearchRequestDTO extends PageRequestDTO {

  /**
   * 关键字搜索
   */
  private String key;

  private String userId;

  /**
   * 板块id，查询帖子列表时，必要
   */
  private String moduleId;

  private String status;
}
