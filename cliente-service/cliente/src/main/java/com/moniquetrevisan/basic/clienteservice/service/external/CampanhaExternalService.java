package com.moniquetrevisan.basic.clienteservice.service.external;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.moniquetrevisan.basic.campanhaservice.model.Campanha;
import com.moniquetrevisan.basic.clienteservice.service.external.CampanhaExternalService.CampanhaClientFallback;

@FeignClient(name = "campanha-service", fallback = CampanhaClientFallback.class)
public class CampanhaExternalService {
	
	@Component
	static class CampanhaClientFallback implements CampanhaExternalService {
		
		private static Logger log = Logger.getLogger(CampanhaClientFallback.class);

		@Override
		public List<Campanha> getCampanhasTimeCoracao(Long idTimeCoracao) {
			log.warn("Fallback para campanha-service: Consulta de campanhas do time do coração indisponível");
			return null;
		}

		@Override
		public List<Campanha> getCampanhasNaoAssociadas(Long clienteId, Long idTimeCoracao) {
			log.warn("Fallback para campanha-service: Consulta de campanhas não associadas indisponível");
			return null;
		}

		@Override
		public AssociacaoClienteCampanha associarClienteCampanha(AssociacaoClienteCampanha associacao) {
			log.warn("Fallback para campanha-service: Associação de campanhas indisponível");
			return null;
		}
		
	}

}
