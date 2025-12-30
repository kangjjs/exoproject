package com.example.demo.service;

import com.example.demo.dto.VoteRequest;
import com.example.demo.dto.VoteResponse;
import com.example.demo.dto.VoteResultResponse;

public interface VoteService {
    VoteResponse vote(VoteRequest req, String voterIp);
    VoteResultResponse getResult();
}
