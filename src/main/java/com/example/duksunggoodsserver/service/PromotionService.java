package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.dto.response.PromotionResponseDto;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.Promotion;
import com.example.duksunggoodsserver.model.dto.request.PromotionRequestDto;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.ItemRepository;
import com.example.duksunggoodsserver.repository.PromotionRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {

    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public List<PromotionResponseDto> getAllPromotions(){

        List<PromotionResponseDto> promotionResponseDtoList = promotionRepository.findAll()
                .stream().map(promotion -> modelMapper.map(promotion, PromotionResponseDto.class))
                .collect(Collectors.toList());

        //랜덤섞기
        Collections.shuffle(promotionResponseDtoList);

        //추출
        if (promotionResponseDtoList.size() < 4) {
            return promotionResponseDtoList;
        } else {
            return promotionResponseDtoList.subList(0,4);
        }

        // TODO: Spring batch로 일정시간마다 특정 정책을 가지고 promotion 가져오기
        // TODO: endDate 끝나면 삭제
    }

    public PromotionResponseDto getPromotion(Long id){

        Optional<Promotion> promotion = promotionRepository.findById(id);
        if (promotion.isPresent())
            return modelMapper.map(promotion.get(), PromotionResponseDto.class);
        else
            return null;
    }

    public PromotionResponseDto createPromotion(@RequestBody PromotionRequestDto requestDto){
        Optional<User> user = userRepository.findById(requestDto.getUserId()); // TODO: 로그인 구현 완료시, access_token으로 사용자 찾기
        Optional<Item> item = itemRepository.findById(requestDto.getItemId());

        if (user.isPresent() && item.isPresent()) {
            Promotion newPromotion = promotionRepository.save(Promotion.builder()
                    .image(requestDto.getImage()) // TODO: S3 연결
                    .content(requestDto.getContent())
                    .startDate(requestDto.getStartDate())
                    .endDate(requestDto.getEndDate())
                    .user(user.get())
                    .item(item.get())
                    .build());
            return modelMapper.map(newPromotion, PromotionResponseDto.class);
        } else
            return null;
    }

    public Long deletePromotion(@PathVariable Long id){
        if (promotionRepository.findById(id).isPresent()) {
            promotionRepository.deleteById(id);
            return id;
        } else
            return null;
    }
}
