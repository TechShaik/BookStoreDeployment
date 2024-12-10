package com.Files.CartModel.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Files.CartModel.Cart;


@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	@Query("SELECT c FROM Cart c WHERE c.bId = :b_id AND c.userId = :userId")
	Cart findByUserIdAndBId(long b_id, long userId);



	@Query("SELECT c FROM Cart c WHERE c.userId = :user_id")
	List<Cart> findByUserId(long user_id);


	List<Cart> findAllByUserId(Long userId);

	@Modifying
	@Query("DELETE FROM Cart c WHERE c.userId = :user_id")
	void deleteAllByUserId(long user_id);




}
