package com.ssafy.realestate.inquiry.controller;

import com.ssafy.realestate.inquiry.dto.InquiryRequestDto;
import com.ssafy.realestate.inquiry.dto.InquiryResponseDto;
import com.ssafy.realestate.inquiry.dto.InquiryUpdateDto;
import com.ssafy.realestate.inquiry.service.InquiryService;
import com.ssafy.realestate.user.annotation.PreAuthorize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/inquiries")
public class InquiryController {
    private final InquiryService inquiryService;

    @GetMapping
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<List<InquiryResponseDto>> findByUserId(@RequestParam("userId")Long userId) {
        return ResponseEntity.ok(inquiryService.findByUserId(userId));
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<InquiryResponseDto> findById(@PathVariable("id") Long id) {
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
        return ResponseEntity.ok("id :"+inquiryService.update(inquiryUpdateDto).getId()+" 수정완료");
    }

    @DeleteMapping("/delete")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<String> delete(@RequestBody Map<String,Long> object) {
        inquiryService.deleteById(object.get("id"));
        return ResponseEntity.ok("id :"+object.get("id")+" 삭제완료");
    }

}
