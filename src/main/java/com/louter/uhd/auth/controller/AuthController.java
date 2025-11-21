package com.louter.uhd.auth.controller;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.*;
import com.louter.uhd.auth.dto.response.*;
import com.louter.uhd.auth.usecase.AuthEmailUseCase;
import com.louter.uhd.auth.usecase.LoginUseCase;
import com.louter.uhd.auth.usecase.SignupUseCase;
import com.louter.uhd.auth.usecase.UpdateUserInfoUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    // 회원가입
    private final SignupUseCase signupUseCase;
    // 로그인
    private final LoginUseCase loginUseCase;
    // 이메일
    private final AuthEmailUseCase authEmailUseCase;
    // 정보 수정
    private final UpdateUserInfoUseCase updateUserInfoUseCase;

    @PostMapping("/email/send")
    public ResponseEntity<Object> signupEmail(@RequestBody SendVerificationEmailRequest request) {
        User response = authEmailUseCase.sendVerificationEmail(request);
        return ResponseEntity.ok(SendVerificationEmailResponse.from(response));
    }

    @PostMapping("/email/verify")
    public ResponseEntity<Object> verifyCode(@RequestBody VerifyCodeRequest request) {
        User response = authEmailUseCase.verifyCode(request);
        return ResponseEntity.ok(VerifyCodeResponse.from(response));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        User user = signupUseCase.signup(signupRequest);
        return ResponseEntity.ok(SignupResponse.from(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
        Map<User, Object> response = loginUseCase.login(loginRequest);
        return ResponseEntity.ok(LoginResponse.from(response));
    }

    @PostMapping("/update/pw")
    public ResponseEntity<Object> updateUserId(@RequestBody UpdatePwRequest updatePwRequest) {
        // 비밀번호 변경
        User user = updateUserInfoUseCase.updateUserPassword(updatePwRequest);
        return ResponseEntity.ok(UpdatePwResponse.from(user));
    }
}
