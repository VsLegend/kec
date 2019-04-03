package pers.wong.kec.domain.entity;

import javax.persistence.*;
import lombok.Data;
import pers.wong.kec.domain.base.BaseObject;

@Data
public class News extends BaseObject {

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 封面url
     */
    @Column(name = "image_url")
    private String imageUrl;
}