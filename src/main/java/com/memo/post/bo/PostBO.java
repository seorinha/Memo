package com.memo.post.bo;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private static final int POST_MAX_SIZE = 3; 
	
	@Autowired //new를 하지않고 spring bean을 가져온다
	private PostMapper postMapper;
	
	@Autowired
	private FileManagerService fileManager; //component여서 new가 아니라 autowired로 연결
	
	//게시판 글 목록 뷰
	//input:userId
	//output:List<Post>
	public List<Post> getPostListByUserId(int userId, Integer prevId, Integer nextId) {
		// 게시글 번호: 10 9 8 | 7 6 5 | 4 3 2 | 1 
		// 내가 만약  432 페이지에 있을 때 
		//1. 다음을 누른다면: 2보다 작은 3개, desc
		//2. 이전을 누른다면: 4보다 큰 3개 asc(5 6 7)을 뒤집어야한다-> List reverse(765)
		//3. 첫 페이지를 누른다면: 이전, 당음 없음 desc 3개
		
		String direction = null; //방향
		Integer standardId = null; // 기준 postId
		if (prevId != null) { //이전
			direction = "prev";
			standardId = prevId;
			
			List<Post> postList = postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
					
			//reverse 5 6 7 -> 7 6 5
			Collections.reverse(postList); //뒤집고 저장
			
			return postList;
					
		} else if (nextId != null) { //다음
			direction = "next";
			standardId = nextId;
		}
		
		//첫페이지 or 다음
		return postMapper.selectPostListByUserId(userId, direction, standardId, POST_MAX_SIZE);
	}
	
	//이전 페이지의 마지막인가?
	public boolean isPrevLastPageByUserId(int prevId, int userId) {
		int postId = postMapper.selectPostIdByUserIdAndSort(userId, "DESC");
		return postId == prevId; //같으면 끝 true, 아니면 false
	}
	
	//다음 페이지의 마지막인가
	public boolean isNextLastPageByUserId(int nextId, int userId) {
		int postId = postMapper.selectPostIdByUserIdAndSort(userId, "ASC");
		return postId == nextId; //같으면 끝 true, 아니면 false
	}
	
	
	
	
	//글 상세 가져오기
	//input: postId, userId
	//output: Post
	public Post getPostByPostIdUserId(int postId, int userId) {
		return postMapper.selectPostByPostIdUserId(postId, userId); 
	}
	
	//글쓰기 submit
	//input: 파라미터들(userId, subject, content, file)
	//output: x
	public void addPost(int userId, String userLoginId, String subject, String content, MultipartFile file) {
		String imagePath = null;
		
		// 이미지가 있으면 업로드
		// 이미지 업로드를 하나의 클래스로 분리해 따로 만든다
		if (file != null) { //이미지가 있을 때만 업로드한다
			imagePath = fileManager.saveFile(userLoginId, file);
		}
		
		postMapper.insertPost(userId, subject, content, imagePath);
	}
	
	
	//글 수정하기
	//input:파라미터들(userId, userLoginId, postId, subject, content, file)
	//output: x
	public void updatePost(int userId, String userLoginId, int postId, 
			String subject, String content, MultipartFile file) {
		
		//먼저 기존 글을 가져와본다.(1. 이미지 교체시 삭제를 위해서, 2. 업데이트 대상이 있는지 확인하기 위해) 
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) { //post가 null? 이럴리가 없는데 
			logger.error("[글 수정] post is null.  post id:{}, userId:{}", postId, userId); //에러수준일때
			//logger.info(); //에러까진 아니고 조심 해야 될정도
			return;
		}
		//파일이 있다면 
		//1. 새로운 이미지를 업로드한다
		//2  새 이미지 업로드 성공시 기존 이미지를 제거한다(기존 이미지가 있을 때 제거)
		String imagePath = null;
		if (file != null) { //대용량트래핑을 하다보면 왜 없는지 모르는 경우가 생길 수 있어서 서버개발자들은 null체크를 무조건 해야한다 
			//업로드
			imagePath = fileManager.saveFile(userLoginId, file);
			
			//업로드 성공시 기존 이미지 제거(이미지가 잇다면)
			if (imagePath != null && post.getImagePath() != null) { //업로드가 성공을 했고, 기존이미지가 존재한다면 삭제를 한다
				//이미지 제거
				fileManager.deleteFile(post.getImagePath());
			}
		}
		
		//db 글 업데이트
		postMapper.updatePostByPostIdUserId(postId, userId, subject, content, imagePath);
		
	}
	
	
	//글 삭제하기
	//input: 글 번호, 글쓴이 번호
	//output:x
	public void deletePostByPostIdUserId(int postId, int userId) {
		// 기존 글 가져옴(이미지가 있으면 삭제야하기 떼문)
		Post post = postMapper.selectPostByPostIdUserId(postId, userId);
		if (post == null) {
			logger.info("[글 삭제] post가 null. postId:{}, userId:{}", postId, userId);
			return;
		}
		//기존 이미지가 존재하면 -> 삭제
		if (post.getImagePath() !=null) {
			fileManager.deleteFile(post.getImagePath());
		}
		
		
		//db 삭제
		postMapper.deletePostByPostIdUserId(postId, userId);
	}
	
}
