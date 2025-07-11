package com.yashporwal.BillingSoftware.service.impl;

import com.yashporwal.BillingSoftware.entity.CategoryEntity;
import com.yashporwal.BillingSoftware.entity.ItemEntity;
import com.yashporwal.BillingSoftware.io.ItemRequest;
import com.yashporwal.BillingSoftware.io.ItemResponse;
import com.yashporwal.BillingSoftware.repository.CategoryRepo;
import com.yashporwal.BillingSoftware.repository.ItemRepo;
import com.yashporwal.BillingSoftware.service.FileUploadService;
import com.yashporwal.BillingSoftware.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public ItemResponse addItem(ItemRequest request, MultipartFile file) {
        String imageUrl = fileUploadService.uploadFile(file);
        ItemEntity newItem = convertToEntity(request);
        CategoryEntity category = categoryRepo.findByCategoryId(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with CategoryId: "+request.getCategoryId()));
        newItem.setCategory(category);
        newItem.setImgUrl(imageUrl);
        newItem = itemRepo.save(newItem);
        return convertToResponse(newItem);

    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .price(newItem.getPrice())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .categoryId(newItem.getCategory().getCategoryId())
                .categoryName(newItem.getCategory().getName())
                .imgUrl(newItem.getImgUrl())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .build();
    }

    @Override
    public List<ItemResponse> readItems() {
        return itemRepo.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

    }

    @Override
    public void deleteItem(String itemId) {
        ItemEntity existingItem = itemRepo.findByItemId(itemId)
                .orElseThrow(() -> new UsernameNotFoundException("Item not found with ItemId: " + itemId));
        fileUploadService.deleteFile(existingItem.getImgUrl());
        itemRepo.delete(existingItem);
    }
}
