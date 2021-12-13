package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Event;
import com.informatorio.tp_final.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping()
    public ArrayList<Event> getEvents(){
        return eventService.getEvents();
    }
}