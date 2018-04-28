package com.moniquetrevisan.basic.campanhaservice.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.moniquetrevisan.basic.campanhaservice.enums.StatusCampanha;

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
	@Convert(converter = StatusCampanha.class)
	private StatusCampanha statusCampanha;

	/**
	 * OneToOne(fetch = FetchType.LAZY) OneToOne(fetch = FetchType.LAZY)
	 * JoinColumn(name = "timeCoracaoId", nullable = false) private Integer
	 * statusCampanha;
	 */

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

	public StatusCampanha getStatusCampanha() {
		return statusCampanha;
	}

	public void setStatusCampanha(StatusCampanha statusCampanha) {
		this.statusCampanha = statusCampanha;
	}

	@Override
	public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Campanha other = (Campanha) obj;
			if(campanhaId == other.getCampanhaId() && nome.trim().equalsIgnoreCase(other.getNome())) {
				return true;
			}
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((statusCampanha == null) ? 0 : statusCampanha.hashCode());
		result = prime * result + ((dataUltimaAtualizacao == null) ? 0 : dataUltimaAtualizacao.hashCode());
		result = prime * result + ((dataVencimento == null) ? 0 : dataVencimento.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((campanhaId == null) ? 0 : campanhaId.hashCode());
		return result;
	}

	@Override
	public String toString() {
		// not yet ... 
		return null;
	}

}