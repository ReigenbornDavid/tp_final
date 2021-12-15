package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.repository.StartupRepository;
import com.informatorio.tp_final.repository.UserRepository;
import com.informatorio.tp_final.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Autowired
    StartupRepository startupRepository;

    @Autowired
    UserRepository userRepository;

    public ArrayList<Vote> getVotes(){
        return (ArrayList<Vote>) voteRepository.findAll();
    }

    public Vote saveVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public Vote addVoteToStartupByUser(Long voteId, Long startupId, Long userId) {
        Startup startup = startupRepository.findById(startupId).get();
        User user = userRepository.findById(userId).get();
        Vote vote = voteRepository.findById(voteId).get();
        vote.setUser(user);
        vote.setStartup(startup);
        return voteRepository.save(vote);
    }
}