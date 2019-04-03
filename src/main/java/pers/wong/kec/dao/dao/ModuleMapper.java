package pers.wong.kec.dao.dao;

import java.util.List;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.Module;
import pers.wong.kec.domain.requestdto.ModuleDTO;

public interface ModuleMapper extends MyMapper<Module> {

  List<ModuleDTO> getModuleList(ModuleDTO moduleDTO);
}