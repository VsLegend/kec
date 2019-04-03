package pers.wong.kec.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class UserFans extends BaseObject {
    /**
     * 被关注人id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 关注人id
     */
    @Column(name = "fans_id")
    private String fansId;
}