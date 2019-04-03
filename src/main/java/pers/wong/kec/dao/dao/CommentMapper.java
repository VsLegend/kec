package pers.wong.kec.dao.dao;

import java.util.List;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.Comment;
import pers.wong.kec.domain.responseDTO.CommentResponseDTO;
import pers.wong.kec.domain.responseDTO.MessageRelationResponseDTO;
import pers.wong.kec.domain.responseDTO.ReplyResponseDTO;

public interface CommentMapper extends MyMapper<Comment> {

  /**
   * 获取评论主贴的内容
   */
  List<CommentResponseDTO> getPostCommentInfo(String postId);


  /**
   * 获取回复评论的内容
   */
  List<ReplyResponseDTO> getCommentReplyInfo(String commentId);

  /**
   * 当为回复时，查找其主贴id
   */
  String getReplyPostId(String commentId);

  /**
   * 评论的基本信息
   */
  MessageRelationResponseDTO selectCommentInfo(String commentId);

  /**
   * 获取主贴（在d天内）的评论数
   */
  int getCommentNum(String postId, int day);
}