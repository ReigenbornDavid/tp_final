package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.service.StartupService;
import com.informatorio.tp_final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/startup")
public class StartupController {
    @Autowired
    StartupService startupService;

    @GetMapping()
    public ArrayList<Startup> getStartups(){
        var startups = startupService.getStartups();
        return startups;
    }

    @PostMapping()
    public Startup saveStartup(@RequestBody Startup startup){
        return startupService.saveStartup(startup);
    }

    @PutMapping("/{startupId}/user/{userId}")
    Startup addStartupToUser(
            @PathVariable Long userId,
            @PathVariable Long startupId
    ){
        return startupService.addUserToStartup(userId, startupId);
    }

}