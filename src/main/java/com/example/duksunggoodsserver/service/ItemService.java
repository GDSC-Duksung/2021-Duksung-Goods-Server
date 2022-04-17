package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.exception.ResourceNotFoundException;
import com.example.duksunggoodsserver.model.dto.request.ImageRequestDto;
import com.example.duksunggoodsserver.model.dto.request.ItemRequestDto;
import com.example.duksunggoodsserver.model.dto.response.ImageResponseDto;
import com.example.duksunggoodsserver.model.dto.response.ItemResponseDto;
import com.example.duksunggoodsserver.model.entity.*;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.repository.ItemRepository;
import com.example.duksunggoodsserver.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    private final BuyRepository buyRepository;
    private final UserService userService;
    private final S3Service s3Service;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ItemResponseDto> getHomeItem() {
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        // 성공임박굿즈
        List<ItemResponseDto> successImminentItem = itemRepository.findSuccessImminentItem()
                .stream().map(item -> addImagesTo(item))
                .collect(Collectors.toList());
        if (successImminentItem.size() == 2) {
            itemResponseDtoList.add(successImminentItem.get(0));
            itemResponseDtoList.add(successImminentItem.get(1));
        } else if (successImminentItem.size() == 1) {
            itemResponseDtoList.add(successImminentItem.get(0));
            itemResponseDtoList.add(null);
        } else {
            itemResponseDtoList.add(null);
            itemResponseDtoList.add(null);
        }
        // 인기굿즈
        List<ItemResponseDto> manyLikeItem = itemRepository.findManyLikeItem()
                .stream().map(item -> addImagesTo(item))
                .collect(Collectors.toList());
        if (manyLikeItem.size() == 2) {
            itemResponseDtoList.add(manyLikeItem.get(0));
            itemResponseDtoList.add(manyLikeItem.get(1));
        } else if (manyLikeItem.size() == 1) {
            itemResponseDtoList.add(manyLikeItem.get(0));
            itemResponseDtoList.add(null);
        } else {
            itemResponseDtoList.add(null);
            itemResponseDtoList.add(null);
        }
        // 신규굿즈
        List<ItemResponseDto> newItem = itemRepository.findNewItem()
                .stream().map(item -> addImagesTo(item))
                .collect(Collectors.toList());
        if (newItem.size() == 2) {
            itemResponseDtoList.add(newItem.get(0));
            itemResponseDtoList.add(newItem.get(1));
        } else if (newItem.size() == 1) {
            itemResponseDtoList.add(newItem.get(0));
            itemResponseDtoList.add(null);
        } else {
            itemResponseDtoList.add(null);
            itemResponseDtoList.add(null);
        }
        return itemResponseDtoList;
    }

    @Transactional
    public List<ItemResponseDto> getItemList(HttpServletRequest req) {
        Optional<User> user = userService.getCurrentUser(req);
        List<ItemResponseDto> itemResponseDtoList = itemRepository.findAllByUserId(user.get().getId())
                .stream().map(item -> {
                    ItemResponseDto itemResponseDto = addImagesTo(item);
                    return addPercentageTo(itemResponseDto);
                })
                .collect(Collectors.toList());
        return itemResponseDtoList;
    }

    @Transactional
    public ItemResponseDto getItemDetail(Long itemId) {
        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));
        ItemResponseDto itemResponseDto = addImagesTo(item.get());
        return addPercentageTo(itemResponseDto);
    }

    @Transactional
    public List<ItemResponseDto> getItemListByDemandSurveyTypeAndCategory(Long demandSurveyTypeId, Long categoryId, int page) {
        Pageable paging = PageRequest.of(page-1, 10);
        if (categoryId == 0) { // 카테고리가 All을 뜻함
            List<ItemResponseDto> itemResponseDtoList = itemRepository.findAllByDemandSurveyTypeIdOrderByCreatedAtDesc(demandSurveyTypeId, paging).getContent()
                    .stream().map(item -> {
                        ItemResponseDto itemResponseDto = addImagesTo(item);
                        return addPercentageTo(itemResponseDto);
                    })
                    .collect(Collectors.toList());
            return itemResponseDtoList;
        }

        List<ItemResponseDto> itemResponseDtoList = itemRepository.findAllByDemandSurveyTypeIdAndCategoryIdOrderByCreatedAtDesc(demandSurveyTypeId, categoryId, paging).getContent()
                .stream().map(item -> {
                    ItemResponseDto itemResponseDto = addImagesTo(item);
                    return addPercentageTo(itemResponseDto);
                })
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

        Long progress = 0L;
        if (itemRequestDto.getDemandSurveyTypeId() == 2L) progress = 20L;

        Item item = itemRepository.save(itemRequestDto.toItemEntity(user.get(), category.get(), demandSurveyType.get(), progress));

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

    @Transactional
    public ItemResponseDto changeProgress(Long itemId, Long progress) {

        Optional<Item> item = Optional.ofNullable(itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("item", "itemId", itemId)));

        if (item.get().getProgress() != progress) {
            item.get().setProgress(progress);
            ItemResponseDto itemResponseDto = modelMapper.map(item.get(), ItemResponseDto.class);
            return itemResponseDto;
        }

        return null;
    }

    public ItemResponseDto addImagesTo(Item item) {
        List<ImageResponseDto> imageList = imageRepository.findAllByItemId(item.getId())
                .stream().map(image ->  modelMapper.map(image, ImageResponseDto.class))
                .collect(Collectors.toList());

        ItemResponseDto itemResponseDto = modelMapper.map(item, ItemResponseDto.class);
        itemResponseDto.setImageList(imageList);
        return itemResponseDto;
    }

    public ItemResponseDto addPercentageTo(ItemResponseDto itemResponseDto) {
        if (buyRepository.findAllByItemId(itemResponseDto.getId()).isEmpty())
            itemResponseDto.setPercentage(0F);
        else
            itemResponseDto.setPercentage(buyRepository.selectTotalCountByItemId(itemResponseDto.getId()) / itemResponseDto.getMinNumber() * 100);
        return itemResponseDto;
    }
}