package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.SearchRequestDTO;
import pers.wong.kec.domain.requestDTO.UserRequestDTO;
import pers.wong.kec.domain.responseDTO.UserResponseDTO;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 14:48
 * @Description
 */
public interface UserService {

  /**
   * 注册
   * @param userRequestDTO
   * @return
   */
  Result signUp(UserRequestDTO userRequestDTO);

  /**
   * 登录
   * @param userRequestDTO
   * @return
   */
  Result login(UserRequestDTO userRequestDTO);

  /**
   * 修改密码
   * @param userResponseDTO
   * @return
   */
  Result changePassword(UserResponseDTO userResponseDTO);

  /**
   * 根据用户的学号或者名字查询学员
   * @param searchRequestDTO
   * @return
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
