package pers.wong.kec.dao.dao;

import java.util.List;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.News;
import pers.wong.kec.domain.requestDTO.NewsDTO;

public interface NewsMapper extends MyMapper<News> {

  List<NewsDTO> getNewsList(NewsDTO newsDTO);
}