package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    VoteService voteService;

    @GetMapping()
    public ResponseEntity<ArrayList<Vote>> getVotes(){
        var votes = voteService.getVotes();
        if(votes.size() > 0){
            return new ResponseEntity<>(votes, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveVote(@Valid @RequestBody Vote vote, BindingResult result){

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
            Vote voteCreated = voteService.saveVote(vote);
            if( voteCreated != null){
                responseHasMap.put("Resultado", "El Voto se ha creado exitosamente");
                responseHasMap.put("Voto Creado", voteCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo crear el Voto");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo crear el Voto: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("{voteId}/startup/{startupId}/user/{userId}")
    public ResponseEntity<Map<String, Object>> addVoteToStartupByUser(
            @PathVariable Long userId,
            @PathVariable Long startupId,
            @PathVariable Long voteId
    ){
        Map<String, Object> responseHasMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        try {
            Vote vote = voteService.addVoteToStartupByUser(voteId, startupId, userId);
            if( vote != null){
                responseHasMap.put("Resultado", "El Voto se ha agregado al Emprendimiento exitosamente");
                responseHasMap.put("Voto Agregado al Emprendimiento", vote);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo agregar el voto al Emprendimiento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo agregar el Voto al Emprendimiento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}