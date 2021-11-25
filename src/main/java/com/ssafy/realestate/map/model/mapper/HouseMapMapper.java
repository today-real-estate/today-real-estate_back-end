package com.ssafy.realestate.map.model.mapper;


import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.map.model.LikedHouseInfoDto;
import com.ssafy.realestate.map.model.SidoGugunCodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface HouseMapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;
	List<HouseInfoDto> guApt(String gugun)throws SQLException;
	List<HouseInfoDto> getAptInDong(String dong) throws SQLException;
    HouseInfoDto getAptName(String aptCode)throws SQLException;
	List<HouseInfoDto> dongNameSearch(String dongName)throws SQLException;
	List<HouseInfoDto> recommend(String dongName)throws SQLException;
	List<LikedHouseInfoDto> likedAptList(List<String> aptCodes)throws SQLException;
	List<LikedHouseInfoDto> likeDongNameSearch(String dongName)throws SQLException;
	List<LikedHouseInfoDto> guAptLiked(String gugun)throws SQLException;
	List<LikedHouseInfoDto> getAptInDongLike(String dong)throws SQLException;
}
