package com.moniquetrevisan.basic.campanhaservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "time_coracao")
public class TimeCoracao implements Serializable {

	private static final long serialVersionUID = -2745581898200832582L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "timeCoracaoId", nullable = false)
	private Integer timeCoracaoId;

	@Column(name = "timeCoracaoNome", nullable = false)
	private String timeCoracaoNome;

	public Integer getTimeCoracaoId() {
		return timeCoracaoId;
	}

	public void setTimeCoracaoId(Integer timeCoracaoId) {
		this.timeCoracaoId = timeCoracaoId;
	}

	public String getTimeCoracaoNome() {
		return timeCoracaoNome;
	}

	public void setTimeCoracaoNome(String timeCoracaoNome) {
		this.timeCoracaoNome = timeCoracaoNome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((timeCoracaoId == null) ? 0 : timeCoracaoId.hashCode());
		result = prime * result + ((timeCoracaoNome == null) ? 0 : timeCoracaoNome.hashCode());
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
		TimeCoracao other = (TimeCoracao) obj;
		if (timeCoracaoId == null) {
			if (other.timeCoracaoId != null)
				return false;
		} else if (!timeCoracaoId.equals(other.timeCoracaoId))
			return false;
		if (timeCoracaoNome == null) {
			if (other.timeCoracaoNome != null)
				return false;
		} else if (!timeCoracaoNome.equals(other.timeCoracaoNome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TimeCoracao [timeCoracaoId=" + timeCoracaoId + ", timeCoracaoNome=" + timeCoracaoNome + "]";
	}

}