package pers.wong.kec.controller.admin;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.SearchRequestDTO;
import pers.wong.kec.service.UserService;

/**
 * @author Wangjunwei
 * @Date 2019/2/27 14:45
 * @Description
 */

@RestController
@RequestMapping("/userControl")
public class UserManegerController {

  @Resource
  private UserService userService;

  @PostMapping("/getUserList")
  public Result getUserList(@RequestBody SearchRequestDTO searchRequestDTO) {
    return userService.getUserList(searchRequestDTO);
  }

  @GetMapping("/getUserDetail/{userId}")
  public Result getUserDetail(@PathVariable("userId") String userId) {
    return userService.getUserDetail(userId);
  }

  @GetMapping("/deleteUserStatus/{userId}")
  public Result deleteUserStatus(@PathVariable("userId") String userId) {
    return userService.deleteUserStatus(userId);
  }
}
