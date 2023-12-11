package com.oskarwiedeweg.cloudwork.health;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthRepository extends CrudRepository<HealthEntity, Long> {

    List<HealthEntity> findAll();

}
