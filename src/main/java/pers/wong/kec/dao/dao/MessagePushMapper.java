package pers.wong.kec.dao.dao;

import java.util.List;
import pers.wong.kec.domain.entity.MessagePush;
import pers.wong.kec.domain.responseDTO.MessagePushResponseDTO;
import tk.mybatis.mapper.common.Mapper;

public interface MessagePushMapper extends Mapper<MessagePush> {

  /**
   * 查询用户的信息
   */
  List<MessagePushResponseDTO> selectUserMessage(String userId);

  /**
   * 用户的未读信息
   */
  Integer getNumOfUnreadMessage(String userId);
}