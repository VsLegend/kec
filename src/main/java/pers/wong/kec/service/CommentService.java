package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.CommentRequestDTO;

/**
 * @author Wangjunwei
 * @Date 2019/3/14 11:27
 * @Description
 */
public interface CommentService {

  /**
   * 新增评论或回复
   */
  Result insertComment(CommentRequestDTO commentRequestDTO);

  /**
   * 获取主贴的评论数
   */
  int getCommentNum(String postId, int day);
}
