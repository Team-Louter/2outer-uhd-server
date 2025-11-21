package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.SignupRequest;
import com.louter.uhd.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupUseCase {
    // 디비 접근
    private final UserRepository userRepository;
    // 예외 호출
    private final AuthValidationUseCase authValidationUseCase;
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public User signup(SignupRequest signupRequest) {
        // 기본 예외 처리
        authValidationUseCase.checkNull(signupRequest);

        String userEmail = signupRequest.getUserEmail();
        String userId = signupRequest.getUserId();
        String userPassword = signupRequest.getUserPassword();
        String userName = signupRequest.getUserName();

        authValidationUseCase.checkUserEmail(userEmail);
        authValidationUseCase.checkUserId(userId);
        authValidationUseCase.checkUserPassword(userPassword);
        authValidationUseCase.checkUserName(userName);

        authValidationUseCase.checkExistAccount(userEmail, userId);

        // 유저 생성
        User user = User.builder()
                .userEmail(userEmail)
                .userId(userId)
                .userPassword(passwordEncoder.encode(userPassword))
                .userName(userName)
                .build();

        // 유저 등록
        return userRepository.save(user);
    }
}
