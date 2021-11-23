package com.ssafy.realestate.likeApt.controller;


import com.ssafy.realestate.likeApt.dto.LikeAptRequestDto;
import com.ssafy.realestate.likeApt.dto.LikeAptResponseDto;
import com.ssafy.realestate.likeApt.entity.LikeApt;
import com.ssafy.realestate.likeApt.service.LikeAptService;
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
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikeAptResponseDto>> getLikeAptList() {
        return ResponseEntity.ok(likeAptService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikeApt>> getUserLikeApt(@PathVariable Long id) {
        List<LikeAptResponseDto> userLikeAptList = likeAptService.findByUserId(id);
        return new ResponseEntity(userLikeAptList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Long> addLikeApt(@RequestBody @Valid LikeAptRequestDto likeAptDto) {
        LikeAptResponseDto savedLikeApt = likeAptService.addLikeApt(likeAptDto);
        return ResponseEntity.created(URI.create("/" + savedLikeApt.getId())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Long> deleteLikeApt(@PathVariable Long id) {
        likeAptService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}