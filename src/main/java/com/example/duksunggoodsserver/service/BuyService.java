package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.BuyRequestDto;
import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.model.entity.*;
import com.example.duksunggoodsserver.repository.BuyRepository;
import com.example.duksunggoodsserver.repository.ItemRepository;
import com.example.duksunggoodsserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyService {

    private final BuyRepository buyRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<BuyResponseDto> getBuyList(Long id) {

        List<BuyResponseDto> buyResponseDtoList = buyRepository.findAllByUserId(id)
                .stream().map(buy -> modelMapper.map(buy, BuyResponseDto.class))
                .collect(Collectors.toList());
        return buyResponseDtoList;
    }

    @Transactional
    public List<BuyResponseDto> getSellFormList(Long id) {

        List<BuyResponseDto> buyResponseDtoList = buyRepository.findAllByItemId(id)
                .stream().map(buy -> modelMapper.map(buy, BuyResponseDto.class))
                .collect(Collectors.toList());
        return buyResponseDtoList;
    }

    @Transactional
    public BuyResponseDto createBuyForm(Long itemId, BuyRequestDto buyRequestDto) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));
        Optional<User> user = Optional.ofNullable(userRepository.findById(1L)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userId", 1L))); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        Buy buy = buyRepository.save(buyRequestDto.toBuyEntity(item.get(), user.get()));
        return modelMapper.map(buy, BuyResponseDto.class);
    }

    @Transactional
    public Long deleteBuyForm(Long buyId) {
        Optional.ofNullable(buyRepository.findById(buyId)
                .orElseThrow(() -> new ResourceNotFoundException("buy", "buyId", buyId)));

        buyRepository.deleteById(buyId);
        return buyId;
    }
}