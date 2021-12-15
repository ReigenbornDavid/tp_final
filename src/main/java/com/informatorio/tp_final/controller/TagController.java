package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.Tag;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.service.TagService;
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

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping()
    public ResponseEntity<ArrayList<Tag>> getTags(){
        var tags = tagService.getTags();
        if(tags.size() > 0){
            return new ResponseEntity<>(tags, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping( value = "/", params = "id")
    public ResponseEntity<Tag> getById(@RequestParam Long id) {
        var tag = tagService.getById(id);
        if(tag != null){
            return new ResponseEntity<>(tag, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> saveTag(@Valid @RequestBody Tag tag, BindingResult result){
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
            Tag tagCreated = tagService.saveTag(tag);
            if( tagCreated != null){
                responseHasMap.put("Resultado", "El Tag se ha creado exitosamente");
                responseHasMap.put("Tag Creado", tagCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo crear el Tag");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo crear el Tag: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PutMapping("/{tagId}/startup/{startupId}")
    public ResponseEntity<Map<String, Object>> addStartupToUser(
            @PathVariable Long tagId,
            @PathVariable Long startupId
    ){
        Map<String, Object> responseHasMap = new HashMap<String, Object>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        try {
            Tag tag = tagService.addTagToStartup(tagId, startupId);
            if( tag != null){
                responseHasMap.put("Resultado", "El Tag se ha agregado al Emprendimiento exitosamente");
                responseHasMap.put("Tag Agregado a Emprendimiento", tag);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo agregar el Tag al Emprendimiento");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo agregar el Tag al Emprendimiento: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping( value = "/", params = "id")
    public ResponseEntity<?> deleteById(@RequestParam Long id){
        boolean ok = tagService.deleteById(id);
        if (ok){
            return new ResponseEntity<>("Se eliminó el tag con id " + id, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("No pudo eliminar el tag con id " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/", params = "id")
    public ResponseEntity<Map<String, Object>> updateById(@RequestParam Long id, @Valid @RequestBody Tag tag, BindingResult result){
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
            Tag tagCreated = tagService.updateById(id, tag);
            if( tagCreated != null){
                responseHasMap.put("Resultado", "El Tag se ha actualizado exitosamente");
                responseHasMap.put("Tag Actualizado", tagCreated);
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.OK);
            }else{
                responseHasMap.put("Resultado", "No se pudo actualizar el Tag");
                responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (DataAccessException e){
            responseHasMap.put("Resultado", "No se pudo actualizar el Tag: "+e.getMostSpecificCause());
            responseEntity = new ResponseEntity<>(responseHasMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}