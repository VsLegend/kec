package pers.wong.kec.domain.requestdto;

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
public class ModuleDTO {

  //板块id
  private String id;

  @Length(min = 2, max = 10)
  private String name;

  /**
   * 管理员
   */
  private String userId;

  private String userName;
  /**
   * 板块简介
   */
  private String summary;

  /**
   * 板块类型  0校园服务 1学习交流  2娱乐交友  3其它板块
   */
  private String type;

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
