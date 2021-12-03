package com.ssafy.realestate.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsDto {
    
    private String imageUrl;
    private String newsTitle;
    private String newsContent;
    private String newsLink;
    private String writing;
    private String date;
}
