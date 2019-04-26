package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestdto.NewsDTO;

/**
 * @author Wangjunwei
 * @Date 2019/3/26 17:27
 * @Description
 */
public interface NewsService {

  /**
   * 插入新闻公告
   */
  Result insertNews(NewsDTO newsDTO);

  /**
   * 获取新闻列表
   */
  Result getNewsList(NewsDTO newsDTO);

  /**
   * 删除新闻呢
   */
  Result deleteNews(String newsId);

  /**
   * 获取新闻详情
   */
  Result getNewsDetail(String newsId);

  /**
   * 编辑新闻
   */
  Result updateNews(NewsDTO newsDTO);
}
