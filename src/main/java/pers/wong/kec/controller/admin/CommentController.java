package pers.wong.kec.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Common;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.domain.requestdto.CommentRequestDTO;
import pers.wong.kec.service.CommentService;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:04
 * @Description
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

  @Resource
  CommentService commentService;


  @PostMapping("/insertComment")
  public Result insertComment(@RequestBody CommentRequestDTO commentRequestDTO, HttpServletRequest request) {
    Object id = request.getSession().getAttribute(Common.SESSION_ADMIN_KEY);
    if (id == null) {
      id = request.getSession().getAttribute(Common.SESSION_USER_KEY);
      if (id == null) {
        return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
      }
    }
    commentRequestDTO.setUserId(id.toString());
    return commentService.insertComment(commentRequestDTO);
  }

}
