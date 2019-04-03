package pers.wong.kec.dao.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import pers.wong.kec.dao.base.MyMapper;
import pers.wong.kec.domain.entity.Post;
import pers.wong.kec.domain.requestdto.PostDTO;
import pers.wong.kec.domain.requestdto.SearchRequestDTO;
import pers.wong.kec.domain.responsedto.MessageRelationResponseDTO;
import pers.wong.kec.domain.responsedto.PopularPostResponseDTO;
import pers.wong.kec.domain.responsedto.PostResponseDTO;
import pers.wong.kec.domain.responsedto.UserAttentionResponseDTO;

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
   * 保存主贴热度值
   */
  int updatePostPopularIndex(@Param("postId") String postId, @Param("index") double index );

  /**
   * 原来的热门主贴
   */
  List<String> selectOriginalPopPost();

  /**
   * 根据板块类型获取热门主贴
   */
  List<PostResponseDTO> getPopularPostList(@Param("moduleType") String moduleType);
}