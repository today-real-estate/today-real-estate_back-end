package com.ssafy.realestate.inquiry.service;

import com.ssafy.realestate.inquiry.dto.InquiryRequestDto;
import com.ssafy.realestate.inquiry.dto.InquiryResponseDto;
import com.ssafy.realestate.inquiry.dto.InquiryUpdateDto;
import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.inquiry.repository.InquiryRepository;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.exception.NoUserFoundException;
import com.ssafy.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquiryService {
    private final InquiryRepository inquiryRepository;
    private final UserRepository userRepository;

    public List<InquiryResponseDto> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        List<Inquiry> inquiries = inquiryRepository.findByUserId(user.getId());
        return inquiries.stream().map(InquiryResponseDto::from).collect(Collectors.toList());
    }

    public InquiryResponseDto findById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 문의 입니다"));
        return InquiryResponseDto.from(inquiry);
    }

    @Transactional
    public InquiryResponseDto save(InquiryRequestDto inquiryRequestDto) {
        UserEntity user = userRepository.findById(inquiryRequestDto.getUserId())
                .orElseThrow(NoUserFoundException::new);
        return InquiryResponseDto.from(inquiryRepository.save(inquiryRequestDto.toSaveInquiryEntity(user)));

    }

    @Transactional
    public InquiryResponseDto update(InquiryUpdateDto inquiryUpdateDto) {
        Inquiry updateInquiry = inquiryUpdateDto.toUpdateInquiryEntity();
        Inquiry originInquiry = inquiryRepository.findById(inquiryUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다 id =" + updateInquiry.getId()));

        Inquiry inquiry = Inquiry.builder()
                .id(updateInquiry.getId())
                .inquiryType(updateInquiry.getInquiryType())
                .title(updateInquiry.getTitle())
                .content(updateInquiry.getContent())
                .user(originInquiry.getUser())
                .build();
        return InquiryResponseDto.from(inquiryRepository.save(inquiry));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!inquiryRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 문의 입니다");
        }
        inquiryRepository.deleteById(id);
    }


}
