package pers.wong.kec.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.dao.dao.ModuleMapper;
import pers.wong.kec.dao.dao.UserMapper;
import pers.wong.kec.domain.entity.Module;
import pers.wong.kec.domain.entity.User;
import pers.wong.kec.domain.requestdto.ModuleDTO;
import pers.wong.kec.service.ModuleService;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Wangjunwei
 * @Date 1/25/2019 4:20 PM
 * @Description
 */

@Service
public class ModuleServiceImpl implements ModuleService {

  @Resource
  ModuleMapper moduleMapper;

  @Resource
  UserMapper userMapper;

  @Override
  public Result insertModule(ModuleDTO moduleDTO) {
    boolean validName = this.validName(moduleDTO.getName());
    if (!validName) {
      return Result.failed(ResultEnum.ILLEGAL_PARAMETER, "板块名称只有空格或名称重复");
    }
    //用户被禁用
    User user = userMapper.selectByPrimaryKey(moduleDTO.getUserId());
    if (KecAllEnum.STATUS_DELETE.equals(user.getStatus())) {
      Result.failed(ResultEnum.USER_HAS_BEEN_FORBIDDEN, "该用户已被封号");
    }
    Module module = new Module();
    module.setId(CommonUtil.getUUID());
    module.setName(moduleDTO.getName());
    module.setSummary(moduleDTO.getSummary());
    module.setType(moduleDTO.getType());
    module.setUserId(moduleDTO.getUserId());
    module.setCreateTime(new Date());
    module.setStatus("0");
    moduleMapper.insertSelective(module);
    return Result.success();
  }

  private boolean validName(String name) {
    if ("".equals(name)) {
      return false;
    }
    Example example = new Example(Module.class);
    example.createCriteria().andEqualTo("name", name).andEqualTo("status", "0");
    List<Module> modules = this.moduleMapper.selectByExample(example);
    if (modules.size() > 0) {
      return false;
    }
    return true;
  }

  @Override
  public Result getModuleList(ModuleDTO moduleDTO) {
    PageInfo<ModuleDTO> result = PageHelper
        .startPage(moduleDTO.getPage().getPageNum(), moduleDTO.getPage().getPageSize(), true)
        .doSelectPageInfo(
            () -> this.moduleMapper.getModuleList(moduleDTO)
        );
    //设置用户名称
    IntStream.range(0, result.getList().size()).forEachOrdered(i -> {
      User user = userMapper.selectByPrimaryKey(result.getList().get(i).getUserId());
      result.getList().get(i).setUserName(user.getName());
    });
    return Result.success(result);
  }

  @Override
  public Result deletaModule(String moduleId) {
    //如果正常则删除，如果删除则恢复正常
    Module module = moduleMapper.selectByPrimaryKey(moduleId);
    if (KecAllEnum.STATUS_NORMAL.getCode().equals(module.getStatus())) {
      module.setStatus(KecAllEnum.STATUS_DELETE.getCode());
    } else {
      module.setStatus(KecAllEnum.STATUS_NORMAL.getCode());
    }
    return Result
        .success(moduleMapper.updateByPrimaryKeySelective(module) > 0 ? "板块删除成功" : "板块删除失败");
  }

  @Override
  public Result getModuleDetail(String moduleId) {
    if (CommonUtil.isEmptyOrNull(moduleId)) {
      return Result.failed(ResultEnum.ILLEGAL_PARAMETER, "无效参数");
    }
    Module module = moduleMapper.selectByPrimaryKey(moduleId);
    User user = userMapper.selectByPrimaryKey(module.getUserId());
    ModuleDTO moduleDTO = new ModuleDTO();
    moduleDTO.setId(moduleId);
    moduleDTO.setName(module.getName());
    moduleDTO.setSummary(module.getSummary());
    moduleDTO.setType(module.getType());
    moduleDTO.setStatus(module.getStatus());
    moduleDTO.setCreateTime(module.getCreateTime());
    moduleDTO.setUserId(user.getId());
    moduleDTO.setUserName(user.getName());
    return Result.success(moduleDTO);
  }

  @Override
  public Result updateModule(ModuleDTO moduleDTO) {
    if (CommonUtil.isEmptyOrNull(moduleDTO.getId())) {
      Result.failed(ResultEnum.ILLEGAL_PARAMETER, "传参错误");
    }
    Module module = moduleMapper.selectByPrimaryKey(moduleDTO.getId());
    if (!moduleDTO.getName().equals(module.getName())) {
      if (!this.validName(moduleDTO.getName())) {
        return Result.failed(ResultEnum.ILLEGAL_PARAMETER, "板块名称只有空格或名称重复");
      }
    }
    //用户被禁用时，不能设置为板块管理员
    User user = userMapper.selectByPrimaryKey(module.getUserId());
    if (null == user) {
      Result.failed(ResultEnum.RESULT_EMPTY, "用户查询失败");
    }
    if (KecAllEnum.STATUS_DELETE.equals(user.getStatus())) {
      Result.failed(ResultEnum.USER_HAS_BEEN_FORBIDDEN, "该用户已被封号");
    }
    module.setUserId(user.getId());
    module.setSummary(moduleDTO.getSummary());
    module.setName(moduleDTO.getName());
    module.setType(moduleDTO.getType());
    moduleMapper.updateByPrimaryKeySelective(module);
    return Result.success();
  }
}
