package pers.wong.kec.dao.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.UserAttention;
import pers.wong.kec.domain.responseDTO.UserAttentionResponseDTO;

public interface UserAttentionMapper extends MyMapper<UserAttention> {

  UserAttention getFollowInfo(@Param("userId") String userId, @Param("postId") String postId);

  /**
   * 获取用户帖子关注信息 未完成
   */
  List<UserAttentionResponseDTO> getUserAttentionList(String userId);
}