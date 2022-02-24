package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
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

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
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
    private final S3Service s3Service;

    @Transactional
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

    @Transactional
    public PromotionResponseDto getPromotion(Long id){
        Optional<Promotion> promotion = Optional.ofNullable(promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("promotion", "promotionId", id)));
        return modelMapper.map(promotion.get(), PromotionResponseDto.class);
    }

    @Transactional
    public PromotionResponseDto createPromotion(Long id, PromotionRequestDto promotionRequestDto){
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", id)));
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경 (로그인 구현 완료시, access_token으로 사용자 찾기)

        Promotion newPromotion = promotionRepository.save(promotionRequestDto.toPromotionEntity(item.get(), user.get()));
        return modelMapper.map(newPromotion, PromotionResponseDto.class);
    }

    @Transactional
    public Long deletePromotion(Long id) throws UnsupportedEncodingException {

        Optional<Promotion> promotion = Optional.ofNullable(promotionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("promotion", "promotionId", id)));
        s3Service.deleteFileInBucket(promotion.get().getImage());
        promotionRepository.deleteById(id);
        return id;
    }
}
