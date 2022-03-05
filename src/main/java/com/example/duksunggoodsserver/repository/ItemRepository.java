package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, CustomizedItemRepository {
    List<Item> findAllByUserId(Long id);
    List<Item> findAll();
    Page<Item> findAllByDemandSurveyTypeIdOrderByCreatedAtDesc(Long demandSurveyTypeId, Pageable pageable);
    Page<Item> findAllByDemandSurveyTypeIdAndCategoryIdOrderByCreatedAtDesc(Long demandSurveyTypeId, Long categoryId, Pageable pageable);
}