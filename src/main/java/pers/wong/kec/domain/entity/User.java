package pers.wong.kec.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class User extends BaseObject {

    @NotBlank(message = "用户名不能为空")
    private String name;

    /**
     * 邮箱用于确认信息，找回密码
     */
    @Email
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
    @Column(name = "studentNo")
    @NotBlank(message = "学号不能为空")
    private String studentno;

    /**
     * 性别
     */
    private String gender;

    /**
     * 用户类型，0普通用户 1版主  2系统管理员
     */
    private String type;
}