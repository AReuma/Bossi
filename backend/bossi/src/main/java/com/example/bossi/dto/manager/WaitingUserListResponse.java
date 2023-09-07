package com.example.bossi.dto.manager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class WaitingUserListResponse {

    private Long id;
    private String email;
    private String sendEmail;
}
