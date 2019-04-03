package pers.wong.kec.domain.responseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Wangjunwei
 * @Date 2019/3/13 17:19
 * @Description
 */
@Data
public class ReplyResponseDTO {

  private String replyId;

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
   * 主贴id/评论id
   */
  private String relationId;

  /**
   * 0评论主贴  1回复评论
   */
  private String relationType;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date getCreateTime() {
    return createTime;
  }
}
