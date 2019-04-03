package pers.wong.kec.dao.dao;

import java.util.List;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.User;
import pers.wong.kec.domain.requestDTO.SearchRequestDTO;
import pers.wong.kec.domain.responseDTO.UserResponseDTO;


public interface UserMapper extends MyMapper<User> {

  List<UserResponseDTO> getUserList(SearchRequestDTO searchRequestDTO);
}