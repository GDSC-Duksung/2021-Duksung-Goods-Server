package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.ItemLike;
import com.example.duksunggoodsserver.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    List<ItemLike> findAllByUserOrderByIdDesc(User user);
    Long countAllByItemId(Long itemId);
    Optional<ItemLike> findByUserAndItem(User user, Item item);
    Optional<ItemLike> deleteByUserAndItem(User user, Item item);
}
