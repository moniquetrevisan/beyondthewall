package com.moniquetrevisan.basic.campanhaservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assc_campanha_cliente")
public class AssociacaoCampanhaCliente implements Serializable {

	private static final long serialVersionUID = -8512076763338960986L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "asscCampanhaClienteId", nullable = false)
	private Integer asscCampanhaClienteId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "campanhaId", nullable = false)
	private Campanha campanha;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clienteId", nullable = false)
	private Cliente cliente;

	public Integer getAsscCampanhaClienteId() {
		return asscCampanhaClienteId;
	}

	public void setAsscCampanhaClienteId(Integer asscCampanhaClienteId) {
		this.asscCampanhaClienteId = asscCampanhaClienteId;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asscCampanhaClienteId == null) ? 0 : asscCampanhaClienteId.hashCode());
		result = prime * result + ((campanha == null) ? 0 : campanha.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
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
		AssociacaoCampanhaCliente other = (AssociacaoCampanhaCliente) obj;
		if (asscCampanhaClienteId == null) {
			if (other.asscCampanhaClienteId != null)
				return false;
		} else if (!asscCampanhaClienteId.equals(other.asscCampanhaClienteId))
			return false;
		if (campanha == null) {
			if (other.campanha != null)
				return false;
		} else if (!campanha.equals(other.campanha))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AssociacaoCampanhaCliente [asscCampanhaClienteId=" + asscCampanhaClienteId + ", campanha=" + campanha
				+ ", cliente=" + cliente + "]";
	}

}