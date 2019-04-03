package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestdto.ModuleDTO;

/**
 * @author Wangjunwei
 * @Date 1/25/2019 4:20 PM
 * @Description
 */
public interface ModuleService {

  //新增板块
  Result insertModule(ModuleDTO moduleDTO);

  //获取板块列表
  Result getModuleList(ModuleDTO moduleDTO);

  //删除模块
  Result deletaModule(String moduleId);

  /**
   * 获取某一个板块的详情信息
   */
  Result getModuleDetail(String moduleId);

  /**
   * 更新板块管理员
   */
  Result updateModule(ModuleDTO moduleDTO);
}
