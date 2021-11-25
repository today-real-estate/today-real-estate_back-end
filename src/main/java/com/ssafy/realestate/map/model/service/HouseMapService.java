package com.ssafy.realestate.map.model.service;


import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.map.model.LikedHouseInfoDto;
import com.ssafy.realestate.map.model.SidoGugunCodeDto;

import java.sql.SQLException;
import java.util.List;

public interface HouseMapService {

    List<SidoGugunCodeDto> getSido() throws Exception;

    List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

    List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;

    List<HouseInfoDto> guApt(String gugun) throws SQLException;

    List<HouseInfoDto> getAptInDong(String dong) throws Exception;

    HouseInfoDto getAptName(String aptCode) throws SQLException;

    List<HouseInfoDto> dongNameSearch(String dongName) throws SQLException;

    List<LikedHouseInfoDto> likedAptList(List<String> aptCodes) throws SQLException;

    List<HouseInfoDto> recommend(String dongName) throws SQLException;

    List<LikedHouseInfoDto> likeDongNameSearch(String dongName) throws SQLException;

    List<LikedHouseInfoDto> guAptLiked(String gugun) throws SQLException;

    List<LikedHouseInfoDto> getAptInDongLike(String dong) throws SQLException;
}