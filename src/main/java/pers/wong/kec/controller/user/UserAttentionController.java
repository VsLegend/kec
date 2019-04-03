package pers.wong.kec.controller.user;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Common;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.domain.requestdto.PageRequestDTO;
import pers.wong.kec.service.UserAttentionService;

/**
 * @author Wangjunwei
 * @Date 1/23/2019 6:03 PM
 * @Description 用户登录之后可用的操作
 */

@RestController
@RequestMapping("/userAttention")
public class UserAttentionController {

  @Resource
  private UserAttentionService userAttentionService;


  //获取用户关注的主贴
  @PostMapping("/getUserAttentionList")
  public Result getUserAttentionList(@RequestBody PageRequestDTO pageRequestDTO, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return userAttentionService.getUserAttentionList(pageRequestDTO, id.toString());
  }

  //用户发布的主贴
  @PostMapping("/getUserHasPostedList")
  public Result getUserHasPostedList(@RequestBody PageRequestDTO pageRequestDTO, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return userAttentionService.getUserHasPostedList(pageRequestDTO, id.toString());
  }
}
