package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;

    public ArrayList<Vote> getVotes(){
        return (ArrayList<Vote>) voteRepository.findAll();
    }
}