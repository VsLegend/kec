package pers.wong.kec.dao.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.requestDTO.PostDTO;
import pers.wong.kec.domain.requestDTO.SearchRequestDTO;
import pers.wong.kec.domain.responseDTO.MessageRelationResponseDTO;
import pers.wong.kec.domain.responseDTO.PopularPostResponseDTO;
import pers.wong.kec.domain.responseDTO.PostResponseDTO;
import pers.wong.kec.domain.responseDTO.UserAttentionResponseDTO;

public interface PostMapper extends MyMapper<Post> {

  /**
   * 获取帖子列表
   */
  List<PostResponseDTO> getPostList(SearchRequestDTO searchRequestDTO);

  void insertPost(PostDTO postDTO);

  /**
   * 查找指定主贴的信息
   */
  PostResponseDTO getPostById(String postId);

  List<UserAttentionResponseDTO> getUserHasPostedList(String userId);

  /**
   * 帖子的基本信息
   */
  MessageRelationResponseDTO selectPostInfo(String postId);

  /**
   * 获取关注人数大于零的主贴id
   */
  List<PopularPostResponseDTO> popularPostList();

  /**
   * 批量更新主贴的状态
   */
  int updatePostType(@Param("list") List<String> list, @Param("type") String type);

  /**
   * 原来的热门主贴
   */
  List<String> selectOldHot();

  List<PostResponseDTO> getHotPostList(String moduleType);
}