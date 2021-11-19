package com.ssafy.realestate.inquiry.service;

import com.ssafy.realestate.inquiry.dto.InquiryRequestDto;
import com.ssafy.realestate.inquiry.dto.InquiryResponseDto;
import com.ssafy.realestate.inquiry.dto.InquiryUpdateDto;
import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.inquiry.exception.NoInquiryIdException;
import com.ssafy.realestate.inquiry.exception.NoInquiryUserIdException;
import com.ssafy.realestate.inquiry.repository.InquiryRepository;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.exception.NoUserException;
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
        UserEntity user = userRepository.findById(userId).orElseThrow(NoInquiryUserIdException::new);
        List<Inquiry> inquiries = inquiryRepository.findByUserId(user.getId());
        return inquiries.stream().map(InquiryResponseDto::from).collect(Collectors.toList());
    }

    public InquiryResponseDto findById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(NoInquiryIdException::new);
        return InquiryResponseDto.from(inquiry);
    }

    @Transactional
    public InquiryResponseDto save(InquiryRequestDto inquiryRequestDto) {
        UserEntity user = userRepository.findById(inquiryRequestDto.getUserId()).orElseThrow(NoInquiryUserIdException::new);
        return InquiryResponseDto.from(inquiryRepository.save(inquiryRequestDto.toSaveInquiryEntity(user)));

    }

    @Transactional
    public InquiryResponseDto update(InquiryUpdateDto inquiryUpdateDto) {
        Inquiry updateInquiry = inquiryUpdateDto.toUpdateInquiryEntity();
        Inquiry originInquiry = inquiryRepository.findById(inquiryUpdateDto.getId())
                .orElseThrow(NoInquiryIdException::new);

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
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(NoInquiryIdException::new);
        inquiryRepository.deleteById(inquiry.getId());
    }

}
