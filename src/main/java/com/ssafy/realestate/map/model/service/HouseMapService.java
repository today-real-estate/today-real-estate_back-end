package com.ssafy.realestate.map.model.service;


import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.map.model.SidoGugunCodeDto;

import java.util.List;

public interface HouseMapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;
	List<HouseInfoDto> getAptInDong(String dong) throws Exception;
	HouseInfoDto getAptName(String aptCode);
}
