package com.moniquetrevisan.basic.campanhaservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCampanha implements Codable<Integer> {

	ATIVO(1, "Ativa"), 
	INATIVA(2, "Inativa"), 
	EXPIRADA(3, "Expirada");

	StatusCampanha(Integer code, String descricao) {
		this.code = code;
		this.descricao = descricao;
	}

	private Integer code;
	private String descricao;

	@JsonValue
	@Override
	public Integer getCode() {
		return code;
	}

	@JsonCreator
	public static StatusCampanha ofCode(Integer code) {
		return Codable.ofCode(StatusCampanha.class, code);
	}

	public String toString() {
		return String.format("%s {code = %s, descricao = %s }", this.getClass().getSimpleName(), code, descricao);
	}

}