package com.wroclawroutes.email.entity;

import com.wroclawroutes.users.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {
    @Column(nullable = false)
    private String token;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column(nullable = true)
    private LocalDateTime confirmedAt;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(
            nullable = true,
            name = "user_id"
    )
    private User user;
}
