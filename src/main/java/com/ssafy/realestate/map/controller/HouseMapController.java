package com.ssafy.realestate.map.controller;


import com.ssafy.realestate.likeApt.dto.LikeAptResponseDto;
import com.ssafy.realestate.likeApt.service.LikeAptService;
import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.map.model.LikedHouseInfoDto;
import com.ssafy.realestate.map.model.SidoGugunCodeDto;
import com.ssafy.realestate.map.model.service.HouseMapService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/map")
public class HouseMapController {
    @Autowired
    private HouseMapService haHouseMapService;
    @Autowired
    private LikeAptService likeAptService;

    @GetMapping("/sido")
    public ResponseEntity<List<SidoGugunCodeDto>> sido() throws Exception {
        return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getSido(), HttpStatus.OK);
    }

    @GetMapping("/gugun")
    public ResponseEntity<List<SidoGugunCodeDto>> gugun(@RequestParam("sido") String sido) throws Exception {

        return new ResponseEntity<List<SidoGugunCodeDto>>(haHouseMapService.getGugunInSido(sido), HttpStatus.OK);
    }

    @GetMapping("/gu/apt")
    public ResponseEntity<List<HouseInfoDto>> guApt(@RequestParam("gugun") String gugun) throws Exception {
        return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.guApt(gugun), HttpStatus.OK);
    }


    @GetMapping("/dong")
    public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
        return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getDongInGugun(gugun), HttpStatus.OK);
    }

    @GetMapping("/dong-search")
    public ResponseEntity<List<HouseInfoDto>> dongNameSearch(@RequestParam("dongName") String dongName) throws Exception {
        return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.dongNameSearch(dongName), HttpStatus.OK);
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<HouseInfoDto>> recommend(@RequestParam("dongName") String dongName) throws Exception {
        return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.recommend(dongName), HttpStatus.OK);
    }

    @GetMapping("/apt")
    public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dong") String dong) throws Exception {
        return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.getAptInDong(dong), HttpStatus.OK);
    }

    @GetMapping("/apt-name")
    public ResponseEntity<HouseInfoDto> getAptName(@RequestParam("aptCode") String aptCode) throws Exception {
        return new ResponseEntity<HouseInfoDto>(haHouseMapService.getAptName(aptCode), HttpStatus.OK);
    }

    @GetMapping("/liked-apt-codes")
    public ResponseEntity<List<HouseInfoDto>> likedAptList(@RequestParam("aptCodes") List<String> aptCodes) throws Exception {
        log.info(aptCodes.toString());
        return new ResponseEntity<List<HouseInfoDto>>(haHouseMapService.likedAptList(aptCodes), HttpStatus.OK);
    }

    @GetMapping("/dong-search/user")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikedHouseInfoDto>> dongNameLiked(@RequestParam("dongName") String dongName, @RequestParam("userId") Long userId) throws Exception {
        HashMap<Integer, Boolean> map = likedStatusSet(userId);
        List<LikedHouseInfoDto> likedHouseInfoDto = haHouseMapService.likeDongNameSearch(dongName);
        if (map != null) {
            for (LikedHouseInfoDto l : likedHouseInfoDto) {
                if (map.get(l.getAptCode()) == null) {
                    continue;
                }
                l.setLikedStatus(true);
            }
        }
        return new ResponseEntity<List<LikedHouseInfoDto>>(likedHouseInfoDto, HttpStatus.OK);
    }

    @GetMapping("/gu/apt/user")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikedHouseInfoDto>> guAptLiked(@RequestParam("gugun") String gugun, @RequestParam("userId") Long userId) throws Exception {
        HashMap<Integer, Boolean> map = likedStatusSet(userId);
        List<LikedHouseInfoDto> likedHouseInfoDto = haHouseMapService.guAptLiked(gugun);
        if (map != null) {
            for (LikedHouseInfoDto l : likedHouseInfoDto) {
                if (map.get(l.getAptCode()) == null) {
                    continue;
                }
                l.setLikedStatus(true);
            }
        }
        return new ResponseEntity<List<LikedHouseInfoDto>>(likedHouseInfoDto, HttpStatus.OK);
    }

    @GetMapping("/apt/user")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<LikedHouseInfoDto>> getAptInDongLike(@RequestParam("dong") String dong, @RequestParam("userId") Long userId) throws Exception {
        HashMap<Integer, Boolean> map = likedStatusSet(userId);
        List<LikedHouseInfoDto> likedHouseInfoDto = haHouseMapService.getAptInDongLike(dong);
        if (map != null) {
            for (LikedHouseInfoDto l : likedHouseInfoDto) {
                if (map.get(l.getAptCode()) == null) {
                    continue;
                }
                l.setLikedStatus(true);
            }
        }
        return new ResponseEntity<List<LikedHouseInfoDto>>(likedHouseInfoDto, HttpStatus.OK);
    }

    public HashMap<Integer, Boolean> likedStatusSet(Long userId) {
        List<LikeAptResponseDto> likeApt = likeAptService.findByUserId(userId);
        if (likeApt == null) {
            return null;
        }
        HashMap<Integer, Boolean> map = new HashMap<>();
        for (LikeAptResponseDto l : likeApt) {
            map.put(Integer.parseInt(l.getAptCode()), true);
        }
        return map;
    }
}
