package pers.wong.kec.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.dao.dao.UserMapper;
import pers.wong.kec.domain.entity.User;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.domain.requestdto.UserRequestDTO;
import pers.wong.kec.domain.responsedto.UserResponseDTO;
import pers.wong.kec.service.UserService;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 14:56
 * @Description
 */

@Service
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;

  @Override
  public Result signUp(UserRequestDTO userRequestDTO) {
    //判断学号是否重复
    User user = new User();
    Example example = new Example(User.class);
    example.createCriteria().andEqualTo("studentno", userRequestDTO.getStudentno())
        .andNotEqualTo("status", "1");
    List<User> users = userMapper.selectByExample(example);
    if (users.size() != 0) {
      return Result.failed(ResultEnum.STUDENT_NUMBER_BEEN_OCCUPIED, "该学号已被占用");
    }
    user.setId(CommonUtil.getUUID());
    user.setStudentno(userRequestDTO.getStudentno());
    user.setAvatar(userRequestDTO.getAvatar());
    user.setEmail(userRequestDTO.getEmail());
    user.setGender(userRequestDTO.getGender());
    user.setName(userRequestDTO.getName());
    //密码加盐加密存放
    user.setPassword(CommonUtil.encodePassWord(userRequestDTO.getPassword()));
    user.setType("0");
    user.setStatus("0");
    user.setCreateTime(new Date());
    userMapper.insertSelective(user);
    UserResponseDTO dto = new UserResponseDTO();
    dto.setId(user.getId());
    dto.setName(user.getName());
    return Result.success(dto);
  }

  @Override
  public Result login(UserRequestDTO userRequestDTO) {
    String studentno = userRequestDTO.getStudentno();
    String password = userRequestDTO.getPassword();
    String type = userRequestDTO.getType();
    password = CommonUtil.encodePassWord(password);
    Example example = new Example(User.class);
    example.createCriteria().andEqualTo("studentno", studentno).andEqualTo("type", type);
    example.setOrderByClause("create_time desc");
    List<User> users = userMapper.selectByExample(example);
    if (null == users || users.size() == 0) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "未查询到该账号");
    }
    User user = users.get(0);
    //封号处理
    if (KecAllEnum.STATUS_DELETE.getCode().equals(user.getStatus())) {
      return Result.failed(ResultEnum.USER_HAS_BEEN_FORBIDDEN, "用户已被封号");
    }
    //密码错误
    if (!password.equals(user.getPassword())) {
      return Result.failed(ResultEnum.USER_PASSWORD_ERROR, "密码错误");
    }
    UserResponseDTO userResponseDTO = new UserResponseDTO();
    userResponseDTO.setId(user.getId());
    userResponseDTO.setName(user.getName());
    userResponseDTO.setStudentno(user.getStudentno());
    userResponseDTO.setEmail(user.getEmail());
    userResponseDTO.setCreateTime(user.getCreateTime());
    userResponseDTO.setGender(user.getGender());
    userResponseDTO.setType(user.getType());
    return Result.success(userResponseDTO);
  }

  @Override
  public Result changePassword(UserResponseDTO userResponseDTO) {
    String oldPassword = CommonUtil.encodePassWord(userResponseDTO.getOldPassword());
    User user = userMapper.selectByPrimaryKey(userResponseDTO.getId());
    //密码错误
    if (!oldPassword.equals(user.getPassword())) {
      return Result.failed(ResultEnum.USER_PASSWORD_ERROR, "密码输入错误");
    }
    if (CommonUtil.isEmptyOrNull(userResponseDTO.getNewPassword())
        || !userResponseDTO.getNewPassword().equals(userResponseDTO.getReNewPassword())) {
      return Result.failed(ResultEnum.USER_PASSWORD_ERROR, "密码不一致");
    }
    //加密
    String newPassword = CommonUtil.encodePassWord(userResponseDTO.getNewPassword());
    user.setPassword(newPassword);
    userMapper.updateByPrimaryKey(user);
    return Result.success();
  }

  @Override
  public Result getUserList(SearchRequestDTO searchRequestDTO) {
    PageInfo<UserResponseDTO> pageInfo = PageHelper
        .startPage(searchRequestDTO.getPageNum(), searchRequestDTO.getPageSize()).doSelectPageInfo(
            () -> userMapper.getUserList(searchRequestDTO)
        );
    return Result.success(pageInfo);
  }

  @Override
  public Result getUserDetail(String userId) {
    User user = userMapper.selectByPrimaryKey(userId);
    return Result.success(user);
  }

  @Override
  public Result deleteUserStatus(String userId) {
    //如果正常则删除，如果删除则恢复正常
    User user = userMapper.selectByPrimaryKey(userId);
    if (KecAllEnum.STATUS_NORMAL.getCode().equals(user.getStatus())) {
      user.setStatus(KecAllEnum.STATUS_DELETE.getCode());
      //封号时，需判断当前用户是否为板块管理员，是，需提示
      //TODO
    } else {
      user.setStatus(KecAllEnum.STATUS_NORMAL.getCode());
    }
    return Result.success(userMapper.updateByPrimaryKeySelective(user) > 0 ? "用户修改成功" : "用户修改失败");
  }

  @Override
  public Result changeUserInfo(UserResponseDTO userResponseDTO) {
    User user = userMapper.selectByPrimaryKey(userResponseDTO.getId());
    user.setUpdateTime(new Date());
    user.setUpdateUser(userResponseDTO.getId());
    if (CommonUtil.isEmptyOrNull(userResponseDTO.getName())) {
      return Result.failed(ResultEnum.USER_NAME_IS_NULL, "输入的用户名为空");
    }
    user.setName(userResponseDTO.getName());
    user.setGender(userResponseDTO.getGender());
    user.setEmail(userResponseDTO.getEmail());
    userMapper.updateByPrimaryKey(user);
    return Result.success();
  }
}
