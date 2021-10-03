package com.example.duksunggoodsserver.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PromotionRequestDto {
    private Long id;
    private String image;
    private String content;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime startDate;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime endDate;

    public PromotionRequestDto(Long id, String image, String content, LocalDateTime startDate, LocalDateTime endDate){
        this.id = id;
        this.image = image;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
