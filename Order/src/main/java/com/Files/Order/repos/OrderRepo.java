package com.Files.Order.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Files.Order.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {

	List<Order> findAllByUserId(long userId);

	@Query("SELECT o FROM Order o WHERE o.userId = :userId")
	List<Order> findOrderByUserId(@Param("userId") long userId);
}
