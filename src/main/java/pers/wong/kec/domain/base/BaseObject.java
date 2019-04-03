package pers.wong.kec.domain.base;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 * 实体基类
 * 
 * @author gaozhiguang
 */
@Data
public class BaseObject implements Serializable {

  /**
   * UUID
   */
  @Id
  @Column(name = "Id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private String id;

  /**
   * 创建时间
   */
  @Column(name = "create_time")
  private Date createTime;

  /**
   * 状态 0 正常 1 删除
   */
  private String status;

  /**
   * 更新时间
   */
  @Column(name = "update_time")
  private Date updateTime;

  /**
   * 更新用户id
   */
  @Column(name = "update_user")
  private String updateUser;
}
