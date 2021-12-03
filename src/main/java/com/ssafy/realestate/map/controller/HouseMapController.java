package com.ssafy.realestate.map.controller;


import com.ssafy.realestate.map.dto.DongResponseDto;
import com.ssafy.realestate.map.dto.GugunResponseDto;
import com.ssafy.realestate.map.dto.HouseInfoResponseDto;
import com.ssafy.realestate.map.dto.SidoResponseDto;
import com.ssafy.realestate.map.service.HouseMapService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/map")
public class HouseMapController {

    private final HouseMapService houseMapService;

    @GetMapping("/sido")
    public ResponseEntity<List<SidoResponseDto>> sido() {
        return new ResponseEntity<List<SidoResponseDto>>(houseMapService.getSido(), HttpStatus.OK);
    }

    @GetMapping("/gugun")
    public ResponseEntity<List<GugunResponseDto>> gugun(@RequestParam("sido") String sido) {
        return new ResponseEntity<List<GugunResponseDto>>(houseMapService.getGugunInSido(sido), HttpStatus.OK);
    }

    @GetMapping("/dong")
    public ResponseEntity<List<DongResponseDto>> dong(@RequestParam("gugun") String gugun) {
        return new ResponseEntity<List<DongResponseDto>>(houseMapService.getDongInGugun(gugun), HttpStatus.OK);
    }

    @GetMapping("/gu/apt")
    public ResponseEntity<List<HouseInfoResponseDto>> guApt(@RequestParam("gugun") String gugun) {
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getguApt(gugun), HttpStatus.OK);
    }

    @GetMapping("/apt")
    public ResponseEntity<List<HouseInfoResponseDto>> apt(@RequestParam("dong") String dong) {
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getAptInDong(dong), HttpStatus.OK);
    }

    @GetMapping("/dong-search")
    public ResponseEntity<List<HouseInfoResponseDto>> getDongNameSearch(@RequestParam("dongName") String dongName) {
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getDongNameSearch(dongName), HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<HouseInfoResponseDto>> getRecommend(@RequestParam("dongName") String dongName) {
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getRecommend(dongName), HttpStatus.OK);
    }

    @GetMapping("/gu/apt/user")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<HouseInfoResponseDto>> getUserLikedGuApt(@RequestParam("gugun") String gugun, @RequestParam("userId") Long userId) {
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getUserLikedGuApt(gugun,userId), HttpStatus.OK);
    }

    @GetMapping("/apt/user")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<HouseInfoResponseDto>> getUserLikedAptInDong(@RequestParam("dong") String dong, @RequestParam("userId") Long userId){
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getUserLikedAptInDong(dong,userId), HttpStatus.OK);
    }

    @GetMapping("/dong-search/user")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<HouseInfoResponseDto>> getUserLikedDongName(@RequestParam("dongName") String dongName, @RequestParam("userId") Long userId) throws Exception {
        return new ResponseEntity<List<HouseInfoResponseDto>>(houseMapService.getUserLikedDongNameSearch(dongName,userId), HttpStatus.OK);
    }
}
