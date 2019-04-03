package pers.wong.kec.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import pers.wong.kec.common.CommonUtil;
import pers.wong.kec.common.Result;
import pers.wong.kec.common.enums.KecAllEnum;
import pers.wong.kec.common.enums.ResultEnum;
import pers.wong.kec.dao.dao.CommentMapper;
import pers.wong.kec.dao.dao.ModuleMapper;
import pers.wong.kec.dao.dao.PostMapper;
import pers.wong.kec.dao.dao.UserMapper;
import pers.wong.kec.domain.entity.Module;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.requestdto.PostContentRequestDTO;
import pers.wong.kec.domain.requestdto.PostDTO;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.domain.responsedto.CommentResponseDTO;
import pers.wong.kec.domain.responsedto.PostResponseDTO;
import pers.wong.kec.service.MessagePushService;
import pers.wong.kec.service.PostService;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:05
 * @Description
 */
@Service
public class PostServiceImpl implements PostService {

  @Resource
  PostMapper postMapper;

  @Resource
  UserMapper userMapper;

  @Resource
  CommentMapper commentMapper;

  @Resource
  ModuleMapper moduleMapper;

  @Resource
  MessagePushService messagePushService;

  @Override
  public Result getPostList(SearchRequestDTO searchRequestDTO) {
    PageInfo<PostResponseDTO> result = PageHelper
        .startPage(searchRequestDTO.getPageNum(), searchRequestDTO.getPageSize())
        .doSelectPageInfo(() -> postMapper.getPostList(searchRequestDTO));
    return Result.success(result);
  }

  @Override
  public Result insertPost(PostDTO postDTO) {
    if (CommonUtil.isEmptyOrNull(postDTO.getModuleId())) {
      return Result.failed(ResultEnum.POST_MODULE_IS_NULL, "板块id为空");
    }
    postDTO.setPostId(CommonUtil.getUUID());
    postDTO.setCreateTime(new Date());
    postDTO.setStatus("0");
    postDTO.setUserName(userMapper.selectByPrimaryKey(postDTO.getUserId()).getName());
    if (CommonUtil.isEmptyOrNull(postDTO.getType())) {
      postDTO.setType("0");
    }
    postMapper.insertPost(postDTO);
    return Result.success();
  }

  @Override
  public Result updatePost(PostDTO postDTO) {
    return null;
  }

  @Override
  public Result deletePost(String postId, String userId) {
    //如果正常则删除，如果删除则恢复正常
    Post post = postMapper.selectByPrimaryKey(postId);
    if (post == null) {
      return Result.failed(ResultEnum.RESULT_EMPTY, "查询异常，该主贴不存在");
    }
    if (KecAllEnum.STATUS_NORMAL.getCode().equals(post.getStatus())) {
      post.setStatus(KecAllEnum.STATUS_DELETE.getCode());
      //推送删除主贴信息
      messagePushService.deletePost(post, userId);
    } else {
      post.setStatus(KecAllEnum.STATUS_NORMAL.getCode());
      messagePushService.recoverPost(post, userId);
    }
    postMapper.updateByPrimaryKeySelective(post);
    return postMapper.updateByPrimaryKeySelective(post) > 0 ? Result.success()
        : Result.failed(ResultEnum.FAIL_DATABASE, "操作失败");
  }

  public Result deletePost(PostDTO postDTO) {
    return null;
  }

  @Override
  public Result getPostAndComment(PostContentRequestDTO contentRequestDTO) {
    PostResponseDTO post = postMapper.getPostById(contentRequestDTO.getPostId());
    if (null == post) {
      return Result.failed(ResultEnum.RESULT_EMPTY, "查询结果为空");
    }
    Module module = moduleMapper.selectByPrimaryKey(post.getModuleId());
    if (null == module || KecAllEnum.STATUS_DELETE.getCode().equals(module.getStatus())) {
      return Result.failed(ResultEnum.MODULE_HAS_BEEN_DELETED, "该主贴所在的板块已被删除");
    }
    if (KecAllEnum.STATUS_DELETE.getCode().equals(post.getStatus())) {
      return Result.failed(ResultEnum.POST_HAS_BEEN_DELETED, "该主贴已被删除");
    }
    //获取主贴评论
    PageInfo<CommentResponseDTO> result = PageHelper
        .startPage(contentRequestDTO.getPageNum(), contentRequestDTO.getPageSize())
        .doSelectPageInfo(() -> commentMapper.getPostCommentInfo(post.getPostId()));
    List<CommentResponseDTO> commentInfo = result.getList();
    //获取评论的回复
    for (int i = 0; i < commentInfo.size(); i++) {
      commentInfo.get(i).setReplyResponseDTOS(
          commentMapper.getCommentReplyInfo(commentInfo.get(i).getCommentId()));
    }
    result.setList(commentInfo);
    return Result.success(result);
  }

  @Override
  public Result getPostById(String postId) {
    PostResponseDTO post = postMapper.getPostById(postId);
    return Result.success(post);
  }

  @Override
  public boolean popularPost() {

    return false;
    //板块类型  0校园服务 1学习交流  2娱乐交友  3其他板块
    //帖子类型，0普通 1置顶  2精华  3热门
//    List<String> list = new ArrayList<>();
//        list = postMapper.popularPost(10, KecAllEnum.MODULE_TYPE_SCHOOL.getCode());
//    list.addAll(postMapper.popularPost(10, KecAllEnum.MODULE_TYPE_LEARN.getCode()));
//    list.addAll(postMapper.popularPost(10, KecAllEnum.MODULE_TYPE_ENTERTAINMENT.getCode()));
//    list.addAll(postMapper.popularPost(10, KecAllEnum.MODULE_TYPE_OTHERS.getCode()));
//    if (list.size() == 0) {
//      return false;
//    }
//    //先将原本的热帖改为普通贴，然后再设置热帖
//    List<String> oldHot = postMapper.selectOriginalPopPost();
//    int i = postMapper.updatePostType(oldHot, KecAllEnum.POST_TYPE_NORMAL.getCode());
//    int i1 = postMapper.updatePostType(list, KecAllEnum.POST_TYPE_POPULAR.getCode());
//    return true;
  }

  @Override
  public Result getPopularPostList() {
    Map<String, List> result = new HashMap<>();
    result.put("popular", postMapper.getPopularPostList(null));
    result.put("school", postMapper.getPopularPostList(KecAllEnum.MODULE_TYPE_SCHOOL.getCode()));
    result.put("learn", postMapper.getPopularPostList(KecAllEnum.MODULE_TYPE_LEARN.getCode()));
    result.put("entertainment",
        postMapper.getPopularPostList(KecAllEnum.MODULE_TYPE_ENTERTAINMENT.getCode()));
    result.put("others", postMapper.getPopularPostList(KecAllEnum.MODULE_TYPE_OTHERS.getCode()));
    return Result.success(result);
  }
}
