package com.ssafy.realestate.map.service;

import com.ssafy.realestate.likeApt.entity.LikeApt;
import com.ssafy.realestate.likeApt.repository.LikeAptRepository;
import com.ssafy.realestate.map.dto.DongResponseDto;
import com.ssafy.realestate.map.dto.GugunResponseDto;
import com.ssafy.realestate.map.dto.HouseInfoResponseDto;
import com.ssafy.realestate.map.dto.SidoResponseDto;
import com.ssafy.realestate.map.repository.HouseMapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HouseMapService {
    private final HouseMapRepository houseMapRepository;
    private final LikeAptRepository likeAptRepository;

    public List<SidoResponseDto> getSido() {
        return houseMapRepository.getSido();
    }

    public List<GugunResponseDto> getGugunInSido(String sido) {
        return houseMapRepository.getGugunInSido(sido);
    }

    public List<DongResponseDto> getDongInGugun(String gugun) {
        return houseMapRepository.getDongInGugun(gugun);
    }

    public List<HouseInfoResponseDto> getguApt(String gugun) {
        return houseMapRepository.getguApt(gugun);
    }

    public List<HouseInfoResponseDto> getAptInDong(String dong) {
        return houseMapRepository.getAptInDong(dong);
    }

    public List<HouseInfoResponseDto> getDongNameSearch(String dongName) {
        return houseMapRepository.getDongNameSearch(dongName);
    }

    public List<HouseInfoResponseDto> getRecommend(String dongName) {
        return houseMapRepository.getRecommend(dongName);
    }
    public List<HouseInfoResponseDto> likedAptList(List<String> aptCode) {
        return houseMapRepository.likedAptList(aptCode);
    }

    public List<HouseInfoResponseDto> getUserLikedGuApt(String gugun, Long userId) {
        HashMap<Integer, Boolean> map = likedStatusSet(userId);
        List<HouseInfoResponseDto> list = houseMapRepository.getguApt(gugun);
        if (map != null) {
            for (HouseInfoResponseDto l : list) {
                if (map.get(l.getAptCode()) == null) {
                    continue;
                }
                l.setLikedStatus(true);
            }
        }
        return list;
    }

    public List<HouseInfoResponseDto> getUserLikedAptInDong(String dong, Long userId) {
        HashMap<Integer, Boolean> map = likedStatusSet(userId);
        List<HouseInfoResponseDto> list = houseMapRepository.getAptInDong(dong);
        if (map != null) {
            for (HouseInfoResponseDto l : list) {
                if (map.get(l.getAptCode()) == null) {
                    continue;
                }
                l.setLikedStatus(true);
            }
        }
        return list;
    }

    public List<HouseInfoResponseDto> getUserLikedDongNameSearch(String dongName, Long userId) {
        HashMap<Integer, Boolean> map = likedStatusSet(userId);
        List<HouseInfoResponseDto> list = houseMapRepository.getDongNameSearch(dongName);
        if (map != null) {
            for (HouseInfoResponseDto l : list) {
                if (map.get(l.getAptCode()) == null) {
                    continue;
                }
                l.setLikedStatus(true);
            }
        }
        return list;
    }

    public HashMap<Integer, Boolean> likedStatusSet(Long userId) {
        List<LikeApt> likeApt = likeAptRepository.findByUserId(userId);
        if (likeApt == null) {
            return null;
        }
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (LikeApt l : likeApt) {
            map.put(Integer.parseInt(l.getAptCode()), true);
        }
        return map;
    }
}
