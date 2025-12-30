package com.example.demo.dto;

import com.example.demo.entity.VoteChoice;

import java.time.LocalDateTime;

public record VoteResponse(
        Long id,
        VoteChoice choice,
        LocalDateTime createdAt
) {}
