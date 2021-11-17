package com.ssafy.realestate.user.controller;

import com.ssafy.realestate.user.dto.UserLoginRequestDto;
import com.ssafy.realestate.user.dto.UserResponseDto;
import com.ssafy.realestate.user.dto.UserSignupDto;
import com.ssafy.realestate.user.dto.UserTokenInfoDto;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.jwt.AuthInterceptor;
import com.ssafy.realestate.user.jwt.TokenProvider;
import com.ssafy.realestate.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.ssafy.realestate.user.jwt.AuthInterceptor.TOKEN_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserManagementService userManagementService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<UserTokenInfoDto> login(@RequestBody @Valid UserLoginRequestDto userLoginDto) {
        UserEntity userEntity = userManagementService.login(userLoginDto);
        String jwt = tokenProvider.createToken(userEntity);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(AuthInterceptor.AUTHORIZATION_HEADER, TOKEN_HEADER + jwt);
        return new ResponseEntity<>(new UserTokenInfoDto(userEntity.getId(), userEntity.getUserName(), userEntity.getUserEmail(), jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid UserSignupDto userSignUpDto) throws Exception {
        UserResponseDto savedUser = userManagementService.save(userSignUpDto);
        return ResponseEntity.ok().body(savedUser.getId());
    }
}
