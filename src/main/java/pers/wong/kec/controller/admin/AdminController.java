package pers.wong.kec.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Wangjunwei
 * @Date 2019/1/28 19:25
 * @Description
 */
@Controller
@RequestMapping("/backstage")
public class AdminController {


  //管理员登录页
  @GetMapping("/login")
  public ModelAndView login() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/login");
    return model;
  }

  //新闻中心
  @GetMapping("/admin")
  public ModelAndView admin() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/admin");
    return model;
  }

  //新增或修改新闻
  @GetMapping("/addNews")
  public ModelAndView addNews() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/addNews");
    return model;
  }

  //管理员板块管理
  @GetMapping("/moduleManeger")
  public ModelAndView moduleManeger() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/moduleManeger");
    return model;
  }

  //添加新板块
  @GetMapping("/addNewModule")
  public ModelAndView addNewModule() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/addNewModule");
    return model;
  }

  //用户管理
  @GetMapping("/controlUser")
  public ModelAndView controlUser() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/controlUser");
    return model;
  }

  //帖子分类展示
  @GetMapping("/postClassification")
  public ModelAndView postControl() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/postClassification");
    return model;
  }

  //帖子管理
  @GetMapping("/controlPost")
  public ModelAndView controlPost() {
    ModelAndView model = new ModelAndView();
    model.setViewName("admin/controlPost");
    return model;
  }

  //帖子详情
  @GetMapping("/postDetail")
  public ModelAndView postDetail() {
    ModelAndView model = new ModelAndView();
    model.setViewName("post/postDetail");
    return model;
  }

  //管理员新增帖子
  @GetMapping("/addNewPost")
  public ModelAndView addNewPost() {
    ModelAndView model = new ModelAndView();
    model.setViewName("post/addNewPost");
    return model;
  }
}
