package com.memo.post;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

@RequestMapping("/post")
@Controller
public class PostController {

	@Autowired
	private PostBO postBO;
	
	/**
	 * 게시판 글 목록 뷰
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/post-list-view")
	public String postListView(Model model, HttpSession session) {
		//로그인 여부 조회
		Integer userId = (Integer)session.getAttribute("userId");
		if (userId == null) {
			//비로그인이면 로그인 화면으로 이동
			return "redirect:/user/sign-in-view";
		}
		
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		model.addAttribute("postList", postList);
		model.addAttribute("viewName", "post/postList");
		return "template/layout";
	}
	
	/**
	 * 글쓰기 화면 뷰
	 * @param model
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView(Model model) {
		model.addAttribute("viewName", "post/postCreate");
		return "template/layout";
	}
	
	
	//
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam ("postId") int postId,
			HttpSession session,
			Model model) {
		
		int userId = (int)session.getAttribute("userId");
		
		//db select 
		//select where postId and userId
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		model.addAttribute("post", post);
		model.addAttribute("viewName", "post/postDetail");
		return "template/layout";
	}
	
	
}
