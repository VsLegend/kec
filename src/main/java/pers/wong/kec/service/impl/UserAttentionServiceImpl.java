package pers.wong.kec.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.dao.dao.PostMapper;
import pers.wong.kec.dao.dao.UserAttentionMapper;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.entity.UserAttention;
import pers.wong.kec.domain.requestDTO.PageRequestDTO;
import pers.wong.kec.domain.responseDTO.UserAttentionResponseDTO;
import pers.wong.kec.service.UserAttentionService;

/**
 * @author Wangjunwei
 * @Date 2019/3/15 16:59
 * @Description
 */

@Service
public class UserAttentionServiceImpl implements UserAttentionService {

  @Resource
  UserAttentionMapper userAttentionMapper;

  @Resource
  PostMapper postMapper;

  @Override
  public Result followPost(String userId, String postId) {
    //插入成功，将该帖子变为已关注（当发帖人不是自身时）,且关注数据只有一条
    UserAttention followInfo = userAttentionMapper.getFollowInfo(userId, postId);
    if (null != followInfo) {
      return Result.success();
    }
    //发帖人为自身时，不关注
    Post post = postMapper.selectByPrimaryKey(postId);
    if (userId.equals(post.getUserId())) {
      return Result.success();
    }
    UserAttention userAttention = new UserAttention();
    userAttention.setId(CommonUtil.getUUID());
    userAttention.setUserId(userId);
    userAttention.setPostId(postId);
    userAttention.setCreateTime(new Date());
    userAttention.setStatus("0");
    userAttentionMapper.insertSelective(userAttention);
    return Result.success();
  }

  @Override
  public Result getUserAttentionList(PageRequestDTO pageRequestDTO, String userId) {
    PageInfo<UserAttentionResponseDTO> objectPageInfo = PageHelper
        .startPage(pageRequestDTO.getPageNum(), pageRequestDTO.getPageSize(), true)
        .doSelectPageInfo(() -> userAttentionMapper.getUserAttentionList(userId));
    return Result.success(objectPageInfo);
  }

  @Override
  public Result getUserHasPostedList(PageRequestDTO pageRequestDTO, String userId) {
    PageInfo<UserAttentionResponseDTO> userHasPosted = PageHelper
        .startPage(pageRequestDTO.getPageNum(), pageRequestDTO.getPageSize(), true)
        .doSelectPageInfo(() -> postMapper.getUserHasPostedList(userId));
    return Result.success(userHasPosted);
  }
}
