package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findAllByItemId(Long id);
}