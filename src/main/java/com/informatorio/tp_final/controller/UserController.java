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
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    StartupService startupService;

    @GetMapping()
    public ArrayList<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping()
    public User saveUser(@RequestBody User user){
        return this.userService.saveUser(user);
    }

    @GetMapping( path = "/{id}")
    public User getById(@PathVariable("id") Long id) {
        return this.userService.getById(id);
    }

    @DeleteMapping( path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.userService.deleteById(id);
        if (ok){
            return "Se eliminó el usuario con id " + id;
        }else{
            return "No pudo eliminar el usuario con id" + id;
        }
    }
}
