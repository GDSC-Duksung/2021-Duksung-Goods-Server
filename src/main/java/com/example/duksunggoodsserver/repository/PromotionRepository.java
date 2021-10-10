package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    ArrayList<Promotion> findAll();
    Optional<Promotion> findItemById(Long id);
}