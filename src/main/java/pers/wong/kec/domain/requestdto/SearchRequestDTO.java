package pers.wong.kec.domain.requestdto;

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

  /**
   * 主贴类型
   */
  private String postType;

  private String status;
}
