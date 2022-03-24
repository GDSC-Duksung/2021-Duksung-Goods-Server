package com.example.duksunggoodsserver.controller;

import com.example.duksunggoodsserver.model.dto.request.CommunityRequestDto;
import com.example.duksunggoodsserver.service.CommunityService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommunityControllerTest {

    @InjectMocks
    private CommunityController target;

    @Mock
    private CommunityService communityService;

    private MockMvc mockMvc;

    private Gson gson;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
        gson = new Gson();
    }

    @Test
    @DisplayName("커뮤니티 생성 실패 - 내용 없음")
    void postCommunityFail_NotContentsFormat() throws Exception {
        // given
        CommunityRequestDto communityRequestDto = CommunityRequestDto.builder()
                .contents(" ")
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/items/1/communities")
                        .content(gson.toJson(communityRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest());

        // then
    }
}
