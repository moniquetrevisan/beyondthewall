package com.moniquetrevisan.basic.clienteservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moniquetrevisan.basic.clienteservice.model.Cliente;
import com.moniquetrevisan.basic.clienteservice.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	public Cliente save(Cliente cliente) {
		findClienteByClienteId(cliente.get)
		return repository.save(cliente);
	}

	public Cliente findClienteByClienteId(Integer clienteId) {
		return repository.findClienteByClienteId(clienteId);
	}

	public Cliente findClienteByEmail(String email) {
		return repository.findClienteByEmail(email);
	}
	
}