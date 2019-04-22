package pers.wong.kec.controller.user;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Common;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.domain.requestdto.ModuleDTO;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.domain.requestdto.UserRequestDTO;
import pers.wong.kec.domain.responsedto.UserResponseDTO;
import pers.wong.kec.service.ModuleService;
import pers.wong.kec.service.PostService;
import pers.wong.kec.service.UserService;

/**
 * @author Wangjunwei
 * @Date 1/23/2019 6:03 PM
 * @Description 用户登录注册操作
 */

@RestController
@RequestMapping("/user")
public class UserDataController {

  @Resource
  private UserService userService;

  @Resource
  private ModuleService moduleService;

  @Resource
  private PostService postService;


  //注册
  @PostMapping("/signUp")
  public Result signUp(@RequestBody @Valid UserRequestDTO userRequestDTO) {
    return userService.signUp(userRequestDTO);
  }

  //登录
  @PostMapping("/login")
  public Result login(@RequestBody UserRequestDTO userRequestDTO, HttpServletRequest request) {
    Result login = userService.login(userRequestDTO);
    if (login.getCode() == 1000) {
      //将验证数据放入session
      UserResponseDTO data = (UserResponseDTO) (login.getData());
      if (KecAllEnum.ROLE_USER.getCode().equals(data.getType())) {
        request.getSession().setAttribute(Common.SESSION_USER_KEY, data.getId());
        request.getSession().setAttribute(Common.SESSION_USER_TYPE,  data.getType());
      } else if (KecAllEnum.ROLE_ADMIN.getCode().equals(data.getType())) {
        request.getSession().setAttribute(Common.SESSION_ADMIN_KEY, data.getId());
      }
    }
    return login;
  }

  //修改用户基本信息
  @PostMapping("changeUserInfo")
  public Result changeUserInfo(@RequestBody UserResponseDTO userResponseDTO, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    userResponseDTO.setId(id.toString());
    return userService.changeUserInfo(userResponseDTO);
  }

  //修改密码
  @PostMapping("changePassword")
  public Result changePassword(@RequestBody UserResponseDTO userResponseDTO, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    userResponseDTO.setId(id.toString());
    return userService.changePassword(userResponseDTO);
  }

  //获取指定用户详情信息
  @GetMapping("/getUserDetail/{userId}")
  public Result getUserDetail(@PathVariable("userId") String userId, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return userService.getUserDetail(userId);
  }

  //获取登录用户的信息
  @GetMapping("/getLoginInfo")
  public Result getLoginInfo(HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return userService.getUserDetail(id.toString());
  }

  //板块列表
  @PostMapping("/getModuleList")
  public Result getModuleList(@RequestBody ModuleDTO moduleDTO) {
    return moduleService.getModuleList(moduleDTO);
  }

  //主贴列表
  @PostMapping("/getPostList")
  public Result getPostList(@RequestBody SearchRequestDTO searchRequestDTO) {
    return postService.getPostList(searchRequestDTO);
  }

  //板块信息
  @GetMapping("/getModuleDetail/{moduleId}")
  public Result getModuleDetail(@PathVariable("moduleId") String moduleId) {
    return moduleService.getModuleDetail(moduleId);
  }
}
