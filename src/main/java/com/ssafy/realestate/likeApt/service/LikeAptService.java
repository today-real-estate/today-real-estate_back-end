package com.ssafy.realestate.likeApt.service;


import com.ssafy.realestate.likeApt.dto.LikeAptRequestDto;
import com.ssafy.realestate.likeApt.dto.LikeAptResponseDto;
import com.ssafy.realestate.likeApt.entity.LikeApt;
import com.ssafy.realestate.likeApt.repository.LikeAptRepository;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.exception.NoUserFoundException;
import com.ssafy.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeAptService {
    private final LikeAptRepository likeAptRepository;
    private final UserRepository userRepository;

    public List<LikeAptResponseDto> findAll() {
        List<LikeApt> likeAptList = likeAptRepository.findAll();
        return likeAptList.stream().map(LikeAptResponseDto::from).collect(Collectors.toList());
    }


    public List<LikeAptResponseDto> findByUserId(Long id) {
        List<LikeApt> userLikeAptList = likeAptRepository.findByUserId(id);
        return userLikeAptList.stream()
                .map(LikeAptResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public LikeAptResponseDto addLikeApt(LikeAptRequestDto likeAptRequestDto) {
        UserEntity user = userRepository.findById(likeAptRequestDto.getUserId())
                .orElseThrow(NoUserFoundException::new);
        return LikeAptResponseDto.from(likeAptRepository.save(LikeApt.builder()
                .user(user)
                .aptCode(likeAptRequestDto.getAptCode())
                .build()));
    }

    @Transactional
    public void delete(Long id) {
        if (!likeAptRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("존재하지 않는 관심 목록 입니다.");
        }
        likeAptRepository.deleteById(id);
    }
}