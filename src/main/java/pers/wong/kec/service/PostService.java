package pers.wong.kec.service;

import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestdto.PostContentRequestDTO;
import pers.wong.kec.domain.requestdto.PostDTO;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;

/**
 * @author Wangjunwei
 * @Date 2019/3/4 10:05
 * @Description
 */
public interface PostService {

  Result getPostList(SearchRequestDTO searchRequestDTO);

  /**
   * 新增主贴
   */
  Result insertPost(PostDTO postDTO);

  /**
   * 修改主贴
   */
  Result updatePost(String postId, String userId);

  /**
   * 删除主贴
   */
  Result deletePost(String postId, String userId);

  /**
   * 查看主贴以及评论
   */
  Result getPostAndComment(PostContentRequestDTO contentRequestDTO);

  Result getPostById(String postId, String userId);

  /**
   * 近期热门主贴
   */
  Result getPopularPostList();

  /**
   * 关注主贴
   */
  Result focusPost(PostContentRequestDTO postContentRequestDTO);
}
