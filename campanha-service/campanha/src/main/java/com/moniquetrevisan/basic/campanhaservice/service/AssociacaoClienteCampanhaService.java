package com.moniquetrevisan.basic.campanhaservice.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moniquetrevisan.basic.campanhaservice.exception.AllAssociateException;
import com.moniquetrevisan.basic.campanhaservice.exception.NotFoundException;
import com.moniquetrevisan.basic.campanhaservice.model.AssociacaoCampanhaCliente;
import com.moniquetrevisan.basic.campanhaservice.model.Campanha;
import com.moniquetrevisan.basic.campanhaservice.repository.AssociacaoCampanhaClienteRepository;

@Service
public class AssociacaoClienteCampanhaService {

	private static Logger log = Logger.getLogger(AssociacaoClienteCampanhaService.class);

	@Autowired
	private AssociacaoCampanhaClienteRepository repository;

	@Autowired
	private CampanhaService campanhaService;

	/**
	 * Procura todas as campanhas ativas que estão associadas a este cliente
	 * @param clienteId - id do cliente a ser procurada as campanhas associadas
	 * @return List com todas as campanhas ativas que este cliente esta associado 
	 */
	public List<Campanha> findAllCampanhasAssociadasByCliente(Integer clienteId) throws NotFoundException{
		List<Campanha> campanhasAssoc = repository.findAllCampanhasAssociadasByCliente(clienteId);
		if(campanhasAssoc == null || campanhasAssoc.isEmpty()) {
			throw new NotFoundException(String.format("Campanhas não encontradas para o cliente %d", clienteId));
		}
		return campanhasAssoc;
	}

	public List<Campanha> consultarCampanhasNaoAssociadas(Integer clienteId, Integer timeCoracaoId) throws NotFoundException, AllAssociateException {
		List<Campanha> notAssociated = null;
		try {
			List<Campanha> campanhasAssociadas = this.findAllCampanhasAssociadasByCliente(clienteId);
			notAssociated = campanhaService.consultarCampanhasDoTimeDoCoracaoNaoAssociadas(timeCoracaoId, campanhasAssociadas);
		} catch (NotFoundException e) {
			log.warn(String.format("Cliente %d não possui campanhas associadas", clienteId));
			notAssociated = campanhaService.findAllCampanhasByTimeCoracao(timeCoracaoId);
		}
		return notAssociated;
	}

	public AssociacaoCampanhaCliente create(AssociacaoCampanhaCliente assoc) {
		return repository.save(assoc);
	}

}