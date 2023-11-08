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
    
    //게시판 글 목록 뷰
    public List<Post> selectPostListByUserId(int userId);
    
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
    
    
    
}
