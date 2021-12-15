package com.informatorio.tp_final.repository;
import com.informatorio.tp_final.entity.User;
import com.informatorio.tp_final.entity.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    ArrayList<User> findByCity(String city);

    @Query(value = "SELECT * " +
            "FROM user " +
            "WHERE creation_date > ?1", nativeQuery = true)
    ArrayList<User> findByCreationDate(LocalDate date);
}