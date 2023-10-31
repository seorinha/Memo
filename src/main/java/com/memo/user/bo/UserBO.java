package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;

@Service
public class UserBO {

	@Autowired
	private UserRepository userRepository; 
	
	//input:loginId
	//output:UserEntity(null이거나 채워져있거나 둘중의 하나의 상태로 return)
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	//로그인 화면
	//input: loginId, password
	//output: userEntity(null이거나 entity로 넘기는 것)
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		return userRepository.findByLoginIdAndPassword(loginId, password);	
	}
	
	
	//input: 4개 파라미터
	//output: id(pk)
	public Integer addUser(String loginId, String password, String name, String email) {
		// UserEntity = save(UserEntity);
		UserEntity userEntity = userRepository.save(
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build());
		
		return userEntity ==  null ? null : userEntity.getId();
	}
	
	
}
