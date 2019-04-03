package pers.wong.kec.dao.dao;

import pers.wong.kec.domain.entity.Visit;
import tk.mybatis.mapper.common.Mapper;

public interface VisitMapper extends Mapper<Visit> {

  int getVisitNum(String postId);
}