package pers.wong.kec.controller.user;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Common;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.service.ModuleService;
import pers.wong.kec.service.PostService;
import pers.wong.kec.service.UserService;

/**
 * @author Wangjunwei
 * @Date 1/23/2019 6:03 PM
 * @Description 用户登录注册操作
 */

@RestController
@RequestMapping("/userDisplay")
public class UserSecurityController {

  @Resource
  private UserService userService;

  @Resource
  private ModuleService moduleService;

  @Resource
  private PostService postService;

  //获取登录用户的信息
  @GetMapping("/getLoginInfo")
  public Result getLoginInfo(HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_ADMIN_KEY);
    if (id == null) {
      id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
      if (id == null) {
        return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
      }
    }
    return userService.getUserDetail(id.toString());
  }


  @GetMapping("/getUserDetail/{userId}")
  public Result getUserDetail(@PathVariable("userId") String userId) {
    return userService.getUserDetail(userId);
  }
}
