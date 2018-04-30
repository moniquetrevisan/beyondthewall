package com.moniquetrevisan.basic.clienteservice.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moniquetrevisan.basic.clienteservice.model.Cliente;
import com.moniquetrevisan.basic.clienteservice.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private AssociacaoCampanhaService associacaoCampanhaService;

	private static Logger log = Logger.getLogger(ClienteService.class);
	
	public Cliente save(Cliente cliente) {
		Cliente clienteFounded = this.findClienteByEmail(cliente.getEmail());
		if(null != clienteFounded) {
			log.info("[Cliente-Service -- o email " + cliente.getEmail() + " já está cadastrado.");
			
			// procurar as campanhas associadas
			associacaoCampanhaService.associateExistente(clienteFounded);
		}
		
		final Cliente saved = repository.save(cliente);
		associacaoCampanhaService.associateNew(clienteFounded);
		
		return saved;
	}

	public Cliente findClienteByClienteId(Integer clienteId) {
		return repository.findClienteByClienteId(clienteId);
	}

	public Cliente findClienteByEmail(String email) {
		return repository.findClienteByEmail(email);
	}
	
}