package pers.wong.kec.controller.user;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Result;
import pers.wong.kec.service.PostService;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:04
 * @Description
 */

@RestController
@RequestMapping("/popular")
public class HotPostController {

  @Resource
  PostService postService;

  //热门主贴
  @GetMapping("/getPopularPostList")
  public Result getPopularPostList() {
    return postService.getPopularPostList();
  }


}
