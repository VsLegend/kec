package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.domain.requestdto.UserRequestDTO;
import pers.wong.kec.domain.responsedto.UserResponseDTO;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 14:48
 * @Description
 */
public interface UserService {

  /**
   * 注册
   */
  Result signUp(UserRequestDTO userRequestDTO);

  /**
   * 登录
   */
  Result login(UserRequestDTO userRequestDTO);

  /**
   * 修改密码
   */
  Result changePassword(UserResponseDTO userResponseDTO);

  /**
   * 根据用户的学号或者名字查询学员
   */
  Result getUserList(SearchRequestDTO searchRequestDTO);

  /**
   * 获取某个用户的详情信息
   */
  Result getUserDetail(String userId);

  /**
   * 改变用户状态
   */
  Result deleteUserStatus(String userId);

  /**
   * 修改用户基本信息
   */
  Result changeUserInfo(UserResponseDTO userResponseDTO);
}
