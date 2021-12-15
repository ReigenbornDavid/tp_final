package com.informatorio.tp_final.service;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.Tag;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.repository.StartupRepository;
import com.informatorio.tp_final.repository.TagRepository;
import com.informatorio.tp_final.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    @Autowired
    StartupRepository startupRepository;

    public ArrayList<Tag> getTags(){
        return (ArrayList<Tag>) tagRepository.findAll();
    }

    public Tag saveTag(Tag tag){
        return tagRepository.save(tag);
    }

    public Tag getById(Long id){
        Tag tag =  null;
        if(tagRepository.existsById(id)){
            tag = tagRepository.findById(id).get();
        }
        return  tag;
    }

    public boolean deleteById(Long id) {
        try{
            tagRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }

    public Tag updateById(Long id, Tag tag) {
        Tag tagResult =  null;
        if(tagRepository.existsById(id)){
            tag.setId(id);
            tagResult = tagRepository.save(tag);
        }
        return tagResult;
    }

    public Tag addTagToStartup(Long tagId, Long startupId) {
        Tag tag = null;
        if (tagRepository.existsById(tagId) && startupRepository.existsById(startupId)){
            Startup startup = startupRepository.findById(startupId).get();
            tag = tagRepository.findById(tagId).get();
            tag.setStartup(startup);
            tag = tagRepository.save(tag);
        }
        return tag;
    }
}