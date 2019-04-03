package pers.wong.kec.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.dao.dao.NewsMapper;
import pers.wong.kec.domain.entity.News;
import pers.wong.kec.domain.requestdto.NewsDTO;
import pers.wong.kec.service.NewsService;
import tk.mybatis.mapper.entity.Example;

/**
 * @author Wangjunwei
 * @Date 2019/3/26 17:28
 * @Description
 */
@Service
public class NewsServiceImpl implements NewsService {

  @Resource
  NewsMapper newsMapper;

  @Override
  public Result insertNews(NewsDTO newsDTO) {
    boolean validName = this.validName(newsDTO.getTitle());
    if (!validName) {
      return Result.failed(ResultEnum.ILLEGAL_PARAMETER, "新闻名称只有空格或新闻名称重复");
    }
    News news = new News();
    news.setId(CommonUtil.getUUID());
    news.setTitle(newsDTO.getTitle());
    news.setContent(newsDTO.getContent());
    news.setCreateTime(new Date());
    news.setStatus("0");
    news.setImageUrl(newsDTO.getImageUrl());
    newsMapper.insertSelective(news);
    return Result.success();
  }

  @Override
  public Result getNewsList(NewsDTO newsDTO) {
    PageInfo<NewsDTO> result = PageHelper
        .startPage(newsDTO.getPage().getPageNum(), newsDTO.getPage().getPageSize(), true)
        .doSelectPageInfo(
            () -> this.newsMapper.getNewsList(newsDTO)
        );
    return Result.success(result);
  }

  @Override
  public Result deleteNews(String newsId) {
    News news = newsMapper.selectByPrimaryKey(newsId);
    if (KecAllEnum.STATUS_NORMAL.getCode().equals(news.getStatus())) {
      news.setStatus(KecAllEnum.STATUS_DELETE.getCode());
    }
    int i = newsMapper.updateByPrimaryKeySelective(news);
    return Result.success();
  }

  @Override
  public Result getNewsDetail(String newsId) {
    if (CommonUtil.isEmptyOrNull(newsId)) {
      return Result.failed(ResultEnum.ILLEGAL_PARAMETER, "参数错误");
    }
    News news = newsMapper.selectByPrimaryKey(newsId);
    if (null == news) {
      return Result.failed(ResultEnum.RESULT_EMPTY, "数据不存在");
    }
    return Result.success(news);
  }

  private boolean validName(String name) {
    if ("".equals(name)) {
      return false;
    }
    Example example = new Example(News.class);
    example.createCriteria().andEqualTo("title", name).andEqualTo("status", "0");
    List<News> modules = this.newsMapper.selectByExample(example);
    if (modules.size() > 0) {
      return false;
    }
    return true;
  }
}
