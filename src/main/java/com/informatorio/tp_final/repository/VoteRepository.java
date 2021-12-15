package com.informatorio.tp_final.repository;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {

    ArrayList<Vote> findByUser(User user);
}