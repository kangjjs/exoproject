package com.example.demo.repository;


import com.example.demo.entity.Vote;
import com.example.demo.entity.VoteChoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    long countByChoice(VoteChoice choice);

    boolean existsByVoterIp(String voterIp);
}
