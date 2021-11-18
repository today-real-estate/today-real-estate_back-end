package com.ssafy.realestate.user.controller;

import com.ssafy.realestate.user.annotation.PreAuthorize;
import com.ssafy.realestate.user.dto.*;
import com.ssafy.realestate.user.entity.UserEntity;
import com.ssafy.realestate.user.jwt.AuthInterceptor;
import com.ssafy.realestate.user.jwt.TokenProvider;
import com.ssafy.realestate.user.service.UserManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

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
        return new ResponseEntity<>(new UserTokenInfoDto(userEntity.getId(), userEntity.getUserName(), userEntity.getUserEmail(), userEntity.getNickname(), jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<Long> signUp(@RequestBody @Valid UserSignupDto userSignUpDto) throws Exception {
        UserResponseDto savedUser = userManagementService.save(userSignUpDto);
        return ResponseEntity.ok().body(savedUser.getId());
    }
    @GetMapping("/emails-check")
    public ResponseEntity<Boolean> isDuplicatedEmail(@RequestBody Map<String,String> object) {
        return new ResponseEntity(userManagementService.validateDuplicatedEmail(object.get("userEmail")), HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userManagementService.update(userUpdateDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/details")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<UserResponseDto> details(Map<String,Long> object) {
        UserResponseDto userDetails = userManagementService.findById(object.get("userId"));
        return new ResponseEntity(userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize(roles = {"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<Void> delete(@RequestBody Map<String,Long> object) {
        log.info(object.get("userId")+"zz");
        userManagementService.deleteById(object.get("userId"));
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
