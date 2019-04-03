package pers.wong.kec.domain.requestdto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 1/24/2019 8:18 PM
 * @Description
 */
@Data
public class UserRequestDTO {

  private String name;

  /**
   * 邮箱用于确认信息，找回密码
   */
  private String email;

  /**
   * 头像url
   */
  private String avatar;

  @NotBlank(message = "密码不能为空")
  private String password;

  /**
   * 学号，用于认证是否为本校学生。一个学号只能注册一个账号。
   */
  @NotBlank(message = "学号不能为空")
  private String studentno;

  /**
   * 性别
   */
  private String gender;

  private String type;
}
