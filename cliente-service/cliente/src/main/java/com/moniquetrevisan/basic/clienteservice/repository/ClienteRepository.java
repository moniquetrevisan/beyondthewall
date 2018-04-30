package com.moniquetrevisan.basic.clienteservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moniquetrevisan.basic.clienteservice.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Query("select cliente " 
		+ " from Cliente cliente " 
		+ " where cliente.statusCliente <> :status "
		+ "   and cliente.clienteId = :clienteId")
	Cliente findClienteByClienteId(@Param("clienteId") Integer clienteId, @Param("status") Integer status);

	@Query("select cliente " 
			+ " from Cliente cliente " 
			+ " where cliente.statusCliente <> :status "
			+ "   and cliente.email = :email")
	Cliente findClienteByEmail(@Param("email") String email, @Param("status") Integer status);

}