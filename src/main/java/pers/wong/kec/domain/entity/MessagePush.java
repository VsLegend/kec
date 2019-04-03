package pers.wong.kec.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "message_push")
public class MessagePush {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 接收人id
     */
    private String receiver;

    /**
     * 发送人id
     */
    private String sender;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 与type对应的关联id
     */
    @Column(name = "relation_id")
    private String relationId;

    /**
     *    * 消息类型：
     *    * 0系统消息 new_id
     *    * 1帖子消息 post_id
     *    * 2评论消息commnet_id
     *    * 3回复消息 comment_id
     */
    @Column(name = "message_type")
    private String messageType;

    /**
     * 操作类型：0新增  1删除  2修改
     */
    @Column(name = "operation_type")
    private String operationType;

    /**
     * 是否查看 0否 1是
     */
    @Column(name = "read_flag")
    private String readFlag;

    /**
     * 状态 0 正常 1 删除
     */
    private String status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 更新用户id
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

}