package com.ijse.gdse73.backend.service.custom;

import com.ijse.gdse73.backend.dto.ItemDTO;
import java.util.List;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);
    void updateItem(ItemDTO itemDTO);
    void deleteItem(int id);
    List<ItemDTO> getAllItems();

    // New methods
    ItemDTO getItemById(int id);
    void reduceStock(int itemId, int qty);
}
