package com.example.Army.SecuritySystem.Token;

import com.example.Army.SecuritySystem.Member.Member;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Integer tokenId;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    private Member member;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, Member member) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.member = member;
    }
}
