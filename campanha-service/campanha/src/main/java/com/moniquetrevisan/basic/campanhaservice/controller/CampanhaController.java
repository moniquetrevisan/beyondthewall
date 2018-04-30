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
import org.springframework.web.bind.annotation.RestController;

import com.moniquetrevisan.basic.campanhaservice.exception.NotFoundException;
import com.moniquetrevisan.basic.campanhaservice.model.Campanha;
import com.moniquetrevisan.basic.campanhaservice.service.CampanhaService;

@RestController
@RequestMapping("/campanha")
public class CampanhaController {

	private static Logger log = Logger.getLogger(CampanhaController.class);

	@Autowired
	private CampanhaService service;

	@RequestMapping(path = "/findById/{campanhaId}", method = RequestMethod.GET)
	public ResponseEntity<Campanha> findById(@PathVariable("campanhaId") Integer campanhaId){
		ResponseEntity<Campanha> response = null;
		try {
			Campanha campanha = service.findCampanhaByCampanhaId(campanhaId);
			response = new ResponseEntity<Campanha>(campanha, HttpStatus.OK);
		} catch (NotFoundException e) {
			log.error(e.getMessage(), e);
			response =  new ResponseEntity<Campanha>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response =  new ResponseEntity<Campanha>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(path = "/delete/{campanhaId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable("campanhaId") Integer campanhaId){
		ResponseEntity<Void> response = null;
		try {
			service.delete(campanhaId);
			response = new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(path = "/findAllCampanhasByTimeCoracaoId/{timeCoracaoId}", method = RequestMethod.GET)
	public ResponseEntity<List<Campanha>> findAllCampanhasByTimeCoracaoId(@PathVariable("timeCoracaoId") Integer timeCoracaoId){
		ResponseEntity<List<Campanha>> response = null;
		try {
			List<Campanha> campanha = service.findAllCampanhasByTimeCoracao(timeCoracaoId);
			response = new ResponseEntity<List<Campanha>>(campanha, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response =  new ResponseEntity<List<Campanha>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Campanha> create(@RequestBody Campanha campanha){
		ResponseEntity<Campanha> response = null;
		try {
			Campanha saved = service.create(campanha);
			response = new ResponseEntity<Campanha>(saved, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Campanha>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Campanha> update(@RequestBody Campanha campanha){
		ResponseEntity<Campanha> response = null;
		try {
			Campanha updated = service.create(campanha);
			response = new ResponseEntity<Campanha>(updated,HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Campanha>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}