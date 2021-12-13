package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.repository.StartupRepository;
import com.informatorio.tp_final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}