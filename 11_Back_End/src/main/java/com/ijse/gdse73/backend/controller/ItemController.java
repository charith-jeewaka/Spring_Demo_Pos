package com.ijse.gdse73.backend.controller;

import com.ijse.gdse73.backend.dto.ItemDTO;
import com.ijse.gdse73.backend.service.custom.ItemService;
import com.ijse.gdse73.backend.util.APIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/item")
@CrossOrigin
public class ItemController {
    private final ItemService itemServiceImpl;

    @PostMapping
    public ResponseEntity<APIResponse<String>> saveItem(@RequestBody @Valid ItemDTO itemDTO){
        itemServiceImpl.saveItem(itemDTO);

        return new ResponseEntity<>(new APIResponse<>(
              201,"Item Saved",null
        ), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<APIResponse<String>> updateItem(@RequestBody @Valid ItemDTO itemDTO) {
        itemServiceImpl.updateItem(itemDTO);

        return new ResponseEntity<>(new APIResponse<>(
                200,"Item Updated",null
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteItem(@PathVariable int id) {
        itemServiceImpl.deleteItem(id);

        return new ResponseEntity<>(new APIResponse<>(
                200,"Item Deleted",null
        ), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ItemDTO>>> getAllItems(){
        return new ResponseEntity<>(new APIResponse<>(
                200,"Items Uploaded",itemServiceImpl.getAllItems()
        ), HttpStatus.OK);
    }

}
