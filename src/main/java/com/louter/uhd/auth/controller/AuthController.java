package com.louter.uhd.auth.controller;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.SendVerificationEmailRequest;
import com.louter.uhd.auth.dto.request.SignupRequest;
import com.louter.uhd.auth.dto.response.SendVerificationEmailResponse;
import com.louter.uhd.auth.dto.response.SignupResponse;
import com.louter.uhd.auth.usecase.EmailUseCase;
import com.louter.uhd.auth.usecase.SignupUseCase;
import jakarta.validation.constraints.Email;
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
    // 이메일
    private final EmailUseCase emailUseCase;

    @PostMapping("/email/send")
    public ResponseEntity<Object> signupEmail(@RequestBody SendVerificationEmailRequest request) {
        Map<User, String> response = emailUseCase.sendVerificationEmail(request);
        return ResponseEntity.ok(SendVerificationEmailResponse.from(response));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        User user = signupUseCase.signup(signupRequest);
        return ResponseEntity.ok(SignupResponse.from(user));
    }
}
