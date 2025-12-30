package com.example.demo.service;

import com.example.demo.dto.VoteRequest;
import com.example.demo.dto.VoteResponse;
import com.example.demo.dto.VoteResultResponse;
import com.example.demo.entity.Vote;
import com.example.demo.entity.VoteChoice;
import com.example.demo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    @Override
    @Transactional
    public VoteResponse vote(VoteRequest req, String voterIp) {
        // (가산점) 어뷰징 방지 로직을 넣을 자리:
        // 예: 같은 IP 1회 제한 등. 지금은 요구사항 최소 충족 위해 저장만.

        Vote saved = voteRepository.save(
                Vote.builder()
                        .choice(req.choice())
                        .voterIp(voterIp)
                        .build()
        );

        return new VoteResponse(saved.getId(), saved.getChoice(), saved.getCreatedAt());
    }

    @Override
    @Transactional(readOnly = true)
    public VoteResultResponse getResult() {
        long jjajang = voteRepository.countByChoice(VoteChoice.JJAJANG);
        long jjambong = voteRepository.countByChoice(VoteChoice.JJAMBONG);
        return new VoteResultResponse(jjajang, jjambong);
    }
}
