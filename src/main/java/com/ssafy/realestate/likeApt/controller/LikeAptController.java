package com.ssafy.realestate.likeApt.controller;

import com.ssafy.realestate.likeApt.dto.LikeAptRequestDto;
import com.ssafy.realestate.likeApt.dto.LikeAptResponseDto;
import com.ssafy.realestate.likeApt.service.LikeAptService;
import com.ssafy.realestate.map.dto.HouseInfoResponseDto;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liked-apt-codes")
@Slf4j
public class LikeAptController {
    private final LikeAptService likeAptService;

    @GetMapping
    public ResponseEntity<List<HouseInfoResponseDto>> getUserLikeApt(@RequestParam("userId") Long userId) {
        return new ResponseEntity(likeAptService.findByUserIdHouseList(userId), HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikeAptResponseDto>> getUserLikeAptCode(@RequestParam("userId") Long userId) {
        return new ResponseEntity(likeAptService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Long> addLikeApt(@RequestBody @Valid LikeAptRequestDto likeAptDto) {
        return ResponseEntity.created(URI.create("/" + likeAptService.addLikeApt(likeAptDto))).build();
    }

    @DeleteMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Long> deleteLikeApt(@RequestParam("userId") Long userId, @RequestParam("aptCode") String aptCode) {
        likeAptService.delete(new LikeAptRequestDto(userId, aptCode));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}