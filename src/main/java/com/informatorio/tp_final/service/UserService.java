package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.repository.UserRepository;
import com.informatorio.tp_final.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    public ArrayList<User> getUsers(){
        return (ArrayList<User>) userRepository.findAll();
    }

    public ArrayList<Vote> getVotesById(Long id) {
        User user = userRepository.findById(id).get();
        var votes =  voteRepository.findByUser(user);
        return  votes;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getById(Long id){
        return userRepository.findById(id).get();
    }

    public boolean deleteById(Long id) {
        try{
            userRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public ArrayList<User> getByCity(String city) {
        return (ArrayList<User>) userRepository.findByCity(city);
    }


    public ArrayList<User> getByDate(LocalDate date) {
        return (ArrayList<User>) userRepository.findByCreationDate(date);
    }

    public User updateById(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
}