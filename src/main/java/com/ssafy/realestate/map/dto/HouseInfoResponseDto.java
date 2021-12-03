package com.ssafy.realestate.map.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class HouseInfoResponseDto {

    private static Random random = new Random();
    static int max = 30;
    static int min = 1;

    private Integer aptCode;

    private String aptName;

    private String dongCode;

    private String dongName;

    private String sidoName;

    private String gugunName;

    private Integer buildYear;

    private String jibun;

    private String lat;

    private String lng;

    private String img;

    private String recentPrice;

    private boolean likedStatus;

    public HouseInfoResponseDto(Integer aptCode, String aptName, Integer buildYear, String dongCode,
                                String dongName, String jibun, String lat, String lng
                                , String sidoName, String gugunName, String recentPrice) {
        this.aptCode = aptCode;
        this.aptName = aptName;
        this.dongCode = dongCode;
        this.dongName = dongName;
        this.sidoName = sidoName;
        this.gugunName = gugunName;
        this.buildYear = buildYear;
        this.jibun = jibun;
        this.lat = lat;
        this.lng = lng;
        this.img = getImg();
        this.recentPrice = recentPrice;
        this.likedStatus =false;
    }

    public String getImg() {
        int value = random.nextInt(max) + min;
        return "http://localhost:9000/images/apt-image" + value + ".jpg";
    }
}
