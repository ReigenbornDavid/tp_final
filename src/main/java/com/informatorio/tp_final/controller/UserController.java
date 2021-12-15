package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<ArrayList<User>> getUsers(){
        var users = userService.getUsers();
        if(users.size() > 0){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/", params = "id")
    public ResponseEntity<User> getById(@RequestParam Long id) {
        var user = userService.getById(id);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/votes", params = "id")
    public ResponseEntity<ArrayList<Vote>> getVotesById(@RequestParam Long id) {
        var votes = userService.getVotesById(id);
        if(votes.size() > 0){
            return new ResponseEntity<>(votes, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/", params = "city")
    public ResponseEntity<ArrayList<User>> getByCity(@RequestParam String city) {
        var users = userService.getByCity(city);
        if(users.size() > 0){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/", params = "date")
    public ResponseEntity<ArrayList<User>> getByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        var users = userService.getByDate(date);
        if(users.size() > 0){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveUser(@Valid @RequestBody User user, BindingResult result){

        Map<String, Object> responseHasMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        ArrayList<String> errors = null;
        if(result.hasErrors()){
            errors = new ArrayList<>();
            for(ObjectError error: result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            responseHasMap.put("Errores", errors);
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            User userCreated = userService.saveUser(user);
            if( userCreated != null){
                responseHasMap.put("Resultado", "El Usuario se ha creado exitosamente");
                responseHasMap.put("Usuario Creado", userCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo crear el Usuario");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo crear el Usuario: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping( value = "/", params = "id")
    public ResponseEntity<?> deleteById(@RequestParam Long id){
        boolean ok = userService.deleteById(id);
        if (ok){
            return new ResponseEntity<>("Se eliminó el usuario con id " + id, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No pudo eliminar el usuario con id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/", params = "id")
    public ResponseEntity<Map<String, Object>> updateById(@RequestParam Long id, @Valid @RequestBody User user, BindingResult result){
        Map<String, Object> responseHasMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        ArrayList<String> errors = null;
        if(result.hasErrors()){
            errors = new ArrayList<>();
            for(ObjectError error: result.getAllErrors()){
                errors.add(error.getDefaultMessage());
            }
            responseHasMap.put("Errores", errors);
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            User userCreated = userService.updateById(id, user);
            if( userCreated != null){
                responseHasMap.put("Resultado", "El Usuario se ha actualizado exitosamente");
                responseHasMap.put("Usuario Creado", userCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo actualizar el Usuario");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo actualizar el Usuario: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
