package pers.wong.kec.domain.entity;

import javax.persistence.*;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class Post extends BaseObject {

    /**
     * 标题
     */
  private String title;

    /**
     * 内容
     */
  private String content;

    /**
     * 发帖人姓名
     */
  @Column(name = "user_name")
  private String userName;

    /**
     * 发帖人id
     */
  @Column(name = "user_id")
  private String userId;

    /**
     * 板块id
     */
  @Column(name = "module_id")
  private String moduleId;

    /**
     * 帖子类型，0普通 1置顶  2精华  3热门
     */
  private String type;

    /**
     * 热度值
     */
  @Column(name = "popular_index")
  private Double popularIndex;
}