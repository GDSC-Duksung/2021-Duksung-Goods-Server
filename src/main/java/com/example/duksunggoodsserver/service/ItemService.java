package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.ImageRequestDto;
import com.example.duksunggoodsserver.model.dto.request.ItemRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ImageResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.model.entity.*;
import com.example.duksunggoodsserver.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final DemandSurveyTypeRepository demandSurveyTypeRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ItemResponseDto> getItemList(HttpServletRequest req) {
        Optional<User> user = userService.getCurrentUser(req);
        List<ItemResponseDto> itemResponseDtoList = itemRepository.findAllByUserId(user.get().getId())
                .stream().map(item -> addImagesTo(item))
                .collect(Collectors.toList());
        return itemResponseDtoList;
    }

    @Transactional
    public ItemResponseDto getItemDetail(Long itemId) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));
        ItemResponseDto itemResponseDto = addImagesTo(item.get());
        return itemResponseDto;
    }

    @Transactional
    public List<ItemResponseDto> getAllItems() {
        List<ItemResponseDto> itemResponseDtoList = itemRepository.findAll()
                .stream().map(item -> addImagesTo(item))
                .collect(Collectors.toList());
        return itemResponseDtoList;
    }

    @Transactional
    public ItemResponseDto createItem(HttpServletRequest req, List<MultipartFile> files, ItemRequestDto itemRequestDto) throws IOException {
        Optional<User> user = userService.getCurrentUser(req);
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(itemRequestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", itemRequestDto.getCategoryId())));
        Optional<DemandSurveyType> demandSurveyType = Optional.ofNullable(demandSurveyTypeRepository.findById(itemRequestDto.getDemandSurveyTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("demandSurveyType", "demandSurveyTypeId", itemRequestDto.getDemandSurveyTypeId())));

        Item item = itemRepository.save(itemRequestDto.toItemEntity(user.get(), category.get(), demandSurveyType.get()));

        if (files != null) {
            for (MultipartFile file: files) {
                String imgPath = s3Service.uploadFile(file);
                ImageRequestDto imageRequestDto = ImageRequestDto.builder()
                        .url(imgPath)
                        .itemId(item.getId())
                        .build();
                ImageResponseDto imageResponseDto = createImage(imageRequestDto);
                log.info("Succeeded in saving image : viewer {} => {}", 1, imageResponseDto);
            }
        }

        ItemResponseDto itemResponseDto = addImagesTo(item);
        return itemResponseDto;
    }

    @Transactional
    public ImageResponseDto createImage(ImageRequestDto imageRequestDto) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(imageRequestDto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", imageRequestDto.getItemId())));
        Image image = imageRepository.save(imageRequestDto.toImageEntity(item.get()));
        return modelMapper.map(image, ImageResponseDto.class);
    }

    @Transactional
    public Long deleteItem(Long itemId) throws UnsupportedEncodingException {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));
        List<Image> imageList = imageRepository.findAllByItemId(item.get().getId());

        for (Image image: imageList) {
            s3Service.deleteFileInBucket(image.getUrl());
            imageRepository.deleteById(image.getId());
        }
        itemRepository.deleteById(itemId);
        return itemId;
    }

    public ItemResponseDto addImagesTo(Item item) {
        List<ImageResponseDto> imageList = imageRepository.findAllByItemId(item.getId())
                .stream().map(image ->  modelMapper.map(image, ImageResponseDto.class))
                .collect(Collectors.toList());

        ItemResponseDto itemResponseDto = modelMapper.map(item, ItemResponseDto.class);
        itemResponseDto.setImageList(imageList);
        return itemResponseDto;
    }
}