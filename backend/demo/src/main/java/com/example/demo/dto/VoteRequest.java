package com.example.demo.dto;

import com.example.demo.entity.VoteChoice;
import jakarta.validation.constraints.NotNull;

public record VoteRequest(
        @NotNull(message = "choice는 필수입니다.")
        VoteChoice choice
) {}
