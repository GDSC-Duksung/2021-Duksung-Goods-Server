package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.BuyRequestDto;
import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.model.entity.*;
import com.example.duksunggoodsserver.repository.BuyRepository;
import com.example.duksunggoodsserver.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Transactional
    public List<BuyResponseDto> getBuyList(HttpServletRequest req) {
        Optional<User> user = userService.getCurrentUser(req);
        List<BuyResponseDto> buyResponseDtoList = buyRepository.findAllByUserId(user.get().getId())
                .stream().map(buy -> modelMapper.map(buy, BuyResponseDto.class))
                .collect(Collectors.toList());
        return buyResponseDtoList;
    }

    @Transactional
    public BuyResponseDto createBuyForm(HttpServletRequest req, Long itemId, BuyRequestDto buyRequestDto) {
        Optional<User> user = userService.getCurrentUser(req);
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));
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

    @Transactional
    public List<BuyResponseDto> getDepositList(Long itemId) {
        List<BuyResponseDto> buyResponseDtoList = buyRepository.findAllByItemId(itemId)
                .stream().map(buy -> modelMapper.map(buy, BuyResponseDto.class))
                .collect(Collectors.toList());
        return buyResponseDtoList;
    }

    @Transactional
    public boolean changeDeposit(Long buyId) {
        Optional<Buy> buy = Optional.ofNullable(buyRepository.findById(buyId)
                .orElseThrow(() -> new ResourceNotFoundException("buy", "buyId", buyId)));

        if (buy.get().isDeposit() == false) {
            buy.get().setDeposit(true);
            return true;
        } else {
            buy.get().setDeposit(false);
            return false;
        }
    }
}