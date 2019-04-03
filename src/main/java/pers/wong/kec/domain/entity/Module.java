package pers.wong.kec.domain.entity;

import javax.persistence.*;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class Module extends BaseObject {
    /**
     *
     */
    private String name;

    /**
     * 管理员
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 板块简介
     */
    private String summary;

    /**
     * 板块类型  0校园服务 1学习交流  2娱乐交友  3其它板块
     */
    private String type;
}