package pers.wong.kec.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.domain.requestdto.PostContentRequestDTO;
import pers.wong.kec.domain.requestdto.PostDTO;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.service.PostService;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:04
 * @Description
 */

@RestController
@RequestMapping("/post")
public class PostController {

  @Resource
  PostService postService;

  @PostMapping("/getPostList")
  public Result getPostList(@RequestBody SearchRequestDTO searchRequestDTO) {
    return postService.getPostList(searchRequestDTO);
  }

  @PostMapping("/insertPost")
  public Result insertPost(@RequestBody PostDTO postDTO, HttpServletRequest request) {
    String userId = CommonUtil.getCurrentUserId(request);
    if (CommonUtil.isEmptyOrNull(userId)) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    postDTO.setUserId(userId);
    return postService.insertPost(postDTO);
  }

  @PostMapping("/getPostAndComment")
  public Result getPostAndComment(@RequestBody PostContentRequestDTO contentRequestDTO) {
    return postService.getPostAndComment(contentRequestDTO);
  }

  @PostMapping("/getPost/{postId}")
  public Result getPostById(@PathVariable("postId") String postId,
      HttpServletRequest request) {
    String userId = CommonUtil.getCurrentUserId(request);
    if (CommonUtil.isEmptyOrNull(userId)) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return postService.getPostById(postId, userId);
  }

  @GetMapping("/updatePost/{postId}")
  public Result deletePost(@PathVariable("postId") String postId, HttpServletRequest request) {
    String userId = CommonUtil.getCurrentUserId(request);
    if (CommonUtil.isEmptyOrNull(userId)) {
      return Result.failed(ResultEnum.USER_NOT_SIGN_UP, "用户未登录");
    }
    return postService.updatePost(postId, userId);
  }
}
