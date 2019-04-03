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
public class UserResponseDTO implements Serializable {

  private String id;

  private String name;

  /**
   * 邮箱用于确认信息，找回密码
   */
  private String email;

  /**
   * 头像url
   */
  private String avatar;

  /**
   * 学号，用于认证是否为本校学生。一个学号只能注册一个账号。
   */
  private String studentno;

  /**
   * 性别
   */
  private String gender;

  /**
   * 用户类型，0普通用户 （版主也为普通用户，版主是拥有某项权限的普通用户）  1系统管理员
   */
  private String type;

  /**
   * 状态，0正常  1删除
   */
  private String status;

  /**
   * 创建时间
   */
  private Date createTime;

  private String oldPassword;

  private String newPassword;

  private String reNewPassword;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date getCreateTime() {
    return createTime;
  }
}
