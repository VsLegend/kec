package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.entity.Comment;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.requestDTO.PageRequestDTO;

/**
 * @author Wangjunwei
 * @Date 2019/3/14 11:27
 * @Description
 */
public interface MessagePushService {

  /**
   * 获取用户的所有信息，未读的放在最前，按时间排倒序
   */
  Result selectUserMessage(PageRequestDTO pageRequestDTO, String userId);

  /**
   * 查询未读信息的条数
   */
  Result getNumOfUnreadMessage(String toString);

  /**
   * 删除主贴时,插入用户提示信息
   */
  boolean deletePost(Post post, String sender);

  /**
   * 恢复主贴时,插入用户提示信息
   */
  boolean recoverPost(Post post, String sender);

  /**
   * 评论主贴时,插入用户提示信息
   */
  boolean commentPost(Post post, String sender);

  /**
   * 回复评论时,插入用户提示信息
   */
  boolean replyComment(Comment comment, String receiver, String relationId);

  /**
   * 消息已读
   */
  Result messageRead(String messageId);
}
