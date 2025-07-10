package com.yashporwal.BillingSoftware.repository;

import com.yashporwal.BillingSoftware.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItemEntity, Long> {

    Optional<OrderItemEntity> findByItemId(String itemId);


}
