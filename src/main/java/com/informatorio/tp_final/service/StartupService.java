package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.*;
import com.informatorio.tp_final.repository.StartupRepository;
import com.informatorio.tp_final.repository.TagRepository;
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

    @Autowired
    TagRepository tagRepository;

    public ArrayList<Startup> getStartups(){
        return (ArrayList<Startup>) startupRepository.findAll();
    }

    public ArrayList<Startup> getStartupsNotPublished(){
        return (ArrayList<Startup>) startupRepository.getStartupsNotPublished();
    }

    public Startup getById(Long id){
        Startup startup =  null;
        if(startupRepository.existsById(id)){
            startup = startupRepository.findById(id).get();
        }
        return  startup;
    }

    public Startup saveStartup(Startup startup){
        return startupRepository.save(startup);
    }

    public Startup addUserToStartup(Long userId, Long startupId) {
        Startup startup = null;
        if (userRepository.existsById(userId) && startupRepository.existsById(startupId)){
            startup = startupRepository.findById(startupId).get();
            User user = userRepository.findById(userId).get();
            startup.setUser(user);
            startup = startupRepository.save(startup);
        }
        return startup;
    }

    public boolean deleteById(Long id) {
        try{
            startupRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public ArrayList<Tag> getTagByName(String name){
        ArrayList<Tag> tags = new ArrayList<Tag>();
        tags = tagRepository.findByNameContaining(name);
        return tags;
    }

    public ArrayList<Startup> getByTagName(String tagName) {
        ArrayList<Startup> startups = new ArrayList<Startup>();
        for(Tag tag:getTagByName(tagName)){
            startups.add(tag.getStartup());
        }
        return startups;
    }

    public Startup updateById(Long id, Startup startup) {
        Startup startupResult =  null;
        if(startupRepository.existsById(id)){
            startup.setId(id);
            startupResult = startupRepository.save(startup);
        }
        return startupResult;
    }
}