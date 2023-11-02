package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {

	@Autowired //new를 하지않고 spring bean을 가져온다
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager;
	
	//게시판 글 목록 뷰
	//input:userId
	//output:List<Post>
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectpostListByUserId(userId);
	}
	
	//글쓰기 submit
	//input: 파라미터들(userId, subject, content, file)
	//output: x
	public void addPost(int userId, String userloginId, String subject, String content, MultipartFile file) {
		String imagePath = null;
		
		// TODO : 이미지가 있으면 업로드
		//이미지 업로드를 하나의 클래스로 따로 만든다
		if (file != null) {
			imagePath = fileManager.saveFile(userloginId, file);
		}
		
		postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	
}
