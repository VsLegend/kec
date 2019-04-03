package pers.wong.kec.controller.user;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pers.wong.kec.domain.entity.User;
import pers.wong.kec.service.UserService;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 11:22
 * @Description 用户登录注册页面展示
 */

@Controller
@RequestMapping("/entrance")
public class UserController {

  @GetMapping("/signUp")
  public ModelAndView signUp() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/signUp");
    return model;
  }

  @GetMapping("/login")
  public ModelAndView signIn() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/login");
    return model;
  }

  //板块下的主贴展示
  @GetMapping("/postDisplay")
  public ModelAndView postDisplay() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/postDisplay");
    return model;
  }

  //板块列表展示
  @GetMapping("/moduleDisplay")
  public ModelAndView moduleDisplay() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/moduleDisplay");
    return model;
  }

  //帖子列表详情
  @GetMapping("/needInfo/postDetail")
  public ModelAndView postDetail() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/postDetail");
    return model;
  }

  //我的关注
  @GetMapping("/needInfo/userFocus")
  public ModelAndView userFocus() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/userFocus");
    return model;
  }

  //个人中心
  @GetMapping("/needInfo/personalCenter")
  public ModelAndView personalCenter() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/personalCenter");
    return model;
  }

  //消息中心
  @GetMapping("/needInfo/messageCenter")
  public ModelAndView messageCenter() {
    ModelAndView model = new ModelAndView();
    model.setViewName("userCenter/messageCenter");
    return model;
  }
}
