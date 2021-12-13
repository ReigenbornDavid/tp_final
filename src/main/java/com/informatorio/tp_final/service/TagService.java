package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Tag;
import com.informatorio.tp_final.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public ArrayList<Tag> getTags(){
        return (ArrayList<Tag>) tagRepository.findAll();
    }
}