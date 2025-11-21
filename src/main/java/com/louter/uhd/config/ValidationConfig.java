package com.louter.uhd.config;

import com.louter.uhd.auth.repository.UserRepository;
import com.louter.uhd.auth.usecase.AuthValidationUseCase;
import com.louter.uhd.common.usecase.ValidationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public ValidationUseCase validationUseCase(UserRepository userRepository) {
        return new ValidationUseCase(userRepository);
    }

    @Bean
    public AuthValidationUseCase authValidationUseCase(ValidationUseCase validationUseCase, UserRepository userRepository) {
        return new AuthValidationUseCase(validationUseCase, userRepository);
    }
}
