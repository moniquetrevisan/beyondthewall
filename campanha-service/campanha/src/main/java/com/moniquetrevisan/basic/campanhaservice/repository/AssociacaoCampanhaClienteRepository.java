package com.moniquetrevisan.basic.campanhaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.campanhaservice.model.AssociacaoCampanhaCliente;

@Repository
public interface AssociacaoCampanhaClienteRepository extends JpaRepository<AssociacaoCampanhaCliente, Integer> {

}
