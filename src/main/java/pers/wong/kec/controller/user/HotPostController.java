package pers.wong.kec.controller.user;

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
import pers.wong.kec.domain.requestDTO.PostContentRequestDTO;
import pers.wong.kec.domain.requestDTO.PostDTO;
import pers.wong.kec.domain.requestDTO.SearchRequestDTO;
import pers.wong.kec.service.PostService;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:04
 * @Description
 */

@RestController
@RequestMapping("/hotPost")
public class HotPostController {

  @Resource
  PostService postService;

  //热门主贴
  @GetMapping("/getHotPostList")
  public Result getHotPostList() {
    return postService.getHotPostList();
  }


}
