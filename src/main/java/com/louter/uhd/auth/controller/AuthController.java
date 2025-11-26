package com.louter.uhd.auth.controller;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.*;
import com.louter.uhd.auth.dto.response.*;
import com.louter.uhd.auth.usecase.AuthEmailUseCase;
import com.louter.uhd.auth.usecase.LoginUseCase;
import com.louter.uhd.auth.usecase.SignupUseCase;
import com.louter.uhd.auth.usecase.UpdateUserInfoUseCase;
import com.louter.uhd.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<SendVerificationEmailResponse>> signupEmail(@RequestBody SendVerificationEmailRequest request) {
        User response = authEmailUseCase.sendVerificationEmail(request);
        return ResponseEntity.ok(ApiResponse.success(SendVerificationEmailResponse.from(response)));
    }

    @PostMapping("/email/verify")
    public ResponseEntity<ApiResponse<VerifyCodeResponse>> verifyCode(@RequestBody VerifyCodeRequest request) {
        User response = authEmailUseCase.verifyCode(request);
        return ResponseEntity.ok(ApiResponse.success(VerifyCodeResponse.from(response)));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest signupRequest) {
        User user = signupUseCase.signup(signupRequest);
        return ResponseEntity.ok(ApiResponse.success(SignupResponse.from(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        Map<User, Object> response = loginUseCase.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(LoginResponse.from(response)));
    }

    @PostMapping("/update/pw")
    public ResponseEntity<ApiResponse<UpdatePwResponse>> updateUserId(@RequestBody UpdatePwRequest updatePwRequest) {
        // 비밀번호 변경
        User user = updateUserInfoUseCase.updateUserPassword(updatePwRequest);
        return ResponseEntity.ok(ApiResponse.success(UpdatePwResponse.from(user)));
    }
}
