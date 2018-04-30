package com.moniquetrevisan.basic.clienteservice.service.external;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.moniquetrevisan.basic.clienteservice.model.AssociacaoCampanhaCliente;
import com.moniquetrevisan.basic.clienteservice.model.Campanha;
import com.moniquetrevisan.basic.clienteservice.service.external.CampanhaServiceExternal.CampanhaClientFallback;

@FeignClient(name = "campanha-service", fallback = CampanhaClientFallback.class)
public interface CampanhaServiceExternal {

	@RequestMapping(method = RequestMethod.GET, value = "/campanha/findAllCampanhasByTimeCoracaoId")
	public List<Campanha> findAllCampanhasByTimeCoracaoId(@RequestParam("timeCoracaoId") Integer timeCoracaoId);

	@RequestMapping(method = RequestMethod.POST, value = "/associacaoCampanha")
	public AssociacaoCampanhaCliente associateNew(@RequestBody AssociacaoCampanhaCliente assoc);

	@RequestMapping(method = RequestMethod.POST, value = "/associacaoCampanha")
	public List<Campanha> consultarCampanhasNaoAssociadas(@RequestParam("clienteId") Integer clienteId, @RequestParam("timeCoracaoId") Integer timeCoracaoId);

	@Component
	static class CampanhaClientFallback implements CampanhaServiceExternal {
		
		private static Logger log = Logger.getLogger(CampanhaClientFallback.class);
		
		public List<Campanha> findAllCampanhasByTimeCoracaoId(@RequestParam("timeCoracaoId") Integer timeCoracaoId){
			log.warn("[Campanha-Service -- something is wrong] Consulta de campanhas do time do coração não está disponível neste momento. Entre em contato com os administradores do serviço.");
			return null;
		}

		public AssociacaoCampanhaCliente associateNew(@RequestBody AssociacaoCampanhaCliente assoc) {
			log.warn("Fallback para campanha-service: Associação de campanhas indisponível");
			return null;
		}

		public List<Campanha> consultarCampanhasNaoAssociadas(@RequestParam("clienteId") Integer clienteId, @RequestParam("timeCoracaoId") Integer timeCoracaoId){
			log.warn("[Campanha-Service -- something is wrong] Consulta de campanhas não associadas não está disponível neste momento. Entre em contato com os administradores do serviço.");
			return null;
		}
	}

}