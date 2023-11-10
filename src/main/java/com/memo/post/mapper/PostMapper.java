package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.memo.post.domain.Post;

@Repository
public interface PostMapper {

	//
    public List<Map<String, Object>> selectPostList();
    
    //게시판 글 목록 뷰 + 이전, 다음 , 첫페이지
    public List<Post> selectPostListByUserId(
    		@Param("userId") int userId,
    		@Param("direction") String direction,
    		@Param("standardId") Integer standardId,
    		@Param("limit") int limit);
    
    
    //이전끝 다음 끝 없애기 설정
    public int selectPostIdByUserIdAndSort(
    		@Param("userId") int userId,
    		@Param("sort") String sort);
    
    
    //글상세 가져오기
    public Post selectPostByPostIdUserId(
    		@Param("postId") int postId, 
    		@Param("userId") int userId);
    
    
    //글쓰기 submit
    public void insertPost(
    		@Param("userId") int userId, 
    		@Param("subject") String subject, 
    		@Param("content") String content,
    		@Param("imagePath") String imagePath);
    
    //파일 수정
    public void updatePostByPostIdUserId(
    		@Param("postId") int postId, 
    		@Param("userId") int userId, 
    		@Param("subject") String subject, 
    		@Param("content") String content, 
    		@Param("imagePath") String imagePath);
    
    
    //글 삭제
    public void deletePostByPostIdUserId(
    		@Param("postId") int postId,
    		@Param("userId") int userId);
    
}
