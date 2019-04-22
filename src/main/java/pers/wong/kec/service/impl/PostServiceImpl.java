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
import pers.wong.kec.dao.dao.UserAttentionMapper;
import pers.wong.kec.dao.dao.UserMapper;
import pers.wong.kec.domain.entity.Module;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.entity.User;
import pers.wong.kec.domain.entity.UserAttention;
import pers.wong.kec.domain.requestdto.PostContentRequestDTO;
import pers.wong.kec.domain.requestdto.PostDTO;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.domain.responsedto.CommentResponseDTO;
import pers.wong.kec.domain.responsedto.PostResponseDTO;
import pers.wong.kec.service.MessagePushService;
import pers.wong.kec.service.PostService;
import tk.mybatis.mapper.entity.Example;

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

  @Resource
  UserAttentionMapper userAttentionMapper;

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
    int i = postMapper.insertPost(postDTO);
    return Result.success();
  }

  @Override
  public Result updatePost(String postId, String userId) {
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

  @Override
  public Result deletePost(String postId, String userId) {
    if (CommonUtil.isEmptyOrNull(postId)) {
      return Result.failed(ResultEnum.RESULT_EMPTY, "主贴不存在");
    }
    Post post = postMapper.selectByPrimaryKey(postId);
    if (!post.getUserId().equals(userId)) {
      return Result.failed(ResultEnum.ACCESS_DENIED, "非法用户");
    }
    post.setStatus(KecAllEnum.STATUS_DELETE.getCode());
    postMapper.updateByPrimaryKeySelective(post);
    return Result.success();
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
  public Result getPostById(String postId, String userId) {
    PostResponseDTO post = postMapper.getPostById(postId);
    UserAttention followInfo = userAttentionMapper.getFollowInfo(userId, postId);
    if (null != followInfo) {
      post.setFocusPost(KecAllEnum.FOLLOW_POST_YES.getCode());
    } else {
      post.setFocusPost(KecAllEnum.FOLLOW_POST_NO.getCode());
    }
    return Result.success(post);
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

  @Override
  public Result focusPost(PostContentRequestDTO postContentRequestDTO) {
    Post post = postMapper.selectByPrimaryKey(postContentRequestDTO.getPostId());
    if (post.getUserId().equals(postContentRequestDTO.getUserId())) {
      return Result.failed(ResultEnum.UNEXCEPTED, "不能关注自己发布的主贴");
    }
    //查看是否已经关注
    UserAttention followInfo = userAttentionMapper
        .getFollowInfo(postContentRequestDTO.getUserId(), postContentRequestDTO.getPostId());
    if (null != followInfo) {
      int i = userAttentionMapper.deleteByPrimaryKey(followInfo);
      return Result.success("取消关注成功");
    }
    UserAttention userAttention = new UserAttention();
    userAttention.setId(CommonUtil.getUUID());
    userAttention.setUserId(postContentRequestDTO.getUserId());
    userAttention.setPostId(postContentRequestDTO.getPostId());
    userAttention.setCreateTime(new Date());
    userAttention.setStatus("0");
    userAttentionMapper.insertSelective(userAttention);
    return Result.success("关注成功");
  }

  @Override
  public Result updatePostType(PostDTO postDTO, String updateMan) {
    Post post = postMapper.selectByPrimaryKey(postDTO.getPostId());
    if (null == post) {
      return Result.failed(ResultEnum.RESULT_EMPTY, "查询失败，结果为空");
    }
    if (!postQualification(post, updateMan)) {
      return Result.failed(ResultEnum.ACCESS_DENIED, "权限不足，禁止操作");
    }
    post.setUpdateTime(new Date());
    post.setUpdateUser(updateMan);
    //仅支持一种类型
    if ( KecAllEnum.POST_TYPE_POPULAR.getCode().equals(post.getType())) {
      return Result.failed(ResultEnum.FAIL_DATABASE, "主贴当前无法修改为其他类型");
    }
    post.setType(postDTO.getType());
    postMapper.updateByPrimaryKeySelective(post);
    return Result.success();
  }

  @Override
  public Result getUserManagedSection(String userId) {
    Example example = new Example(Module.class);
    example.createCriteria().andEqualTo("userId", userId)
        .andEqualTo("status", KecAllEnum.STATUS_NORMAL.getCode());
    List<Module> modules = moduleMapper.selectByExample(example);
    return Result.success(modules);
  }

  public boolean postQualification(Post post, String userId) {
    User user = userMapper.selectByPrimaryKey(userId);
    Module module = moduleMapper.selectByPrimaryKey(post.getModuleId());
    if (module.getUserId().equals(userId) || user.getType().equals(KecAllEnum.ROLE_ADMIN.getCode())) {
      return true;
    }
    return false;
  }
}
