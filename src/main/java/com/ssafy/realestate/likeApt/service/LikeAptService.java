package com.ssafy.realestate.likeApt.service;


import com.ssafy.realestate.likeApt.dto.LikeAptRequestDto;
import com.ssafy.realestate.likeApt.dto.LikeAptResponseDto;
import com.ssafy.realestate.likeApt.entity.LikeApt;
import com.ssafy.realestate.likeApt.repository.LikeAptRepository;
import com.ssafy.realestate.map.dto.HouseInfoResponseDto;
import com.ssafy.realestate.map.service.HouseMapService;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.exception.NoUserException;
import com.ssafy.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeAptService {

    private final LikeAptRepository likeAptRepository;
    private final UserRepository userRepository;
    private final HouseMapService houseMapService;

    public List<HouseInfoResponseDto> findByUserIdHouseList(Long id) {
        List<LikeApt> userLikeAptList = likeAptRepository.findByUserId(id);
        if (userLikeAptList.isEmpty()) {
            return null;
        }
        List<String> aptCode = new ArrayList<>();
        for (LikeApt likeApt : userLikeAptList) {
            aptCode.add(likeApt.getAptCode());
        }
        List<HouseInfoResponseDto> list = houseMapService.likedAptList(aptCode);
        for (HouseInfoResponseDto l : list) {
            l.setLikedStatus(true);
        }
        return list;
    }

    public List<LikeAptResponseDto> findByUserId(Long id) {
        List<LikeApt> userLikeAptList = likeAptRepository.findByUserId(id);
        if (userLikeAptList.isEmpty()) {
            return null;
        }
        return userLikeAptList.stream().map(LikeAptResponseDto::from).collect(Collectors.toList());
    }

    @Transactional
    public int addLikeApt(LikeAptRequestDto likeAptRequestDto) {
        UserEntity user = userRepository.findById(likeAptRequestDto.getUserId())
                .orElseThrow(NoUserException::new);
        return likeAptRepository.saveLikeAptCode(likeAptRequestDto.getUserId(), likeAptRequestDto.getAptCode());
    }

    @Transactional
    public void delete(LikeAptRequestDto likeAptRequestDto) {
        if (likeAptRepository.findByUserIdAndAptCode(likeAptRequestDto.getUserId(), likeAptRequestDto.getAptCode()).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 관심 목록입니다");
        }
        likeAptRepository.deleteByUserIdByAptCode(likeAptRequestDto.getUserId(), likeAptRequestDto.getAptCode());
    }
}