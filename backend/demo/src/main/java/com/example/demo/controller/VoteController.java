package com.example.demo.controller;

import com.example.demo.dto.VoteRequest;
import com.example.demo.dto.VoteResponse;
import com.example.demo.dto.VoteResultResponse;
import com.example.demo.service.VoteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class VoteController {

    private final VoteService voteService;

    // 투표하기
    // POST /api/votes
    @PostMapping("/votes")
    public ResponseEntity<VoteResponse> vote(@Valid @RequestBody VoteRequest req,
                                             HttpServletRequest httpReq) {
        String ip = extractClientIp(httpReq);
        VoteResponse res = voteService.vote(req, ip);
        return ResponseEntity.ok(res);
    }

    // 결과보기
    // GET /api/votes/result
    @GetMapping("/votes/result")
    public ResponseEntity<VoteResultResponse> result() {
        return ResponseEntity.ok(voteService.getResult());
    }

    private String extractClientIp(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        if (xf != null && !xf.isBlank()) {
            // XFF는 "client, proxy1, proxy2" 형태일 수 있음
            return xf.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
