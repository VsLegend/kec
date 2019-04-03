package pers.wong.kec.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Common;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.domain.requestDTO.PageRequestDTO;
import pers.wong.kec.service.MessagePushService;

/**
 * @author Wangjunwei
 * @Date 2019/3/14 11:27
 * @Description
 */
@RestController
@RequestMapping("/messagePush")
public class MessagePushController {

  @Resource
  private MessagePushService messagePushService;

  //查询所有网站信息
  @PostMapping("/selectUserMessage")
  public Result selectUserMessage(@RequestBody PageRequestDTO pageRequestDTO, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return messagePushService.selectUserMessage(pageRequestDTO, id.toString());
  }

  //查询未读信息的条数
  @GetMapping("/getNumOfUnreadMessage")
  public Result getNumOfUnreadMessage(HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
    if (id == null) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return messagePushService.getNumOfUnreadMessage(id.toString());
  }

  //消息已读
  @GetMapping("/messageRead/{messageId}")
  public Result messageRead(@PathVariable("messageId") String messageId) {
    return messagePushService.messageRead(messageId);
  }
}
