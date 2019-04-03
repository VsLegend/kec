package pers.wong.kec.domain.responseDTO;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Wangjunwei
 * @Date 2019/3/15 18:15
 * @Description 用户帖子关注信息
 */
@Data
public class MessageRelationResponseDTO implements Serializable {

  private String relationId;

  /**
   * 关联的内容
   */
  private String relationContent;

  private String status;

}
