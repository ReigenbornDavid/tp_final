package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    VoteService voteService;

    @GetMapping()
    public ArrayList<Vote> getVotes(){
        return voteService.getVotes();
    }
}