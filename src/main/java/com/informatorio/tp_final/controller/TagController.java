package com.informatorio.tp_final.controller;
import com.informatorio.tp_final.entity.Tag;
import com.informatorio.tp_final.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping()
    public ArrayList<Tag> getTags(){
        return tagService.getTags();
    }
}