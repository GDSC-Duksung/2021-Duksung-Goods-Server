package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    List<Buy> findAllByUserId(Long id);
    List<Buy> findAllByItemId(Long id);
}