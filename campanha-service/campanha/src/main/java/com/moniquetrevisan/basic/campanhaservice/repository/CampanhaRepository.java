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

	@Query("select campanha "
			+ " from Campanha campanha "
			+ " where campanha.statusCampanha <> :status "
			+ "   and campanha.campanhaId = :campanhaId ")
	Campanha findCampanhaByCampanhaId(@Param("campanhaId") Integer campanhaId, @Param("status") Integer status);

	@Query("select campanha "
		+ " from Campanha campanha, AssociacaoClienteCampanha assoc "
		+ " where campanha.campanhaId = assoc.campanhaId "
		+ "   and campanha.statusCampanha <> :status "
		+ "   and assoc.clienteId = :clienteId ")
	List<Campanha> findCampanhaByClienteId(@Param("clienteId") Integer clienteId, @Param("status") Integer status);

	@Query("select campanha "
		+ " from Campanha campanha "
		+ " where campanha.campanhaId <> :campanhaId "
		+ "   and campanha.timeCoracaoId = :timeCoracaoId "
		+ "   and campanha.statusCampanha <> :status "
		+ "   and campanha.dataVencimento >= :dataInicio "
		+ "   and campanha.dataVencimento <= :dataVencimento "
		+ " orderby campanha.dataVencimento asc ")
	List<Campanha> findOverlapCampanhas(@Param("campanhaId") Integer campanhaId, @Param("timeCoracaoId") Integer timeCoracaoId, @Param("dataInicio") Date dataInicio, @Param("dataVencimento") Date dataVencimento, @Param("status") Integer status);

	@Query("select campanha "
			+ " from Campanha campanha "
			+ " where campanha.statusCampanha <> :status "
			+ "   and campanha.timeCoracaoId = :timeCoracaoId ")
	List<Campanha> findCampanhaByTimeCoracaoId(@Param("timeCoracaoId") Integer timeCoracaoId, @Param("status") Integer status);

	@Query("select campanha "
			+ " from Campanha campanha "
			+ " where campanha.statusCampanha <> :status "
			+ "   and campanha.timeCoracaoId = :timeCoracaoId "
			+ "   and campanha.campanhaId not in :timeCoracaoId ")
	List<Campanha> findCampanhaNotAssociate(@Param("timeCoracaoId") Integer timeCoracaoId, @Param("status") Integer status, @Param("campanhaIds")List<Integer> campanhaIds);

}