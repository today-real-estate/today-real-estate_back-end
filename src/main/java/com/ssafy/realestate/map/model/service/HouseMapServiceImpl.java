package com.ssafy.realestate.map.model.service;


import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.map.model.SidoGugunCodeDto;
import com.ssafy.realestate.map.model.mapper.HouseMapMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HouseMapServiceImpl implements HouseMapService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<SidoGugunCodeDto> getSido() throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getSido();
	}

	@Override
	public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> guApt(String gugun) throws SQLException {
		return sqlSession.getMapper(HouseMapMapper.class).guApt(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws Exception {
		return sqlSession.getMapper(HouseMapMapper.class).getAptInDong(dong);
	}

	@Override
	public HouseInfoDto getAptName(String aptCode) throws SQLException {
		return sqlSession.getMapper(HouseMapMapper.class).getAptName(aptCode);
	}

	@Override
	public List<HouseInfoDto> dongNameSearch(String dongName) throws SQLException {
		return sqlSession.getMapper(HouseMapMapper.class).dongNameSearch(dongName);
	}

	@Override
	public List<HouseInfoDto> likedAptList(List<String> aptCodes) throws SQLException {
		return sqlSession.getMapper(HouseMapMapper.class).likedAptList(aptCodes);
	}
}
