package pers.wong.kec.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestdto.PostContentRequestDTO;
import pers.wong.kec.service.PostService;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:04
 * @Description
 */

@RestController
@RequestMapping("/userPostOperation")
public class UserPostController {

  @Resource
  PostService postService;

  @PostMapping("/focusPost")
  public Result focusPost(@RequestBody PostContentRequestDTO postContentRequestDTO,
      HttpServletRequest request) {
    postContentRequestDTO.setUserId(CommonUtil.getCurrentUserId(request));
    return postService.focusPost(postContentRequestDTO);
  }
}
