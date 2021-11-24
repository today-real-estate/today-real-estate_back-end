package com.ssafy.realestate.likeApt.controller;


import com.ssafy.realestate.likeApt.dto.LikeAptRequestDto;
import com.ssafy.realestate.likeApt.dto.LikeAptResponseDto;
import com.ssafy.realestate.likeApt.entity.LikeApt;
import com.ssafy.realestate.likeApt.service.LikeAptService;
import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/liked-apt-codes")
@Slf4j
public class LikeAptController {
    private final LikeAptService likeAptService;

    @GetMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikeAptResponseDto>> getLikeAptList() {
        return ResponseEntity.ok(likeAptService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<HouseInfoDto>> getUserLikeApt(@PathVariable Long id) throws SQLException {
        return new ResponseEntity(likeAptService.findByUserIdHouseList(id), HttpStatus.OK);
    }

    @GetMapping("/list")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<HouseInfoDto>> getUserLikeAptCode(@RequestParam("userId") Long userId) {
        return new ResponseEntity(likeAptService.findByUserId(userId), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Long> addLikeApt(@RequestBody @Valid LikeAptRequestDto likeAptDto) {
        LikeAptResponseDto savedLikeApt = likeAptService.addLikeApt(likeAptDto);
        return ResponseEntity.created(URI.create("/liked-apt-codes")).build();
    }

    @DeleteMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Long> deleteLikeApt(@RequestBody LikeAptRequestDto likeAptRequestDto) {
        likeAptService.delete(likeAptRequestDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}