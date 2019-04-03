package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.PageRequestDTO;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 14:48
 * @Description 用户关注
 */
public interface UserAttentionService {

  /**
   * 关注帖子
   */
  Result followPost(String userId, String postId);

  /**
   * 获取用户关注的主贴
   */
  Result getUserAttentionList(PageRequestDTO pageRequestDTO, String userId);

  /**
   * 用户发布的主贴
   */
  Result getUserHasPostedList(PageRequestDTO pageRequestDTO, String userId);
}
