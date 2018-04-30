package com.moniquetrevisan.basic.campanhaservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moniquetrevisan.basic.campanhaservice.exception.NotFoundException;
import com.moniquetrevisan.basic.campanhaservice.model.Campanha;
import com.moniquetrevisan.basic.campanhaservice.repository.AssociacaoCampanhaClienteRepository;
import com.moniquetrevisan.basic.campanhaservice.repository.CampanhaRepository;
import com.moniquetrevisan.basic.campanhaservice.util.DateUtil;

@Service
public class CampanhaService {

	@Autowired
	private CampanhaRepository repository;

	@Autowired
	private AssociacaoCampanhaClienteRepository associacaoRepository;

	public Campanha findById(Integer campanhaId) throws NotFoundException {
		boolean notFoundAlerta = Boolean.FALSE;
		String errorMessage = "";

		Campanha campanha = repository.findOne(campanhaId);
		if (null == campanha) {
			notFoundAlerta = Boolean.TRUE;
			errorMessage = "Não foi encontrada nenhuma campanha para este id";
		} else if (campanha.getStatusCampanha() == 3) {
			notFoundAlerta = Boolean.TRUE;
			errorMessage = "Esta campanha esta expirada e portanto não sera retornada.";
		}

		if (notFoundAlerta && !errorMessage.isEmpty()) {
			throw new NotFoundException(errorMessage);
		}

		return repository.findOne(campanhaId);
	}

	public Iterable<Campanha> findAllCampanhasByCliente(Integer clienteId) {
		List<Campanha> campanhasCliente = associacaoRepository.findCampanhaByClienteId(clienteId);
		return campanhasCliente;
	}

	/*public Campanha create(String nome, Integer timeCoracaoId, String dataVigencia) {
		String[] dataVigenciaArr = dataVigencia.split(";");
		Date dataInicio = DateUtil.getDateByString(dataVigenciaArr[0]);
		Date dataVencimento = DateUtil.getDateByString(dataVigenciaArr[0]);

		Campanha campanhaCadastrada = repository.save(new Campanha(nome, timeCoracaoId, dataInicio, dataVencimento));
		return campanhaCadastrada;
	}*/

	public void delete(Integer id) {
		repository.delete(id);
	}
	
	public Campanha create(Campanha campanha) {
		validateOverlapCampanha(campanha);

		Campanha campanhaCadastrada = repository.save(campanha);
		return campanhaCadastrada;
	}

	private void validateOverlapCampanha(Campanha campanha) {
		List<Campanha> overlappings = repository.findOverladDeCampanhas(campanha.getCampanhaId(), campanha.getTimeCoracao().getTimeCoracaoId(), campanha.getDataInicio(), campanha.getDataVencimento());

		if (overlappings != null && !overlappings.isEmpty()) {
			addDayInOverlappings(overlappings, campanha);

			Iterable<Campanha> updateOverlaps = overlappings;
			updateOverlaps = repository.save(updateOverlaps);
		}
	}

	/**
	 * 
	 * @param campanhasVigentes
	 * @param campanhaRef
	 */
	private void addDayInOverlappings(List<Campanha> campanhaOverlappings, Campanha campanhaRef) {
		if (campanhaOverlappings == null || campanhaOverlappings.isEmpty()) {
			return;
		}

		Campanha currentCampanha = campanhaOverlappings.get(0);
		if (currentCampanha.getDataVencimento().compareTo(campanhaRef.getDataVencimento()) <= 0) {
			currentCampanha.setDataVencimento(DateUtil.plusDay(campanhaRef.getDataVencimento()));
		}

		// call again recursively
		List<Campanha> rest = campanhaOverlappings.subList(1, campanhaOverlappings.size());
		addDayInOverlappings(rest, currentCampanha);
	}

}