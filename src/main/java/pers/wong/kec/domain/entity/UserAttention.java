package pers.wong.kec.domain.entity;

import javax.persistence.*;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class UserAttention extends BaseObject {
    /**
     * 关注人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 帖子id
     */
    @Column(name = "post_id")
    private String postId;
}