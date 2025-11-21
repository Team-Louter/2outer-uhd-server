package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserInfoUseCase {
    // 디비 접근
    private final UserRepository userRepository;
    // 예외 호출
    private final AuthValidationUseCase authValidationUseCase;
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;

    // 이메일 찾기
    public User findUserEmailByIdAndPw(FindEmailRequest findEmailRequest) {
        String userId = findEmailRequest.getUserId();
        String userPassword = findEmailRequest.getUserPassword();

        authValidationUseCase.checkUserId(userId);
        authValidationUseCase.checkUserPassword(userPassword);

        return userRepository.findByUserId(userId)
                // 필터로 비밀번호 매치 로직 추가
                .filter(user -> passwordEncoder.matches(userPassword, user.getUserPassword()))
                .orElseThrow(() -> new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않음"));
    }

    // 아이디 찾기
    public User findUserIdByIdAndPw(FindIdRequest findIdRequest) {
        String userEmail = findIdRequest.getUserEmail();
        String userPassword = findIdRequest.getUserPassword();

        authValidationUseCase.checkUserEmail(userEmail);
        authValidationUseCase.checkUserPassword(userPassword);

        return userRepository.findByUserEmail(userEmail)
                // 필터로 비밀번호 매치 로직 추가
                .filter(user -> passwordEncoder.matches(userPassword, user.getUserPassword()))
                .orElseThrow(() -> new UserNotFoundException("아이디 또는 비밀번호가 올바르지 않음"));
    }
}
