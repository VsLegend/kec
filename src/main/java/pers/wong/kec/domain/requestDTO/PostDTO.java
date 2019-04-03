package pers.wong.kec.domain.requestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Wangjunwei
 * @Date 2019/3/8 14:46
 * @Description
 */

@Data
public class PostDTO {

  private String postId;

  /**
   * 标题
   */
  private String title;

  /**
   * 内容
   */
  private String content;

  /**
   * 发帖人姓名
   */
  private String userName;

  /**
   * 发帖人id
   */
  private String userId;

  /**
   * 板块id
   */
  private String moduleId;

  /**
   * 帖子类型，0普通 1置顶  2精华  3热门
   */
  private String type;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 状态 0 正常 1 删除
   */
  private String status;

  /**
   * 更新时间
   */
  private Date updateTime;

  /**
   * 更新用户id
   */
  private Date updateUser;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date getCreateTime() {
    return createTime;
  }
}
