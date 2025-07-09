package com.yashporwal.BillingSoftware.service;

import com.yashporwal.BillingSoftware.io.ItemRequest;
import com.yashporwal.BillingSoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ItemService {

    ItemResponse addItem(ItemRequest request, MultipartFile file);

    List<ItemResponse> readItems();

    void deleteItem(String itemId);

}
