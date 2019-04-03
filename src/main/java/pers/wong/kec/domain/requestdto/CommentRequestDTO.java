package pers.wong.kec.domain.requestdto;

import java.util.Date;
import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/2/27 14:49
 * @Description
 */

@Data
public class CommentRequestDTO {

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 状态 0 正常 1 删除
   */
  private String status;

  /**
   * 评论内容
   */
  private String content;

  /**
   * 评论人姓名
   */
  private String userName;

  /**
   * 评论人id
   */
  private String userId;

  /**
   * 关联id 主贴id/评论id
   */
  private String relationId;

  /**
   * 0评论主贴  1回复评论
   */
  private String relationType;

  private String postId;
}
