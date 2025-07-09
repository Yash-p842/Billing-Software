package com.yashporwal.BillingSoftware.service.impl;

import com.yashporwal.BillingSoftware.entity.CategoryEntity;
import com.yashporwal.BillingSoftware.io.CategoryRequest;
import com.yashporwal.BillingSoftware.io.CategoryResponse;
import com.yashporwal.BillingSoftware.repository.CategoryRepo;
import com.yashporwal.BillingSoftware.repository.ItemRepo;
import com.yashporwal.BillingSoftware.service.CategoryService;
import com.yashporwal.BillingSoftware.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private ItemRepo itemRepo;

    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file) {
        String imageUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImageUrl(imageUrl);
        newCategory = categoryRepo.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepo.findAll()
                .stream()
                .map(this::convertToResponse)    // map(categoryEntity -> convertToResponse(categoryEntity)) -> lamda method
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity category = categoryRepo.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not Found: " + categoryId));
        fileUploadService.deleteFile(category.getImageUrl());
        categoryRepo.delete(category);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .imageUrl(newCategory.getImageUrl())
                .items(itemRepo.countByCategoryId(newCategory.getId()))
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request){
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
