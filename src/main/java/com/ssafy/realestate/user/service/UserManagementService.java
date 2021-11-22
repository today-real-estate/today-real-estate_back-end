package com.ssafy.realestate.user.service;

import com.ssafy.realestate.user.dto.*;
import com.ssafy.realestate.user.encoder.BCryptPasswordEncoder;
import com.ssafy.realestate.user.entity.Authority;
import com.ssafy.realestate.user.entity.UserAuthority;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.exception.DuplicatedEmailException;
import com.ssafy.realestate.user.exception.NoUserException;
import com.ssafy.realestate.user.exception.NoUserFoundException;
import com.ssafy.realestate.user.exception.UnmatchedPasswordCheckException;
import com.ssafy.realestate.user.repository.AuthorityRepository;
import com.ssafy.realestate.user.repository.UserAuthorityRepository;
import com.ssafy.realestate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final UserAuthorityRepository userAuthorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserEntity login(UserLoginRequestDto userLoginDto) {
        UserEntity userEntity = userRepository.findOneWithAuthoritiesByUserEmail(userLoginDto.getUserEmail())
                .orElseThrow(NoUserFoundException::new);
        if (passwordEncoder.isMatch(userLoginDto.getPassword(), userEntity.getPassword())) {
            return userEntity;
        }
        throw new UnmatchedPasswordCheckException();
    }

    @Transactional
    public UserResponseDto save(UserSignupDto userSignupDto) throws Exception {
        if (!validateDuplicatedEmail(userSignupDto.getUserEmail())) {
            throw new DuplicatedEmailException();
        }
        UserEntity signupUser = userSignupDto.toUserEntity();
        UserEntity user = UserEntity.builder()
                .userEmail(signupUser.getUserEmail())
                .password(passwordEncoder.encrypt(signupUser.getPassword()))
                .userName(signupUser.getUserName())
                .nickname(signupUser.getNickname())
                .authorities(signupUser.getAuthorities())
                .build();

        Authority auth = authorityRepository.findById(2L).orElseThrow(Exception::new);
        UserAuthority userAuth = UserAuthority.builder()
                .userEntity(user)
                .auth(auth)
                .build();
        log.info(userAuth.toString());
        userAuthorityRepository.save(userAuth);
        return UserResponseDto.from(userRepository.save(user));
    }

    public boolean validateDuplicatedEmail(String userEmail) {
        if (userRepository.existsByUserEmail(userEmail)) {
            return false;
        }
        return true;
    }

    public UserResponseDto findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return UserResponseDto.from(user.orElseThrow(NoUserException::new));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoUserException();
        }
        userRepository.deleteById(id);
    }
    @Transactional
    public void recentSearch(UserRecentSearchDto recentSearchDto) {
        UserEntity updateUser = recentSearchDto.toUserRecentSearchEntity();
        UserEntity originUser = userRepository.findById(recentSearchDto.getId()).orElseThrow(NoUserException::new);

        UserEntity user = UserEntity.builder()
                .id(originUser.getId())
                .userEmail(originUser.getUserEmail())
                .password(originUser.getPassword())
                .userName(originUser.getUserName())
                .nickname(originUser.getNickname())
                .recentSearch(recentSearchDto.getDongCode())
                .authorities(originUser.getAuthorities())
                .inquiries(originUser.getInquiries())
                .build();
        userRepository.save(user);
    }
    @Transactional
    public void updateUserName(UserUpdateDto userUpdateDto) {
        UserEntity updateUser = userUpdateDto.toUserEntity();
        UserEntity originUser = userRepository.findById(userUpdateDto.getUserId()).orElseThrow(NoUserException::new);

        UserEntity user = UserEntity.builder()
                .id(originUser.getId())
                .userEmail(originUser.getUserEmail())
                .password(originUser.getPassword())
                .userName(updateUser.getUserName())
                .nickname(originUser.getNickname())
                .recentSearch(originUser.getRecentSearch())
                .authorities(originUser.getAuthorities())
                .inquiries(originUser.getInquiries())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public void updateNickName(UpdateNickDto updateNickDto) {
        UserEntity updateUser = updateNickDto.toUserEntity();
        UserEntity originUser = userRepository.findById(updateNickDto.getUserId()).orElseThrow(NoUserException::new);

        UserEntity user = UserEntity.builder()
                .id(originUser.getId())
                .userEmail(originUser.getUserEmail())
                .password(originUser.getPassword())
                .userName(originUser.getUserName())
                .nickname(updateUser.getNickname())
                .recentSearch(originUser.getRecentSearch())
                .authorities(originUser.getAuthorities())
                .inquiries(originUser.getInquiries())
                .build();
        userRepository.save(user);
    }
}
