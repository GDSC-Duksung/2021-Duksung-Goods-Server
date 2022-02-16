package com.example.duksunggoodsserver.service;

import com.example.duksunggoodsserver.model.dto.request.CommunityRequestDto;
import com.example.duksunggoodsserver.model.dto.response.CommunityResponseDto;
import com.example.duksunggoodsserver.model.entity.Community;
import com.example.duksunggoodsserver.model.entity.Item;
import com.example.duksunggoodsserver.model.entity.User;
import com.example.duksunggoodsserver.repository.CommunityRepository;
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
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<CommunityResponseDto> getCommunityList(Long itemId) {

        List<CommunityResponseDto> communityResponseDto = communityRepository.findAllByItemId(itemId)
                .stream().map(community -> modelMapper.map(community, CommunityResponseDto.class))
                .collect(Collectors.toList());
        return communityResponseDto;
    }

    @Transactional
    public CommunityResponseDto saveCommunity(Long id, CommunityRequestDto communityRequestDto) {
        Optional<Item> itemId = itemRepository.findById(id);
        Optional<User> userId = userRepository.findById(1L); // TODO: 임시로 해놓음. 추후에 본인 id로 변경

        if (itemId.isPresent() && userId.isPresent()) {
            Community newCommunity = communityRepository.save(communityRequestDto.toCommunityEntity(itemId.get(), userId.get()));
            return modelMapper.map(newCommunity, CommunityResponseDto.class);
        } else
            return null;
    }

    @Transactional
    public Long deleteCommunity(Long id) {
        if (communityRepository.findById(id).isPresent()) {
            communityRepository.deleteById(id);
            return id;
        } else
            return null;
    }
}
