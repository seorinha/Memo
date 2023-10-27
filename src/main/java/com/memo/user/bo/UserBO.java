package com.memo.user.bo;

import org.springframework.stereotype.Service;

import com.memo.user.entity.UserEntity;

@Service
public class UserBO {

	//input:loginId
	//output:UserEntity(null이거나 채워져있거나 둘중의 하나의 상태로 return)
	public UserEntity getUserEntityByLoginId(String loginId) {
		return null;
	}
	
	
	
}
