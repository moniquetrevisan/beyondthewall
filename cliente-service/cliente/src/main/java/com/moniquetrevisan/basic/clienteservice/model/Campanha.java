package com.moniquetrevisan.basic.clienteservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.moniquetrevisan.basic.clienteservice.util.DateUtil;
import com.moniquetrevisan.basic.clienteservice.util.StatusDefaults;

@Entity
@Table(name = "campanha")
public class Campanha implements Serializable {

	private static final long serialVersionUID = -5962483113083124279L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "campanhaId", nullable = false)
	private Integer campanhaId;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "dataInicio", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = "dataVencimento", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;

	@Column(name = "dataUltimaAtualizacao", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAtualizacao;

	@Column(name = "statusCampanha", nullable = false)
	private Integer statusCampanha;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "timeCoracaoId", nullable = false)
	private TimeCoracao timeCoracao;

	public Campanha(String nome, Integer timeCoracaoId, Date dataInicio, Date dataVencimento) {
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataVencimento = dataVencimento;
		this.dataUltimaAtualizacao = DateUtil.today();
		if(DateUtil.isToday(dataInicio)) {
			this.statusCampanha = StatusDefaults.CAMPANHA_ATIVA; // a campanha tem inicio no mesmo dia / imediato
		} else {
			this.statusCampanha = StatusDefaults.CAMPANHA_PENDENTE; // a campanha tem inicio em uma data futura
		}
		this.timeCoracao = new TimeCoracao(timeCoracaoId);
	}
	
	public Integer getCampanhaId() {
		return campanhaId;
	}

	public void setCampanhaId(Integer campanhaId) {
		this.campanhaId = campanhaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public Integer getStatusCampanha() {
		return statusCampanha;
	}

	public void setStatusCampanha(Integer statusCampanha) {
		this.statusCampanha = statusCampanha;
	}
	
	public TimeCoracao getTimeCoracao() {
		return timeCoracao;
	}
	
	public void setTimeCoracao(TimeCoracao timeCoracao) {
		this.timeCoracao = timeCoracao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campanhaId == null) ? 0 : campanhaId.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((dataUltimaAtualizacao == null) ? 0 : dataUltimaAtualizacao.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((statusCampanha == null) ? 0 : statusCampanha.hashCode());
		result = prime * result + ((timeCoracao == null) ? 0 : timeCoracao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campanha other = (Campanha) obj;
		if (campanhaId == null) {
			if (other.campanhaId != null)
				return false;
		} else if (!campanhaId.equals(other.campanhaId))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (dataUltimaAtualizacao == null) {
			if (other.dataUltimaAtualizacao != null)
				return false;
		} else if (!dataUltimaAtualizacao.equals(other.dataUltimaAtualizacao))
			return false;
		if (dataVencimento == null) {
			if (other.dataVencimento != null)
				return false;
		} else if (!dataVencimento.equals(other.dataVencimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (statusCampanha != other.statusCampanha)
			return false;
		if (timeCoracao == null) {
			if (other.timeCoracao != null)
				return false;
		} else if (!timeCoracao.equals(other.timeCoracao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Campanha [campanhaId=" + campanhaId + ", nome=" + nome + ", dataInicio=" + dataInicio
				+ ", dataVencimento=" + dataVencimento + ", dataUltimaAtualizacao=" + dataUltimaAtualizacao
				+ ", statusCampanha=" + statusCampanha + ", timeCoracao=" + timeCoracao + "]";
	}

}