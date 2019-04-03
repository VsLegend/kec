package pers.wong.kec.domain.entity;

import javax.persistence.*;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class Comment extends BaseObject {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人姓名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 评论人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 主贴id
     */
    @Column(name = "post_id")
    private String postId;

    /**
     * 主贴id/评论id
     */
    @Column(name = "relation_id")
    private String relationId;

    /**
     * 0评论主贴  1回复评论
     */
    @Column(name = "relation_type")
    private String relationType;
}