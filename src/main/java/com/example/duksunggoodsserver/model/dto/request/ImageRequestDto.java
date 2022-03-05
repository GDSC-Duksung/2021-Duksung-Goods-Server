package com.example.duksunggoodsserver.model.dto.request;

import com.example.duksunggoodsserver.model.entity.Image;
import com.example.duksunggoodsserver.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
@Builder
public class ImageRequestDto {

    private String url;

    private Long itemId;

    public Image toImageEntity(Item item) {
        return Image.builder()
                .url(this.url)
                .item(item)
                .build();
    }
}
