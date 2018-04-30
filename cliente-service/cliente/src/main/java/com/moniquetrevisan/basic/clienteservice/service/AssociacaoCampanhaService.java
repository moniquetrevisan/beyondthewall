package com.moniquetrevisan.basic.clienteservice.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.moniquetrevisan.basic.clienteservice.model.AssociacaoCampanhaCliente;
import com.moniquetrevisan.basic.clienteservice.model.Campanha;
import com.moniquetrevisan.basic.clienteservice.model.Cliente;
import com.moniquetrevisan.basic.clienteservice.repository.ClienteRepository;
import com.moniquetrevisan.basic.clienteservice.service.external.CampanhaServiceExternal;

@Service
public class AssociacaoCampanhaService {

	private static Logger log = Logger.getLogger(AssociacaoCampanhaService.class);

	@Autowired
	@Qualifier("com.moniquetrevisan.basic.clienteservice.service.external.CampanhaServiceExternal.CampanhaClientFallback")
	private CampanhaServiceExternal campanhaServiceExternal;

	@Autowired
	private ClienteRepository repository;
	
	public void associateExistente(Cliente cliente) {
		List<Campanha> campanhasNaoAssociadas = campanhaServiceExternal.consultarCampanhasNaoAssociadas(cliente.getClienteId(), cliente.getTimeCoracao().getTimeCoracaoId());
		if(campanhasNaoAssociadas == null || campanhasNaoAssociadas.isEmpty()) {
			log.warn("Cliente " + cliente + " não possui campanhas não associadas");
			return;
		}
		
		campanhasNaoAssociadas.stream().forEach(campanha -> campanhaServiceExternal.associateNew(new AssociacaoCampanhaCliente(campanha, cliente)));
		
		repository.save(cliente);
	}

	public void associateNew(Cliente cliente) {
		List<Campanha> campanhasToAssociate = campanhaServiceExternal.findAllCampanhasByTimeCoracaoId(cliente.getTimeCoracao().getTimeCoracaoId());

		if(campanhasToAssociate == null || campanhasToAssociate.isEmpty()) {
			log.warn("Time do coração " + cliente.getTimeCoracao() + " não possui campanhas cadastradas");
			return;
		}
		
		campanhasToAssociate.stream().forEach(campanha -> campanhaServiceExternal.associateNew(new AssociacaoCampanhaCliente(campanha, cliente)));
	}

}