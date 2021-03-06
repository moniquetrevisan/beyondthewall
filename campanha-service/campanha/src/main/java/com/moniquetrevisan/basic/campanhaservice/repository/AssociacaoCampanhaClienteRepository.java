package com.moniquetrevisan.basic.campanhaservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.campanhaservice.model.AssociacaoCampanhaCliente;
import com.moniquetrevisan.basic.campanhaservice.model.Campanha;

@Repository
public interface AssociacaoCampanhaClienteRepository extends JpaRepository<AssociacaoCampanhaCliente, Integer> {

	@Query("select campanha from Campanha campanha, AssociacaoClienteCampanha assoc where campanha.campanhaId = assoc.campanhaId and campanha.statusCampanha <> 3 and assoc.clienteId = :clienteId")
	List<Campanha> findAllCampanhasAssociadasByCliente(@Param("clienteId") Integer clienteId);

}