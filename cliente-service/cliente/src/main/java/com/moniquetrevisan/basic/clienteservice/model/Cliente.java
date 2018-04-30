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

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = -5358519701833478337L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "clienteId")
	private Integer clienteId;

	@Column(name = "nomeCompleto")
	private String nomeCompleto;

	@Column(name = "email")
	private String email;

	@Column(name = "dataNascimento")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(name = "statusCliente")
	private Integer statusCliente;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "timeCoracaoId")
	private TimeCoracao timeCoracao;

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getStatusCliente() {
		return statusCliente;
	}

	public void setStatusCliente(Integer statusCliente) {
		this.statusCliente = statusCliente;
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
		result = prime * result + ((clienteId == null) ? 0 : clienteId.hashCode());
		result = prime * result + ((dataNascimento == null) ? 0 : dataNascimento.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nomeCompleto == null) ? 0 : nomeCompleto.hashCode());
		result = prime * result + ((statusCliente == null) ? 0 : statusCliente.hashCode());
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
		Cliente other = (Cliente) obj;
		if (clienteId == null) {
			if (other.clienteId != null)
				return false;
		} else if (!clienteId.equals(other.clienteId))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nomeCompleto == null) {
			if (other.nomeCompleto != null)
				return false;
		} else if (!nomeCompleto.equals(other.nomeCompleto))
			return false;
		if (statusCliente == null) {
			if (other.statusCliente != null)
				return false;
		} else if (!statusCliente.equals(other.statusCliente))
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
		return "Cliente [clienteId=" + clienteId + ", nomeCompleto=" + nomeCompleto + ", email=" + email
				+ ", dataNascimento=" + dataNascimento + ", statusCliente=" + statusCliente + ", timeCoracao="
				+ timeCoracao + "]";
	}

}