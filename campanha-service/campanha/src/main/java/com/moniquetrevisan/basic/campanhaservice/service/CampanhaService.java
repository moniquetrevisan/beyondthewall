package com.moniquetrevisan.basic.campanhaservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moniquetrevisan.basic.campanhaservice.exception.AllAssociateException;
import com.moniquetrevisan.basic.campanhaservice.exception.NotFoundException;
import com.moniquetrevisan.basic.campanhaservice.exception.NotificacaoException;
import com.moniquetrevisan.basic.campanhaservice.model.Campanha;
import com.moniquetrevisan.basic.campanhaservice.repository.CampanhaRepository;
import com.moniquetrevisan.basic.campanhaservice.util.DateUtil;
import com.moniquetrevisan.basic.campanhaservice.util.StatusDefaults;

@Service
public class CampanhaService {

	@Autowired
	private CampanhaRepository repository;

	@Autowired
	private NotificadorCampanhaService notificadorCampanhaService;

	/**
	 * Procura uma camapnha atraves de seu id
	 * @param campanhaId
	 * @return a campanha ativa correspondente a este id
	 * @throws NotFoundException - Ou nao existe referente ou a campanha esta expirada e por isso nao deve ser retornada
	 */
	public Campanha findCampanhaByCampanhaId(Integer campanhaId) throws NotFoundException {
		boolean notFoundAlerta = Boolean.FALSE;
		String errorMessage = "";

		Campanha campanha = repository.findCampanhaByCampanhaId(campanhaId, StatusDefaults.CAMPANHA_EXPIRADA);
		if (null == campanha) {
			notFoundAlerta = Boolean.TRUE;
			errorMessage = "Não foi encontrada nenhuma campanha para este id";
		} else if (campanha.getStatusCampanha() == StatusDefaults.CAMPANHA_EXPIRADA) {
			notFoundAlerta = Boolean.TRUE;
			errorMessage = "Esta campanha esta expirada e portanto não sera retornada.";
		}
		if (notFoundAlerta && !errorMessage.isEmpty()) {
			throw new NotFoundException(errorMessage);
		}
		return repository.findOne(campanhaId);
	}

	/**
	 * Procura todas as campanhas ativas para determinado time do coracao
	 * @param timeCoracaoId - time a ser procurado
	 * @return lista de todas as campanhas ativas do time do coracao pesquisado
	 */
	public List<Campanha> findAllCampanhasByTimeCoracao(Integer timeCoracaoId) {
		return repository.findCampanhaByTimeCoracaoId(timeCoracaoId, StatusDefaults.CAMPANHA_EXPIRADA);
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
	public Campanha create(Campanha campanha) throws NotificacaoException {
		validateAndFixOverlaps(campanha);

		Campanha saved = repository.save(campanha);
		
		try {
			notificadorCampanhaService.notificarCadastro(saved);
		} catch (AmqpException | JsonProcessingException e) {
			throw new NotificacaoException("Falha ao tentar notificar cadastro da campanha " + saved, e);
		}
		
		return saved;
	}

	/**
	 * Valida se existem campanhas relacionadas no intervalo de tempo da camapnha a ser cadastrada.
	 * Caso existam campanhas relacionadas, é acrescentado um dia ao final das campanhas relacionadas e assim sucessivamente ate que nao existam duas pesquisas do mesmo time com a mesma data de vencimento 
	 * @param campanha - campanha a ser validada
	 * @throws NotificacaoException 
	 */
	private void validateAndFixOverlaps(Campanha campanha) throws NotificacaoException {
		List<Campanha> overlappings = repository.findOverlapCampanhas(campanha.getCampanhaId(), campanha.getTimeCoracao().getTimeCoracaoId(), campanha.getDataInicio(), campanha.getDataVencimento(), StatusDefaults.CAMPANHA_EXPIRADA);

		if (overlappings != null && !overlappings.isEmpty()) {
			fixOverlapsDataVencimento(campanha, overlappings);
		}
	}

	/**
	 * Executa a logica necessaria para que nao existam campanhas ativas do mesmo time com a mesma data de vencimento
	 * @param campanha - campanha nova com a referencia de periodo (Data de vencimento)
	 * @param overlappings - lista de campanhas relacionadas ao periodo da nova campanha
	 * @throws NotificacaoException 
	 */
	private void fixOverlapsDataVencimento(Campanha campanha, List<Campanha> overlappings) throws NotificacaoException {
		addDayInOverlappings(overlappings, campanha);

		List<Campanha> updateOverlaps = overlappings;
		updateOverlaps = repository.save(updateOverlaps);
		
		try {
			notificadorCampanhaService.notificarAlteracoes(updateOverlaps);
		} catch (AmqpException | JsonProcessingException e) {
			throw new NotificacaoException("Falha ao tentar notificar alterações realizadas nas campanhas", e);
		}
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

	public List<Campanha> consultarCampanhasDoTimeDoCoracaoNaoAssociadas(Integer timeCoracaoId, List<Campanha> campanhasAssociadas) throws AllAssociateException {
		List<Integer> campanhaIds = campanhasAssociadas.stream().map(Campanha::getCampanhaId).collect(Collectors.toList());

		List<Campanha> notAssociate = repository.findCampanhaNotAssociate(timeCoracaoId, StatusDefaults.CAMPANHA_EXPIRADA, campanhaIds);

		if(notAssociate == null || notAssociate.isEmpty()) {
			throw new AllAssociateException(String.format("Campanhas do time do coração %d já associadas", timeCoracaoId));
		}
		return notAssociate;
	}

}