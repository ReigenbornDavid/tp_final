package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Event;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import com.informatorio.tp_final.repository.EventRepository;
import com.informatorio.tp_final.repository.StartupRepository;
import com.informatorio.tp_final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    StartupRepository startupRepository;

    public ArrayList<Event> getEvents(){
        return (ArrayList<Event>) eventRepository.findAll();
    }

    public ArrayList<Startup> getStartupsById(Long id) {
        ArrayList<Startup> startups = new ArrayList<Startup>();
        if (eventRepository.existsById(id)){
            Event event = eventRepository.findById(id).get();
            startups = new ArrayList<Startup>(event.getStartups());
            if (startups.size() > 1){
                startups.sort(
                        (Startup s1,Startup s2) -> (Integer.compare(s2.getVotes().size(), s1.getVotes().size())));
            }
        }
        return  startups;
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event addStartupToEvent(Long eventId, Long startupId) {
        Event event = null;
        if (eventRepository.existsById(eventId) && startupRepository.existsById(startupId)){
            Startup startup = startupRepository.findById(startupId).get();
            event = eventRepository.findById(eventId).get();
            if (!event.getStartups().contains(startup)){
                event.getStartups().add(startup);
                event = eventRepository.save(event);
            }else{
                event = null;
            }
        }
        return event;
    }

    public Event getById(Long id){
        Event event =  null;
        if(eventRepository.existsById(id)){
            event = eventRepository.findById(id).get();
        }
        return  event;
    }

    public boolean deleteById(Long id) {
        try{
            eventRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public Event updateById(Long id, Event event) {
        Event eventResult =  null;
        if(eventRepository.existsById(id)){
            event.setId(id);
            eventResult = eventRepository.save(event);
        }
        return eventResult;
    }
}