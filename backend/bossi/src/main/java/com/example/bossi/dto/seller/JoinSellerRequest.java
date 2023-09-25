package com.example.bossi.dto.seller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JoinSellerRequest implements Serializable {

    private String approvedEmail;
    private String email;
    private String password;
    private String storeName;
    private String storeBio;
    private String storeIntroduction;
    @NotBlank
    private MultipartFile profileImg;
}
