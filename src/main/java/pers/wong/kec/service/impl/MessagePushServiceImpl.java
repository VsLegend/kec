package pers.wong.kec.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.dao.dao.CommentMapper;
import pers.wong.kec.dao.dao.MessagePushMapper;
import pers.wong.kec.dao.dao.NewsMapper;
import pers.wong.kec.dao.dao.PostMapper;
import pers.wong.kec.domain.entity.Comment;
import pers.wong.kec.domain.entity.MessagePush;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.requestDTO.PageRequestDTO;
import pers.wong.kec.domain.responseDTO.MessagePushResponseDTO;
import pers.wong.kec.domain.responseDTO.MessageRelationResponseDTO;
import pers.wong.kec.service.MessagePushService;

/**
 * @author Wangjunwei
 * @Date 2019/3/17 19:58
 * @Description
 */
@Service
public class MessagePushServiceImpl implements MessagePushService {

  @Resource
  MessagePushMapper messagePushMapper;

  @Resource
  NewsMapper newsMapper;

  @Resource
  PostMapper postMapper;

  @Resource
  CommentMapper commentMapper;

  @Override
  public Result selectUserMessage(PageRequestDTO pageRequestDTO, String userId) {
    PageInfo<MessagePushResponseDTO> messagePush = PageHelper
        .startPage(pageRequestDTO.getPageNum(), pageRequestDTO.getPageSize(), true)
        .doSelectPageInfo( () -> messagePushMapper.selectUserMessage(userId));
    List<MessagePushResponseDTO> list = messagePush.getList();
    for (int i = 0; i < list.size(); i++) {
      String type = list.get(i).getMessageType();
      String relationId = list.get(i).getRelationId();
      MessageRelationResponseDTO messageInfo = null;
      if (KecAllEnum.MESSAGE_TYPE_SYS.getCode().equals(type)) {
        //获取新闻信息
        //TODO:
      } else if (KecAllEnum.MESSAGE_TYPE_POST.getCode().equals(type)) {
        //获取主贴信息
        messageInfo = postMapper
            .selectPostInfo(relationId);
      } else if (KecAllEnum.MESSAGE_TYPE_COMMENT.getCode().equals(type)) {
        //获取评论信息
        messageInfo = commentMapper
            .selectCommentInfo(relationId);
      } else {
        return Result.failed(ResultEnum.ILLEGAL_PARAMETER, "数据参数错误");
      }
      list.get(i).setMessageRelationResponseDTO(messageInfo);
    }
    messagePush.setList(list);
    return Result.success(messagePush);
  }

  @Override
  public Result getNumOfUnreadMessage(String toString) {
    return Result.success(messagePushMapper.getNumOfUnreadMessage(toString));
  }

  @Override
  public boolean deletePost(Post post, String sender) {
//    Example example = new Example(MessagePush.class);
//    example.createCriteria().andEqualTo("messageType", KecAllEnum.MESSAGE_TYPE_POST)
//        .andEqualTo("relation_id", postId).andEqualTo("status", "0");
//    List<MessagePush> messagePushes = messagePushMapper.selectByExample(example);
    String content = "主贴《" + post.getTitle() + "》已被管理员删除";
    insertMessagePush(post.getUserId(), post.getId(), content,sender,
        KecAllEnum.MESSAGE_TYPE_POST.getCode(), KecAllEnum.OPERATION_TYPE_DELETE.getCode());
    return true;
  }

  @Override
  public boolean recoverPost(Post post, String sender) {
    String content = "主贴《" + post.getTitle() + "》已被管理员恢复";
    insertMessagePush(post.getUserId(), post.getId(), content, sender,
        KecAllEnum.MESSAGE_TYPE_POST.getCode(), KecAllEnum.OPERATION_TYPE_UPDATE.getCode());
    return true;
  }

  @Override
  public boolean commentPost(Post post, String sender) {
    String content = "你的主贴《" + post.getTitle() + "》有人评论";
    insertMessagePush(post.getUserId(), post.getId(), content, sender,
        KecAllEnum.MESSAGE_TYPE_POST.getCode(), KecAllEnum.OPERATION_TYPE_INSERT.getCode());
    return true;
  }

  @Override
  public boolean replyComment(Comment comment, String receiver, String relationId) {
    String content = "你的评论'" + comment.getContent() + "'有人回复";
    insertMessagePush(receiver, relationId, content,
        comment.getUserId(), KecAllEnum.MESSAGE_TYPE_COMMENT.getCode(), KecAllEnum.OPERATION_TYPE_INSERT.getCode());
    return true;
  }

  @Override
  public Result messageRead(String messageId) {
    MessagePush messagePush = messagePushMapper.selectByPrimaryKey(messageId);
    if (null == messagePush){
      return Result.failed(ResultEnum.RESULT_EMPTY, "未找到该条记录");
    }
    messagePush.setReadFlag(KecAllEnum.MESSAGE_READ_FLAG_TRUE.getCode());
    messagePushMapper.updateByPrimaryKeySelective(messagePush);
    return Result.success();
  }

  //插入方法
  private MessagePush insertMessagePush(String receiver, String relationId, String content, String sender
      , String messageType, String operationType) {
    MessagePush messagePush = new MessagePush();
    messagePush.setId(CommonUtil.getUUID());
    //接收人id
    messagePush.setReceiver(receiver);
    messagePush.setSender(sender);
    messagePush.setContent(content);
    messagePush.setReadFlag(KecAllEnum.MESSAGE_READ_FLAG_FALSE.getCode());
    //关联id，关联主贴，评论或新闻
    messagePush.setRelationId(relationId);
    //关联类型以及操作类型
    messagePush.setMessageType(messageType);
    messagePush.setOperationType(operationType);
    messagePush.setStatus(KecAllEnum.STATUS_NORMAL.getCode());
    messagePush.setCreateTime(new Date());
    messagePushMapper.insertSelective(messagePush);
    return messagePush;
  }
}
