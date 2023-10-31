package com.memo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.memo.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	//userentity는 null이거나 채워져있거나
	public UserEntity findByLoginId(String loginId);
	
	
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
}
