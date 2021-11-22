package com.ssafy.realestate.inquiry.service;

import com.ssafy.realestate.inquiry.dto.*;
import com.ssafy.realestate.inquiry.enitity.Inquiry;
import com.ssafy.realestate.inquiry.enitity.InquiryAnswer;
import com.ssafy.realestate.inquiry.exception.NoInquiryIdException;
import com.ssafy.realestate.inquiry.repository.InquiryAnswerRepository;
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
    private final InquiryAnswerRepository inquiryAnswerRepository;
    private final UserRepository userRepository;

    public List<InquiryResponseDto> findByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(NoUserException::new);
        List<Inquiry> inquiries = inquiryRepository.findByUserId(user.getId());
        return inquiries.stream().map(InquiryResponseDto::from).collect(Collectors.toList());
    }

    public InquiryResponseDto findById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(NoInquiryIdException::new);
        return InquiryResponseDto.from(inquiry);
    }

    @Transactional
    public InquiryResponseDto save(InquiryRequestDto inquiryRequestDto) {
        UserEntity user = userRepository.findById(inquiryRequestDto.getUserId()).orElseThrow(NoUserException::new);
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
                .inquiryAnswer(originInquiry.getInquiryAnswer())
                .build();
        return InquiryResponseDto.from(inquiryRepository.save(inquiry));
    }

    public Long answerUpdate(InquiryAnswerUpdateDto inquiryAnswerUpdateDto) {
        InquiryAnswer update = inquiryAnswerUpdateDto.toUpdateInquiryAnswerEntity();
        InquiryAnswer origin = inquiryAnswerRepository.findById(inquiryAnswerUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("답글 ID가 존재하지 않습니다."));

        InquiryAnswer inquiryAnswer = InquiryAnswer.builder()
                .id(origin.getId())
                .inquiry(origin.getInquiry())
                .answerContent(update.getAnswerContent())
                .build();
        return inquiryAnswerRepository.save(inquiryAnswer).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id).orElseThrow(NoInquiryIdException::new);
        inquiryRepository.deleteById(inquiry.getId());
    }

    public Long answerSave(InquiryAnswerDto inquiryAnswerDto) {
        Inquiry inquiry = inquiryRepository.findById(inquiryAnswerDto.getInquiryId()).orElseThrow(NoInquiryIdException::new);
        InquiryAnswer inquiryAnswer = inquiryAnswerRepository.save(inquiryAnswerDto.toSaveInquiryAnswerEntity(inquiry));
        return inquiryAnswer.getId();
    }

    public Long answerDelete(Long id) {
        inquiryAnswerRepository.deleteById(id);
        return id;
    }
}
