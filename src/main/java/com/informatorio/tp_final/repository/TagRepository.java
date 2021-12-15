package com.informatorio.tp_final.repository;
import com.informatorio.tp_final.entity.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

    ArrayList<Tag> findByNameContaining(String name);
}
