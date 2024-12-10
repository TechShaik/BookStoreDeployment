package com.Files.UserEntity.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Files.UserEntity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	User findByUsername(String username);

	void deleteByUsername(String username);

}
