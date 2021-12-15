package com.informatorio.tp_final.repository;
import com.informatorio.tp_final.entity.Event;
import com.informatorio.tp_final.entity.Startup;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface StartupRepository extends CrudRepository<Startup, Long> {

    @Query(value = "SELECT * FROM startup WHERE published = false", nativeQuery = true)
    ArrayList<Startup> getStartupsNotPublished();

    @Query(value = "Select distinct s.id, s.name, s.description, s.content, s.creation_date, s.income, s.published, s.url \n" +
            "from tp_final_db.tag t, tp_final_db.startup s\n" +
            "where t.startup_id = s.id and t.name Like :tagName%", nativeQuery = true)
    ArrayList<Startup> findByTagName(@Param("tagName") String tagName);
}