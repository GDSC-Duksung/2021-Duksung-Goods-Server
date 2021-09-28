package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.repository.CategoryRepository;
import com.example.duksunggoodsserver.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Item> getItemList(Long id) {
        List<Item> item = itemRepository.findAllByUserId(id);
        return item;
    }

    public Optional<Item> getItemDetail(Long id) {
        return itemRepository.findItemById(id);
    }

    public List<Item> getAllItems() { return itemRepository.findAll(); }
}