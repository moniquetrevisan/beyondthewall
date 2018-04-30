package com.moniquetrevisan.basic.clienteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.clienteservice.model.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Integer> {

}