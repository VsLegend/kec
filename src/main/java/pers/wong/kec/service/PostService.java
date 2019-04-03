package pers.wong.kec.service;

import java.util.List;
import pers.wong.kec.common.Result;
import pers.wong.kec.domain.requestDTO.PostContentRequestDTO;
import pers.wong.kec.domain.requestDTO.PostDTO;
import pers.wong.kec.domain.requestDTO.SearchRequestDTO;

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
  Result updatePost(PostDTO postDTO);

  /**
   * 删除或恢复主贴
   */
  Result deletePost(String postId, String userId);

  /**
   * 查看主贴以及评论
   */
  Result getPostAndComment(PostContentRequestDTO contentRequestDTO);

  Result getPostById(String postId);

  /**
   * 热门主贴算法
   */
  boolean popularPost();

  /**
   * 近期热门主贴
   * 算法：
   * 变量 x/天 y/浏览用户 z/评论数
   * 1. 近期x天内有评论
   * 2. y > z*2
   * 3. 一个月内评论 > z
   */
  Result getHotPostList();
}
