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
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslTestConfig.class)
class ItemRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DemandSurveyTypeRepository demandSurveyTypeRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BuyRepository buyRepository;

    public List<User> saveUsers() {
        User user1 = User.builder()
                .email("user1@duksung.ac.kr")
                .password("user1")
                .name("user1")
                .createdAt(LocalDateTime.now())
                .createdBy("ADMIN")
                .enabled(false)
                .build();
        User user2 = User.builder()
                .email("user2@duksung.ac.kr")
                .password("user2")
                .name("user2")
                .createdAt(LocalDateTime.now())
                .createdBy("ADMIN")
                .enabled(false)
                .build();
        return userRepository.saveAll(Arrays.asList(user1, user2));
    }

    public List<Item> saveItems(List<User> users) {
        Category category = categoryRepository.getById(1L);
        DemandSurveyType demandSurveyType = demandSurveyTypeRepository.getById(1L);
        Item item1 = Item.builder()
                .title("item1")
                .description("description")
                .price(10000)
                .minNumber(10)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .demandSurveyType(demandSurveyType)
                .category(category)
                .user(users.get(0))
                .createdAt(LocalDateTime.now())
                .build();
        Item item2 = Item.builder()
                .title("item2")
                .description("description")
                .price(10000)
                .minNumber(10000)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .demandSurveyType(demandSurveyType)
                .category(category)
                .user(users.get(1))
                .createdAt(LocalDateTime.now())
                .build();
        Item item3 = Item.builder()
                .title("item3")
                .description("description")
                .price(10000)
                .minNumber(1000)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .demandSurveyType(demandSurveyType)
                .category(category)
                .user(users.get(1))
                .createdAt(LocalDateTime.now())
                .build();
        return itemRepository.saveAll(Arrays.asList(item1, item2, item3));
    }

    public List<Buy> saveBuys(List<User> userList, List<Item> itemList) {
        Buy buy1 = Buy.builder()
                .item(itemList.get(1))
                .user(userList.get(1))
                .count(9999)
                .createdAt(LocalDateTime.now())
                .build();
        Buy buy2 = Buy.builder()
                .item(itemList.get(2))
                .user(userList.get(1))
                .count(999)
                .createdAt(LocalDateTime.now())
                .build();
        return buyRepository.saveAll(Arrays.asList(buy1, buy2));
    }

    @Test
    @DisplayName("QueryDslTest1 - 성공임박굿즈 조회")
    void findSuccessImminentItemTest() {
        // given
        List<User> userList = saveUsers();
        List<Item> itemList = saveItems(userList);
        saveBuys(userList, itemList);

        // when
        List<Item> successImminentItemList = itemRepository.findSuccessImminentItem();

        // then
        Assertions.assertEquals(successImminentItemList.size(), 2);
        Assertions.assertEquals(successImminentItemList.get(0).getTitle(), "item2");
        Assertions.assertEquals(successImminentItemList.get(1).getTitle(), "item3");
    }

    @Test
    @DisplayName("QueryDslTest2 - 신규굿즈 조회")
    void findNewItemTest() {
        // given
        List<User> userList = saveUsers();
        saveItems(userList);

        // when
        List<Item> newItemList = itemRepository.findNewItem();

        // then
        Assertions.assertEquals(newItemList.size(), 2);
        Assertions.assertEquals(newItemList.get(0).getTitle(), "item3");
        Assertions.assertEquals(newItemList.get(1).getTitle(), "item2");
    }
}