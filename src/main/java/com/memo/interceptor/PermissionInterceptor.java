package com.memo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component //spring bean
public class PermissionInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws IOException {
		
		//요청 url path를 꺼낸다
		String uri = request.getRequestURI(); //uri가 더 큰 범위 
		logger.info("[$$$$$$$$] preHandle. uri:{}", uri);
		
		//로그인 여부
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// 비로그인 && /post  -> 로그인 페이지로 이동, 컨트롤러 수행방지 
		if (userId == null && uri.startsWith("/post")) {
			response.sendRedirect("/user/sign-in-view");
			return false; //컨트롤러 수행안함(원래 요청 안함)
		}
		
		// 로그인 && /user -> 글 목록 페이지 이동, 컨트롤러 수행 방지
		if (userId != null && uri.startsWith("/user")) {
			response.sendRedirect("/post/post-list-view");
			return false;  //컨트롤러 수행안함(원래 요청 안함)
		}
		
		return true;  //컨트롤러 수행함
	}

	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav) {
		// view객체가 mav로 존재한다는 것은 아직 jsp가 html로 변환되기 전이라는 뜻
		
		logger.info("[######] postHandel");
	}
	
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		//jsp가 html로 최종 변환된 후 
		logger.info("[@@@@@@@] afterCompletion");
	}
}
