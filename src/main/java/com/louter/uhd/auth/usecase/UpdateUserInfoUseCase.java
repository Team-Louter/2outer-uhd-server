package com.louter.uhd.auth.usecase;

import com.louter.uhd.auth.domain.User;
import com.louter.uhd.auth.dto.request.UpdatePwRequest;
import com.louter.uhd.auth.exception.UserNotFoundException;
import com.louter.uhd.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateUserInfoUseCase {
    // 디비 접근
    private final UserRepository userRepository;
    // 비밀번호 암호화
    private final PasswordEncoder passwordEncoder;
    // 예외 분리
    private final AuthValidationUseCase authValidationUseCase;

    public User updateUserPassword(UpdatePwRequest updatePwRequest) {
        String userPassword = updatePwRequest.getUserPassword();
        String userId = updatePwRequest.getUserId();

        authValidationUseCase.checkUserId(userId);

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        User user = optionalUser.orElseThrow(() ->
                new UserNotFoundException("이메일이 조회되지 않음"));

        authValidationUseCase.checkUserPassword(userPassword);

        // 디비 수정
        userRepository.updateUserPassword(userId, passwordEncoder.encode(userPassword));

        return user;
    }
}
