package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.config.QuerydslTestConfig;
import com.example.duksunggoodsserver.model.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslTestConfig.class)
class ItemLikeRepositoryTest {

    @Autowired
    private ItemLikeRepository itemLikeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DemandSurveyTypeRepository demandSurveyTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    public User saveUser() {
        User user = User.builder()
                .email("email@duksung.ac.kr")
                .password("password")
                .name("name")
                .createdAt(LocalDateTime.now())
                .createdBy("ADMIN")
                .enabled(false)
                .build();
        return userRepository.save(user);
    }

    public Item saveItem(User user) {
        Category category = categoryRepository.getById(1L);
        DemandSurveyType demandSurveyType = demandSurveyTypeRepository.getById(1L);
        Item item = Item.builder()
                .title("title")
                .description("description")
                .price(10000)
                .startDate(LocalDate.of(2022, 2, 24))
                .endDate(LocalDate.of(2022, 2, 28))
                .demandSurveyType(demandSurveyType)
                .category(category)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        return itemRepository.save(item);
    }

    @Test
    @DisplayName("아이템아이디로 찜 갯수 조회 성공")
    void countAllByItemIdTest() {
        // given
        User user = saveUser();
        Item item = saveItem(user);
        ItemLike itemLike = ItemLike.builder()
                .user(user)
                .item(item)
                .build();

        // when
        Long count1 = itemLikeRepository.countAllByItemId(item.getId());
        itemLikeRepository.save(itemLike);
        Long count2 = itemLikeRepository.countAllByItemId(item.getId());

        // then
        Assertions.assertEquals(count1, 0);
        Assertions.assertEquals(count2, 1);
    }

    @Test
    @DisplayName("유저와 아이템으로 찜 조회 성공")
    void findByUserAndItemTest() {
        // given
        User user = saveUser();
        Item item = saveItem(user);
        ItemLike itemLike = ItemLike.builder()
                .user(user)
                .item(item)
                .build();
        ItemLike savedItemLike = itemLikeRepository.save(itemLike);

        // when
        Optional<ItemLike> itemLikeFindByUserAndItem = itemLikeRepository.findByUserAndItem(itemLike.getUser(), itemLike.getItem());

        // then
        itemLikeFindByUserAndItem.ifPresent(value -> Assertions.assertEquals(savedItemLike.getId(), value.getId()));
        itemLikeFindByUserAndItem.ifPresent(value -> Assertions.assertEquals(savedItemLike.getUser(), value.getUser()));
        itemLikeFindByUserAndItem.ifPresent(value -> Assertions.assertEquals(savedItemLike.getItem(), value.getItem()));
    }
}