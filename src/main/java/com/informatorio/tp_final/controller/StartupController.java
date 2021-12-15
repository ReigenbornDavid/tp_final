package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.service.StartupService;
import com.informatorio.tp_final.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/startup")
public class StartupController {
    @Autowired
    StartupService startupService;

    @GetMapping()
    public ResponseEntity<ArrayList<Startup>> getStartups(){
        var startups = startupService.getStartups();
        if(startups.size() > 0){
            return new ResponseEntity<>(startups, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/notPublished")
    public ResponseEntity<ArrayList<Startup>> getStartupNotPublished(){
        var startups = startupService.getStartupsNotPublished();
        if(startups.size() > 0){
            return new ResponseEntity<>(startups, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/", params = "tagName")
    public ResponseEntity<ArrayList<Startup>> getByTagName(@RequestParam String tagName){
        var startups = startupService.getByTagName(tagName);
        if(startups.size() > 0){
            return new ResponseEntity<>(startups, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveStartup(@Valid @RequestBody Startup startup, BindingResult result){
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
            Startup startupCreated = startupService.saveStartup(startup);
            if( startupCreated != null){
                responseHasMap.put("Resultado", "El Emprendimiento se ha creado exitosamente");
                responseHasMap.put("Emprendimiento Creado", startupCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo crear el Emprendimiento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo crear el Emprendimiento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping( value = "/", params = "id")
    public ResponseEntity<?> deleteById(@RequestParam Long id){
        boolean ok = startupService.deleteById(id);
        if (ok){
            return new ResponseEntity<>("Se eliminó el emprendimiento con id " + id, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No pudo eliminar el emprendimiento con id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{startupId}/user/{userId}")
    public ResponseEntity<Map<String, Object>> addStartupToUser(
            @PathVariable Long userId,
            @PathVariable Long startupId
    ){
        Map<String, Object> responseHasMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        try {
            Startup startup = startupService.addUserToStartup(userId, startupId);
            if( startup != null){
                responseHasMap.put("Resultado", "El Emprendimiento se ha agregado al usuario exitosamente");
                responseHasMap.put("Emprendimiento Agregado a Usuaio", startup);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo agregar el Emprendimiento al usuario");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo agregar el Emprendimiento al Usuario: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping(value = "/", params = "id")
    public ResponseEntity<Map<String, Object>> updateById(@RequestParam Long id, @Valid @RequestBody Startup startup, BindingResult result){
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
            Startup startupCreated = startupService.updateById(id, startup);
            if( startupCreated != null){
                responseHasMap.put("Resultado", "El Emprendimiento se ha actualizado exitosamente");
                responseHasMap.put("Emprendimiento Actualizado", startupCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo actualizar el Emprendimiento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo actualizar el Emprendimiento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}