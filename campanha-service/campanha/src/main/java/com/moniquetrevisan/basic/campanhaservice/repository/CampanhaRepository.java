package com.moniquetrevisan.basic.campanhaservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.campanhaservice.model.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Integer> {

	/* 
	 * StatusCampanha
	 *   1 - Ativa
	 *   2 - Pendente
	 *   3 - Expirada
	 */

	@Query("select campanha "
			+ " from Campanha campanha "
			+ " where campanha.statusCampanha <> 3 "
			+ "   and campanha.campanhaId = :campanhaId ")
	Campanha findCampanhaByCampanhaId(@Param("campanhaId") Integer campanhaId);

	@Query("select campanha "
		+ " from Campanha campanha, AssociacaoClienteCampanha assoc "
		+ " where campanha.campanhaId = assoc.campanhaId "
		+ "   and campanha.statusCampanha <> 3 "
		+ "   and assoc.clienteId = :clienteId ")
	List<Campanha> findCampanhaByClienteId(@Param("clienteId") Integer clienteId);

	@Query("select campanha "
		+ " from Campanha campanha "
		+ " where campanha.campanhaId <> :campanhaId "
		+ "   and campanha.timeCoracaoId = :timeCoracaoId "
		+ "   and campanha.statusCampanha <> 3 "
		+ "   and campanha.dataVencimento >= :dataInicio "
		+ "   and campanha.dataVencimento <= :dataVencimento "
		+ " orderby campanha.dataVencimento asc ")
	List<Campanha> findOverlapCampanhas(@Param("campanhaId") Integer campanhaId, @Param("timeCoracaoId") Integer timeCoracaoId, @Param("dataInicio") Date dataInicio, @Param("dataVencimento") Date dataVencimento);

	@Query("select campanha "
			+ " from Campanha campanha "
			+ " where campanha.statusCampanha <> 3 "
			+ "   and campanha.timeCoracaoId = :timeCoracaoId ")
	List<Campanha> findCampanhaByTimeCoracaoId(@Param("timeCoracaoId") Integer timeCoracaoId);

}