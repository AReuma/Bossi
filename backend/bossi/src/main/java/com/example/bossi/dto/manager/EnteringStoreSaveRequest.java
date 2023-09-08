package com.example.bossi.dto.manager;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Schema(description = "입점 스토어 저장 관련 ")
public class EnteringStoreSaveRequest {

    @NotBlank
    private Long id;

    @NotBlank
    private boolean enteringStatus;
}
