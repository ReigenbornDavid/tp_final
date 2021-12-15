package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Event;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.service.EventService;
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
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping()
    public ResponseEntity<ArrayList<Event>> getEvents(){
        var events = eventService.getEvents();
        if(events.size() > 0){
            return new ResponseEntity<>(events, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/", params = "id")
    public ResponseEntity<Event> getById(@RequestParam Long id) {
        var event = eventService.getById(id);
        if(event != null){
            return new ResponseEntity<>(event, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/startups", params = "id")
    public ResponseEntity<ArrayList<Startup>> getStartupsById(@RequestParam Long id){
        var startups = eventService.getStartupsById(id);
        if(startups.size() > 0){
            return new ResponseEntity<>(startups, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveEvent(@Valid @RequestBody Event event, BindingResult result){
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
            Event eventCreated = eventService.saveEvent(event);
            if( eventCreated != null){
                responseHasMap.put("Resultado", "El Evento se ha creado exitosamente");
                responseHasMap.put("Evento Creado", eventCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo crear el Evento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo crear el Evento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/{eventId}/startup/{startupId}")
    public ResponseEntity<Map<String, Object>> addStartupToEvent(
            @PathVariable Long eventId,
            @PathVariable Long startupId
    ){
        Map<String, Object> responseHasMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        try {
            Event event = eventService.addStartupToEvent(eventId, startupId);
            if( event != null){
                responseHasMap.put("Resultado", "El Emprendimiento se ha agregado al Evento exitosamente");
                responseHasMap.put("Emprendimiento Agregado a Evento", event);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo agregar el Emprendimiento al Evento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo agregar el Emprendimiento al Evento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping( value = "/", params = "id")
    public ResponseEntity<?> deleteById(@RequestParam Long id){
        boolean ok = eventService.deleteById(id);
        if (ok){
            return new ResponseEntity<>("Se eliminó el evento con id " + id, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No pudo eliminar el evento con id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/", params = "id")
    public ResponseEntity<Map<String, Object>> updateById(@RequestParam Long id, @Valid @RequestBody Event event, BindingResult result){
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
            Event eventCreated = eventService.updateById(id, event);
            if( eventCreated != null){
                responseHasMap.put("Resultado", "El Evento se ha actualizado exitosamente");
                responseHasMap.put("Evento Actualizado", eventCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo actualizar el Evento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo actualizar el Evento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}