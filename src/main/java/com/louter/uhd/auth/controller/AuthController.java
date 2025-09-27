package com.louter.uhd.auth.controller;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.SignupRequest;
import com.louter.uhd.auth.dto.response.SignupResponse;
import com.louter.uhd.auth.usecase.SignupUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final SignupUseCase signupUseCase;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequest signupRequest) {
        User user = signupUseCase.signup(signupRequest);
        return ResponseEntity.ok(SignupResponse.from(user));
    }
}
