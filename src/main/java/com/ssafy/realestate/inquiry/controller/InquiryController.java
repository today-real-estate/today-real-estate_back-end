package com.ssafy.realestate.inquiry.controller;

import com.ssafy.realestate.inquiry.dto.InquiryRequestDto;
import com.ssafy.realestate.inquiry.dto.InquiryRequestId;
import com.ssafy.realestate.inquiry.dto.InquiryResponseDto;
import com.ssafy.realestate.inquiry.dto.InquiryUpdateDto;
import com.ssafy.realestate.inquiry.service.InquiryService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<InquiryResponseDto>> findByUserId(@RequestParam("page")int page, @RequestBody InquiryRequestId inquiryRequestId) {
        return ResponseEntity.ok(inquiryService.findByUserId(inquiryRequestId.getUserId()));
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<InquiryResponseDto> findById(@PathVariable("id") Long id,@RequestParam("userId") Long userId) {
        return ResponseEntity.ok(inquiryService.findById(id));
    }

    @PostMapping("/create")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> save(@RequestBody InquiryRequestDto inquiryRequestDto) {
        InquiryResponseDto postResponseDto = inquiryService.save(inquiryRequestDto);
        return ResponseEntity.created(URI.create("/" + postResponseDto.getId()))
                .build();
    }

    @PutMapping("/update")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> update(@RequestBody InquiryUpdateDto inquiryUpdateDto) {
        InquiryResponseDto postResponseDto = inquiryService.update(inquiryUpdateDto);
        return ResponseEntity.created(URI.create("/" + postResponseDto.getId()))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        inquiryService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
