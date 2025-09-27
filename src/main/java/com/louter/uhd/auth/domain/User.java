package com.louter.uhd.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @Column(name = "u_id", unique = true, nullable = false)
    private String userId;

    @Column(name = "u_password", nullable = false)
    private String userPassword;

    @Column(name = "u_name", nullable = false)
    private String userName;

    @Column(name = "u_email", unique = true, nullable = false)
    private String userEmail;
}
