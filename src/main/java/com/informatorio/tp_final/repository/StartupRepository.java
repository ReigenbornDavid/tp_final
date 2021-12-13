package com.informatorio.tp_final.repository;
import com.informatorio.tp_final.entity.Startup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartupRepository extends CrudRepository<Startup, Long> {

}