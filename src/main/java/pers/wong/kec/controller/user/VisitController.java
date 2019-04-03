package pers.wong.kec.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.service.VisitService;

/**
 * @author Wangjunwei
 * @Date 2019/3/29 15:46
 * @Description
 */

@RestController
@RequestMapping("/visit")
public class VisitController {

  @Resource
  private VisitService visitService;

  @GetMapping("/insertVisit/{postId}")
  public Result insertVisit(@PathVariable("postId") String postId, HttpServletRequest request) {
    String userId = CommonUtil.getCurrentUserId(request);
    if (CommonUtil.isEmptyOrNull(userId)) {
      return Result.failed(ResultEnum.USER_NOT_LOGIN, "用户未登录");
    }
    return visitService.insertVisit(postId, userId);
  }


  @GetMapping("/getVisitNum/{postId}")
  public Result getVisitNum(@PathVariable("postId") String postId) {
    return Result.success(visitService.getVisitNum(postId));
  }
}
