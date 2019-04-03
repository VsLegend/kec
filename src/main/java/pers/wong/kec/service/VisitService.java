package pers.wong.kec.service;

import pers.wong.kec.common.Result;

/**
 * @author Wangjunwei
 * @Date 2019/3/26 17:27
 * @Description
 */
public interface VisitService {

  /**
   * 插入新闻公告
   */
  Result insertVisit(String postId, String userId);

  int getVisitNum(String postId);
}
