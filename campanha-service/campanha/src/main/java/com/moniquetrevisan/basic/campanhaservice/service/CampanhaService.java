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

	/**
	 * Procura uma camapnha atraves de seu id
	 * @param campanhaId
	 * @return a camanha ativa correspondente a este id
	 * @throws NotFoundException - Ou nao existe referente ou a campanha esta expirada e por isso nao deve ser retornada
	 */
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

	/**
	 * Procura todas as campanhas ativas que estão associadas a este cliente
	 * @param clienteId - id do cliente a ser procurada as campanhas associadas
	 * @return List com todas as campanhas ativas que este cliente esta associado 
	 */
	public List<Campanha> findAllCampanhasAssociadasByCliente(Integer clienteId) {
		return associacaoRepository.findAllCampanhasAssociadasByCliente(clienteId);
	}
	
	/**
	 * Procura todas as campanhas ativas para determinado time do coracao
	 * @param timeCoracaoId - time a ser procurado
	 * @return lista de todas as campanhas ativas do time do coracao pesquisado
	 */
	public List<Campanha> findAllCampanhasByTimeCoracao(Integer timeCoracaoId) {
		return repository.findCampanhaByTimeCoracaoId(timeCoracaoId);
	}

	/**
	 * Exclui defitivamente uma campanha  
	 * @param campanhaId - id da campanha a ser removida
	 */
	public void delete(Integer campanhaId) {
		repository.delete(campanhaId);
	}
	
	/**
	 * Salva no banco de dados uma nova campanha
	 * @param campanha - campanha a ser gravada no banco de dados
	 * @return retorna a campanha que foi salva no banco de dados
	 */
	public Campanha create(Campanha campanha) {
		validateAndFixOverlaps(campanha);

		Campanha campanhaCadastrada = repository.save(campanha);
		return campanhaCadastrada;
	}

	/**
	 * Valida se existem campanhas relacionadas no intervalo de tempo da camapnha a ser cadastrada.
	 * Caso existam campanhas relacionadas, é acrescentado um dia ao final das campanhas relacionadas e assim sucessivamente ate que nao existam duas pesquisas do mesmo time com a mesma data de vencimento 
	 * @param campanha - campanha a ser validada
	 */
	private void validateAndFixOverlaps(Campanha campanha) {
		List<Campanha> overlappings = repository.findOverlapCampanhas(campanha.getCampanhaId(), campanha.getTimeCoracao().getTimeCoracaoId(), campanha.getDataInicio(), campanha.getDataVencimento());

		if (overlappings != null && !overlappings.isEmpty()) {
			fixOverlapsDataVencimento(campanha, overlappings);
		}
	}

	/**
	 * Executa a logica necessaria para que nao existam campanhas ativas do mesmo time com a mesma data de vencimento
	 * @param campanha - campanha nova com a referencia de periodo (Data de vencimento)
	 * @param overlappings - lista de campanhas relacionadas ao periodo da nova campanha
	 */
	private void fixOverlapsDataVencimento(Campanha campanha, List<Campanha> overlappings) {
		addDayInOverlappings(overlappings, campanha);

		Iterable<Campanha> updateOverlaps = overlappings;
		updateOverlaps = repository.save(updateOverlaps);
	}

	/**
	 * Trata recursivamente o cenario do efeito em cadeia da adiacao de dia na data de vencimento para que nao existam datas de vencimentos no mesmo dia para o mesmo time 
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