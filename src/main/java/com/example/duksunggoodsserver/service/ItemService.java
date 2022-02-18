package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.repository.ItemRepository;
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
public class ItemService {

    private final ItemRepository itemRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ItemResponseDto> getItemList(Long id) {

        List<ItemResponseDto> itemResponseDtoList = itemRepository.findAllByUserId(id)
                .stream().map(item -> modelMapper.map(item, ItemResponseDto.class))
                .collect(Collectors.toList());
        return itemResponseDtoList;
    }

    @Transactional
    public ItemResponseDto getItemDetail(Long id) {
        Optional<Item> itemId = Optional.ofNullable(itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", id)));
        return modelMapper.map(itemId.get(), ItemResponseDto.class);
    }

    @Transactional
    public List<ItemResponseDto> getAllItems() {

        List<ItemResponseDto> itemResponseDtoList = itemRepository.findAll()
                .stream().map(item -> modelMapper.map(item, ItemResponseDto.class))
                .collect(Collectors.toList());
        return itemResponseDtoList;
    }
}