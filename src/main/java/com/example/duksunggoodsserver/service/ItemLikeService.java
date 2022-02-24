package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.ItemLike;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ItemRepository;
import com.example.duksunggoodsserver.repository.ItemLikeRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemLikeRepository itemLikeRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<ItemLike> getItemLike() {
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        List<ItemLike> itemLikeList = itemLikeRepository.findAllByUserOrderByIdDesc(user.get()); // 최근 찜 기준 내림차순 정렬
        return itemLikeList;
    }

    @Transactional
    public Long getCountOfItemLike(Long itemId) {
        Long count = itemLikeRepository.countAllByItemId(itemId);
        return count;
    }

    @Transactional
    public boolean changeItemLike(Long itemId) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        if (itemLikeRepository.findByUserAndItem(user.get(), item.get()).isEmpty()) {
            ItemLike itemLike = ItemLike.builder()
                    .item(item.get())
                    .user(user.get())
                    .build();
            itemLikeRepository.save(itemLike);
            return true;
        } else {
            itemLikeRepository.deleteByUserAndItem(user.get(), item.get());
            return false;
        }
    }
}
