package com.moniquetrevisan.basic.campanhaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.campanhaservice.model.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Integer> {

}
