package com.example.demo.service;

import com.example.demo.dto.VoteRequest;
import com.example.demo.dto.VoteResponse;
import com.example.demo.dto.VoteResultResponse;
import com.example.demo.entity.Vote;
import com.example.demo.entity.VoteChoice;
import com.example.demo.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;


    @Override
    @Transactional
    public VoteResponse vote(VoteRequest req, String voterIp) {

        // 1) 입력 검증(선택: 이미 validation 쓰면 생략 가능)
        if (voterIp == null || voterIp.isBlank()) {
            throw new IllegalArgumentException("voterIp is required");
        }

        // 2) (어뷰징 방지) 같은 IP 1회 제한
        if (voteRepository.existsByVoterIp(voterIp)) {
            throw new IllegalStateException("이미 투표한 IP입니다.");
        }

        try {
            Vote saved = voteRepository.save(
                    Vote.builder()
                            .choice(req.choice())
                            .voterIp(voterIp)
                            .build()
            );

            return new VoteResponse(saved.getId(), saved.getChoice(), saved.getCreatedAt());

        } catch (DataIntegrityViolationException e) {
            // 3) (강추) 동시성 상황에서 exists 체크를 뚫고 2번 저장되는 걸 DB 유니크로 최종 방어
            throw new IllegalStateException("이미 투표한 IP입니다.");
        }
    }


    @Override
    @Transactional(readOnly = true)
    public VoteResultResponse getResult() {
        long jjajang = voteRepository.countByChoice(VoteChoice.JJAJANG);
        long jjambong = voteRepository.countByChoice(VoteChoice.JJAMBONG);
        return new VoteResultResponse(jjajang, jjambong);
    }
}
