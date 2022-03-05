package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Item;

import java.util.List;

public interface CustomizedItemRepository {
    List<Item> findSuccessImminentItem();
    List<Item> findManyLikeItem();
    List<Item> findNewItem();
}
