package pers.wong.kec.domain.responsedto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 15:13
 * @Description
 */

@Data
public class PostResponseDTO implements Serializable {

  private String postId;

  private String title;

  private String content;

  private String userName;

  private String userId;

  private String moduleId;

  /**
   * 板块类型
   */
  private String moduleType;

  /**
   * 帖子类型，0普通 1置顶  2精华  3热门
   */
  private String type;

  /**
   * 状态，0正常  1删除
   */
  private String status;

  /**
   * 访问量
   */
  private int visitNum;

  /**
   * 评论量
   */
  private int commentNum;

  /**
   * 创建时间
   */
  private Date createTime;


  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date getCreateTime() {
    return createTime;
  }
}
