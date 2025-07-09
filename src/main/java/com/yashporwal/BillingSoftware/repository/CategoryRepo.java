package com.yashporwal.BillingSoftware.repository;

import com.yashporwal.BillingSoftware.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByCategoryId(String categoryId);
}
