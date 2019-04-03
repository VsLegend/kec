package pers.wong.kec.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.dao.dao.CommentMapper;
import pers.wong.kec.dao.dao.PostMapper;
import pers.wong.kec.dao.dao.UserAttentionMapper;
import pers.wong.kec.dao.dao.UserMapper;
import pers.wong.kec.domain.entity.Comment;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.entity.UserAttention;
import pers.wong.kec.domain.requestDTO.CommentRequestDTO;
import pers.wong.kec.service.CommentService;
import pers.wong.kec.service.MessagePushService;

/**
 * @author Wangjunwei
 * @Date 2019/3/14 11:27
 * @Description
 */

@Service
public class CommentServiceImpl implements CommentService {

  @Resource
  CommentMapper commentMapper;

  @Resource
  UserMapper userMapper;

  @Resource
  UserAttentionMapper userAttentionMapper;

  @Resource
  PostMapper postMapper;

  @Resource
  MessagePushService messagePushService;

  @Override
  public Result insertComment(CommentRequestDTO commentRequestDTO) {
    Comment comment = insert(commentRequestDTO);
    //插入成功，将该帖子变为已关注（当发帖人不是自身时）,关注数据只有一条
    String postId = null;
    if ("0".equals(comment.getRelationType())) {
      //评论主贴，接收人为主贴发布者，关联id为主贴id
      postId = comment.getRelationId();
      messagePushService.commentPost(postMapper.selectByPrimaryKey(postId), comment.getUserId());
    } else {
      //回复评论,接收人为评论者，关联id为评论id
      postId = commentMapper.getReplyPostId(comment.getId());
      Comment key = commentMapper.selectByPrimaryKey(comment.getRelationId());
      messagePushService.replyComment(comment, key.getUserId(), key.getId());
    }
    //查看是否已经关注
    UserAttention followInfo = userAttentionMapper.getFollowInfo(comment.getUserId(), postId);
    if (null != followInfo) {
      return Result.success();
    }
    //发帖人是否为自身
    Post post = postMapper.selectByPrimaryKey(postId);
    if (comment.getUserId().equals(post.getUserId())) {
      return Result.success();
    }
    UserAttention userAttention = new UserAttention();
    userAttention.setId(CommonUtil.getUUID());
    userAttention.setUserId(comment.getUserId());
    userAttention.setPostId(postId);
    userAttention.setCreateTime(new Date());
    userAttention.setStatus("0");
    userAttentionMapper.insertSelective(userAttention);
    return Result.success();
  }

  @Override
  public int getCommentNum(String postId, int day) {
    return commentMapper.getCommentNum(postId, day);
  }

  //插入语句
  private Comment insert(CommentRequestDTO commentRequestDTO) {
    Comment comment = new Comment();
    comment.setId(CommonUtil.getUUID());
    comment.setContent(commentRequestDTO.getContent());
    comment.setPostId(commentRequestDTO.getPostId());
    comment.setRelationId(commentRequestDTO.getRelationId());
    comment.setRelationType(commentRequestDTO.getRelationType());
    comment.setUserId(commentRequestDTO.getUserId());
    comment.setUserName(userMapper.selectByPrimaryKey(commentRequestDTO.getUserId()).getName());
    comment.setCreateTime(new Date());
    comment.setStatus("0");
    int i = commentMapper.insertSelective(comment);
    return comment;
  }
}
