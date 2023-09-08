package com.example.bossi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WaitingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WAITING_LIST_ID")
    private Long id;

    private String email;

    private String sendEmail;

    @Enumerated(EnumType.STRING)
    private WaitingListStatus status;

    private LocalDateTime expirationTime;

    public void updateUserWaitingUser(WaitingListStatus status){
        LocalDateTime newLocalDateTime = LocalDateTime.now();

        if(status == WaitingListStatus.ALLOW){
            this.expirationTime = newLocalDateTime.plusWeeks(1);
        }

        this.status = status;
    }
}
