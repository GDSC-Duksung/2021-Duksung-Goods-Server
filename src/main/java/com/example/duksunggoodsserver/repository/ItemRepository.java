package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByUserId(Long id);
}