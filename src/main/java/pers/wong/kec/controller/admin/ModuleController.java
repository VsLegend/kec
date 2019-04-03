package pers.wong.kec.controller.admin;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.ModuleDTO;
import pers.wong.kec.service.ModuleService;

/**
 * @author Wangjunwei
 * @Date 1/25/2019 4:14 PM
 * @Description
 */

@RestController
@RequestMapping("/module")
public class ModuleController {

  @Resource
  private ModuleService moduleService;

  //新增板块
  @PostMapping("/insertModule")
  public Result insertModule(@RequestBody @Valid ModuleDTO moduleDTO) {
    return moduleService.insertModule(moduleDTO);
  }

  //更新板块管理员
  @PostMapping("/updateModule")
  public Result updateModule(@RequestBody ModuleDTO moduleDTO) {
    return moduleService.updateModule(moduleDTO);
  }

  @PostMapping("/getModuleList")
  public Result getModuleList(@RequestBody ModuleDTO moduleDTO) {
    return moduleService.getModuleList(moduleDTO);
  }

  @GetMapping("/deleteModule/{moduleId}")
  public Result deletaModule(@PathVariable("moduleId") String moduleId) {
    return moduleService.deletaModule(moduleId);
  }

  @GetMapping("/getModuleDetail/{moduleId}")
  public Result getModuleDetail(@PathVariable("moduleId") String moduleId) {
    return moduleService.getModuleDetail(moduleId);
  }
}
