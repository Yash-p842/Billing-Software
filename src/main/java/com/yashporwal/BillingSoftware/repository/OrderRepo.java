package com.yashporwal.BillingSoftware.repository;

import com.yashporwal.BillingSoftware.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findByOrderId(String orderId);

    List<OrderEntity> findALlByOrderByCreatedAtDesc();

    @Query("SELECT SUM(o.grandTotal) FROM OrderEntity o WHERE DATE(o.createdAt) = :date")
    Double sunSalesByDate(@Param("date")LocalDate date);

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE DATE(o.createdAt) = :date")
    Long countByOrderDate(@Param("date")LocalDate date);

    @Query("SELECT o FROM OrderEntity o ORDER BY o.createdAt DESC")
    List<OrderEntity> findRecentOrders(Pageable pageable);
}
