package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

@RestController
@RequestMapping("/post")
public class PostRestController {

	
	@Autowired
	private PostBO postBO;
	
	/**
	 * 글쓰기 submit
	 * @param subject
	 * @param content
	 * @param session
	 * @return
	 */
	@PostMapping("/create")
	//mybatis
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		
		//session에 들어있는 유저 id를 꺼낸다
		int userId = (int)session.getAttribute("userId"); //일부러 에러내기 위해서 int , 원래는 Integer가 맞음
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		//db insert
		postBO.addPost(userId, userLoginId, subject, content, file); //session은 무거워서 bo까지 안내리고 따로 가져와야한다
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
	}
	
	
	//글 수정하기
	@PutMapping("/update")
	public Map<String, Object> update(
			@RequestParam("postId") int postId,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) { 
		
		int userId = (int)session.getAttribute("userId");
		String userLoginId = (String)session.getAttribute("userLoginId");  //키가 기억 안나면 userRestController 확인
		
		//db update
		postBO.updatePost(userId, userLoginId, postId, subject, content, file);
		
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
		
		
	}
	
	
}
