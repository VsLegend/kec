package pers.wong.kec.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.dao.dao.VisitMapper;
import pers.wong.kec.domain.entity.Visit;
import pers.wong.kec.service.VisitService;

/**
 * @author Wangjunwei
 * @Date 2019/3/29 15:53
 * @Description
 */

@Service
public class VisitServiceImpl implements VisitService {

  @Resource
  VisitMapper visitMapper;

  @Override
  public Result insertVisit(String postId, String userId) {
    Visit visit = new Visit();
    visit.setId(CommonUtil.getUUID());
    visit.setPostId(postId);
    visit.setUserId(userId);
    visit.setCreateTime(new Date());
    visit.setStatus("0");
    int i = visitMapper.insertSelective(visit);
    return Result.success();
  }

  @Override
  public int getVisitNum(String postId) {
    return visitMapper.getVisitNum(postId);
  }
}
