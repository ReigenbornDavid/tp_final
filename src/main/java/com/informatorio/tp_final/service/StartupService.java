package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.repository.StartupRepository;
import com.informatorio.tp_final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StartupService {
    @Autowired
    StartupRepository startupRepository;

    @Autowired
    UserRepository userRepository;

    public ArrayList<Startup> getStartups(){
        return (ArrayList<Startup>) startupRepository.findAll();
    }

    public ArrayList<Startup> getStartupsNotPublished(){
        return (ArrayList<Startup>) startupRepository.getStartupsNotPublished();
    }

    public Startup getById(Long id){
        return startupRepository.findById(id).get();
    }

    public Startup saveStartup(Startup startup){
        return startupRepository.save(startup);
    }

    public Startup addUserToStartup(Long userId, Long startupId) {
        Startup startup = startupRepository.findById(startupId).get();
        User user = userRepository.findById(userId).get();
        startup.setUser(user);
        return startupRepository.save(startup);
    }

    public boolean deleteById(Long id) {
        try{
            startupRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public ArrayList<Startup> getByTagName(String tagName) {
        var startups = startupRepository.findAll();
        for (Startup startup: startups ){
            //startup.getTags();
            System.out.println(startup.getTags());
        }

        return (ArrayList<Startup>) startupRepository.findByTagName(tagName);
    }

    public Startup updateById(Long id, Startup startup) {
        startup.setId(id);
        return startupRepository.save(startup);
    }
}