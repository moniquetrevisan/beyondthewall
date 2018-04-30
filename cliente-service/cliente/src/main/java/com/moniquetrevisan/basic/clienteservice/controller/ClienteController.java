package com.moniquetrevisan.basic.clienteservice.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.moniquetrevisan.basic.clienteservice.model.Cliente;
import com.moniquetrevisan.basic.clienteservice.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

private static Logger log = Logger.getLogger(ClienteController.class);

	@Autowired
	private ClienteService service;

	@RequestMapping(path = "/findClienteByClienteId/{clienteId}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> findClienteByClienteId(@PathVariable("clienteId") Integer clienteId){
		ResponseEntity<Cliente> response = null;
		try {
			Cliente cliente = service.findClienteByClienteId(clienteId);
			response = new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Cliente> create(@RequestBody Cliente cliente){
		ResponseEntity<Cliente> response = null;
		try {
			Cliente created = service.save(cliente);
			response = new ResponseEntity<Cliente>(created, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response = new ResponseEntity<Cliente>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}