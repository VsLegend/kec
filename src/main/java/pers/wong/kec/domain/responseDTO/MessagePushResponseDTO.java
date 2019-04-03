package pers.wong.kec.domain.responseDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Wangjunwei
 * @Date 2019/1/4 15:13
 * @Description
 */

@Data
public class MessagePushResponseDTO implements Serializable {

  private String messageId;

  /**
   * 接收人
   */
  private String receiver;

  /**
   * 发送人
   */
  private String sender;

  /**
   * 内容
   */
  private String content;

  /**
   * 关联id
   */
  private String relationId;

  /**
   * 对应的名称新闻标题， 主贴标题， 评论内容
   */
  private MessageRelationResponseDTO messageRelationResponseDTO;

  /**
   * 消息类型：
   * 0系统消息 new_id
   * 1帖子消息 post_id
   * 2评论消息commnet_id
   * 3回复消息 comment_id
   */
  private String messageType;

  /**
   * 操作类型：0新增  1删除  2修改
   */
  private String operationType;

  /**
   * 是否查看 0否 1是
   */
  private String readFlag;

  /**
   * 状态，0正常  1删除
   */
  private String status;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 操作用户
   */
  private String updateUser;

  private Integer numOfUnread;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
  public Date getCreateTime() {
    return createTime;
  }
}
