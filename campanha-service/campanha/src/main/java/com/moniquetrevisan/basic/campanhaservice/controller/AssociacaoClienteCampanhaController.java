package com.moniquetrevisan.basic.campanhaservice.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moniquetrevisan.basic.campanhaservice.model.AssociacaoCampanhaCliente;
import com.moniquetrevisan.basic.campanhaservice.model.Campanha;
import com.moniquetrevisan.basic.campanhaservice.service.AssociacaoClienteCampanhaService;

@RestController
@RequestMapping("/associacaoCampanha")
public class AssociacaoClienteCampanhaController {

	private static Logger log = Logger.getLogger(AssociacaoClienteCampanhaController.class);

	@Autowired
	private AssociacaoClienteCampanhaService associacaoService;

	@RequestMapping(path="/{clienteId}", method = RequestMethod.GET)
	public ResponseEntity<List<Campanha>> findAllCampanhasAssociadasByCliente(@PathVariable("clienteId") Integer clienteId){
		ResponseEntity<List<Campanha>> response = null;
		try {
			List<Campanha> associateCampanhas = associacaoService.findAllCampanhasAssociadasByCliente(clienteId);
			response = new ResponseEntity<List<Campanha>>(associateCampanhas, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response =  new ResponseEntity<List<Campanha>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Campanha>> consultarCampanhasNaoAssociadas(@RequestParam("clienteId") Integer clienteId, @RequestParam("timeCoracaoId") Integer timeCoracaoId){
		ResponseEntity<List<Campanha>> response = null;
		try {
			List<Campanha> campanhasNaoAssociadas = associacaoService.consultarCampanhasNaoAssociadas(clienteId, timeCoracaoId);
			response = new ResponseEntity<List<Campanha>>(campanhasNaoAssociadas,HttpStatus.OK);
			log.info(String.format("Consulta de campanhas não associadas realizada com sucesso: " + campanhasNaoAssociadas));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response =  new ResponseEntity<List<Campanha>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<AssociacaoCampanhaCliente> associateNew(@RequestBody AssociacaoCampanhaCliente associacao){
		ResponseEntity<AssociacaoCampanhaCliente> response = null;
		try {
			AssociacaoCampanhaCliente saved = associacaoService.create(associacao);
			response = new ResponseEntity<AssociacaoCampanhaCliente>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<AssociacaoCampanhaCliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}