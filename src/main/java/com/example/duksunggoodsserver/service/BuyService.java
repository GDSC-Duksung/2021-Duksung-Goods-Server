package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.dto.response.BuyResponseDto;
import com.example.duksunggoodsserver.repository.BuyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyService {

    private final BuyRepository buyRepository;
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
}