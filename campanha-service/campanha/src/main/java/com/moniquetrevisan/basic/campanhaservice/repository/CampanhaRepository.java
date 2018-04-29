package com.moniquetrevisan.basic.campanhaservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.campanhaservice.model.Campanha;

@Repository
public interface CampanhaRepository extends CrudRepository<Campanha, Integer> {

}
