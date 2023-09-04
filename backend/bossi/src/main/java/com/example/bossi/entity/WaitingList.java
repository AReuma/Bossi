package com.example.bossi.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
