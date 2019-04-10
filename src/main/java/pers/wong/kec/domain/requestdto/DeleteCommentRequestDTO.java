package pers.wong.kec.domain.requestdto;

import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/2/27 14:49
 * @Description 删除评论或回复
 */

@Data
public class DeleteCommentRequestDTO {

  /**
   * 当前id
   */
  private String currentUserId;

  /**
   * 评论或回复id
   */
  private String commentId;

  /**
   * 主贴id
   */
  private String postId;

  /**
   * 类型 0评论  1回复
   */
  private String relationType;
}
