package pers.wong.kec.domain.responseDTO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/3/15 18:15
 * @Description 用户帖子关注信息
 */
@Data
public class PopularPostResponseDTO implements Serializable {

  private String postId;

  /**
   * 主贴的创建时间与当前时间的间隔，单位小时
   */
  private double hours;

  /**
   * 关注人数
   */
  private double follow;

  /**
   * 综合得分
   */
  private double score;

}
