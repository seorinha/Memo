package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

@RequestMapping("/user")
@RestController
public class UserRestController {

	@Autowired
	private UserBO userBO;
	
	/**
	 * 로그인 아이디 중복확인 API
	 * @param loginId
	 * @return
	 */
	@RequestMapping("/is-duplicated-id")
	public Map<String, Object> isDuplicatedId(
			@RequestParam("loginId") String loginId) {
		
		//db조회
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		
		//응답값 만들고 리턴 -> json
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		
		if (user == null) {
			//중복 아님
			result.put("isDuplicated", false);
		} else {
			//중복
			result.put("isDuplicated", true);
			
		}
		return result;
	}
	
	
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam("email") String email) {
		
		// password 암호화 = 해싱(한번 만들어지면 다시 복구가 안되는 것) => 많은 알고리즘들 중에 md5 알고리즘으로 해볼 것
		// aaaa => 74b8733745420d4d33f80c4663dc5e5
		String hashedPassword = EncryptUtils.md5(password);
		
		
		//db insert
		Integer id = userBO.addUser(loginId, hashedPassword, name, email);
		
		//응답값
		Map<String, Object> result = new HashMap<>();
		if (id == null) {
			result.put("code", 500);
			result.put("errorMessage", "회원가입하는데 실패했습니다.");
		} else {
			result.put("code", 200);
			result.put("result", "성공");			
		}
		
		return result; //json
	}
	
}
