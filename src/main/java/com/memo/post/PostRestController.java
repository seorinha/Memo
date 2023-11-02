package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
			HttpSession session) {
		
		//session에 들어있는 유저 id를 꺼낸다
		int userId = (int)session.getAttribute("userId"); //일부러 에러내기 위해서 int , 원래는 Integer가 맞음
		
		
		//db insert
		postBO.addtPost(userId, subject, content);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("result", "성공");
		
		return result;
	}
	
	
}
