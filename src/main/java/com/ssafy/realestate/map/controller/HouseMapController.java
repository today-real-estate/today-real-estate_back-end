package com.ssafy.realestate.map.controller;


import com.ssafy.realestate.map.model.HouseInfoDto;
import com.ssafy.realestate.map.model.SidoGugunCodeDto;
import com.ssafy.realestate.map.model.service.HouseMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/map")
public class HouseMapController {
    @Autowired
    private HouseMapService haHouseMapService;

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

}
