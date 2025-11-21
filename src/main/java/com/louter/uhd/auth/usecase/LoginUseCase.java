package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.LoginRequest;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.jwt.JwtTokenProvider;
import com.louter.uhd.auth.repository.UserRepository;
import com.louter.uhd.common.usecase.ValidationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginUseCase {
    // 디비 접근
    private final UserRepository userRepository;
    // 예외 이용
    private final AuthValidationUseCase authValidationUseCase;
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    // Jwt 토큰 생성
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인, 토큰 반환
    public Map<User, Object> login(LoginRequest loginRequest) {
        // 기본 예외 확인
        authValidationUseCase.checkNull(loginRequest);

        String userId = loginRequest.getUserId();
        String userPassword = loginRequest.getUserPassword();

        authValidationUseCase.checkUserId(userId);
        authValidationUseCase.checkUserPassword(userPassword);

        Optional<User> optionalUser = userRepository.findByUserId(userId);

        // 유저 조회
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("유저가 조회되지 않음");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            throw new UserNotFoundException("유저 조회 실패");
        }

        // 토큰 생성 및 반환
        return Map.of(user, jwtTokenProvider.generateToken(String.valueOf(user.getUserId())));
    }
}
