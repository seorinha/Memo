<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="login-box">
		<h1 class="mb-4">로그인</h1>
		
		<%--키보드 엔터로 로그인이 될 수 있도록 form 태그를 만들어준다 (submit 타입의 버튼이 동작됨) --%>
		<form id="loginForm" method="post" action="/user/sign-in">
			<div class="input-group mb-3"> 
				<%--input-group-prepend : inputbox 앞의 id부분을 회색으로 붙인다 --%>
				<div class="input-group-prepend">
					<span class="input-group-text">ID</span>
				</div>
				<input type="text" id="loginId" name="loginId" class="form-control">
			</div>
			
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text">PW</span>
				</div>
				<input type="password" id="password" name="password" class="form-control">
			</div>
			
			<%--btn-block : 로그인 박스 영역에 버튼을 가득 채운다 --%>
			<input type="submit" id="loginBtn" class="btn btn-block btn-primary" value="로그인">
			<a class="btn btn-block btn-dark" href="/user/sign-up-view">회원가입</a>
		</form>
	</div>
</div>

<script>
$(document).ready(function() {
	//로그인
	$('#loginForm').on('submit', function(e) {
		e.preventDefault(); //form submit 중단
		
		//validation
		let loginId = $('input[name=loginId]').val().trim();
		let passward = $('#password').val();
		
		if (!loginId) {
			alert("아이디를 입력하세요");
			return false;
		}
		
		if (!password) {
			alert("비밀번호를 입력하세요")
			return false;
		}
		
		
		//서버로 로그인 정보 보내기
		//ajax
		//form url, params
		let url = $(this).attr('action');
		console.log(url);
		let params = $(this).serialize(); //name 속성이 반드시 있어야 한다
		console.log(params);
		
		$.post(url, params) //request
		.done(function(data) { //response
			if (data.code == 200) { //성공 -> 글 목록으로 이동
				location.href = "/post/post-list-view";
			} else {
				//로직상 실패
				alert(data.errorMessage);
			}
		});
	});
});
</script>