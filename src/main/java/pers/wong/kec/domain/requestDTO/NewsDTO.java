package pers.wong.kec.domain.requestDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import pers.wong.kec.domain.entity.Page;

/**
 * @author Wangjunwei
 * @Date 1/25/2019 4:28 PM
 * @Description
 */

@Data
public class NewsDTO {

  //新闻id
  private String id;

  /**
   * 新闻标题
   */
  private String title;

  /**
   * 内容
   */
  private String content;

  /**
   * 封面url
   */
  private String imageUrl;

  /**
   * 状态 0 正常 1 删除
   */
  private String status;

  /**
   * 创建时间
   */
  private Date createTime;

  //分页参数
  private Page page;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date getCreateTime() {
    return createTime;
  }
}
