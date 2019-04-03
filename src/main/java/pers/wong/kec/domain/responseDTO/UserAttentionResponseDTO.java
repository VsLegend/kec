package pers.wong.kec.domain.responseDTO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/3/15 18:15
 * @Description 用户帖子关注信息
 */
@Data
public class UserAttentionResponseDTO implements Serializable {

  private String postId;

  /**
   * 帖子标题
   */
  private String title;

  private String postStatus;

  /**
   * 帖子内容
   */
  private String postContent;

  /**
   * 新增的评论条数
   */
  private String newComment;

  private String moduleId;

  private String moduleName;

  private String moduleStatus;
}
