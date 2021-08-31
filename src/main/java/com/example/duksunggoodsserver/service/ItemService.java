package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItemList(Long id) {
        List<Item> item = itemRepository.findAllByUserId(id);
        return item;
    }
}