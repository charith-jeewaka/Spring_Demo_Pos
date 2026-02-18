package com.ijse.gdse73.backend.service.custom.impl;

import com.ijse.gdse73.backend.dto.CustomerDTO;
import com.ijse.gdse73.backend.dto.ItemDTO;
import com.ijse.gdse73.backend.entity.Item;
import com.ijse.gdse73.backend.exception.CustomException;
import com.ijse.gdse73.backend.repository.ItemRepository;
import com.ijse.gdse73.backend.service.custom.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        if(itemDTO==null){
            throw new CustomException("ItemDTO is null");
        }

        itemRepository.save(modelMapper.map(itemDTO, Item.class));
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        if(itemDTO==null){
            throw new CustomException("ItemDTO is null");
        }

        itemRepository.save(modelMapper.map(itemDTO, Item.class));
    }

    @Override
    public void deleteItem(int id) {
        if(id<=0){
            throw new CustomException("Invalid Item Id");
        }

        itemRepository.deleteById(id);
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(item -> modelMapper.map(item, ItemDTO.class))
                .toList();
    }

    @Override
    public ItemDTO getItemById(int id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found: " + id));

        return modelMapper.map(item, ItemDTO.class);
    }

    @Override
    @Transactional
    public void reduceStock(int itemId, int qty) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found: " + itemId));

        if (item.getQty() < qty) {
            throw new RuntimeException("Insufficient stock for item: " + item.getName());
        }

        item.setQty(item.getQty() - qty);

        Item updatedItem = modelMapper.map(item, Item.class);
        itemRepository.save(updatedItem);
    }

}
