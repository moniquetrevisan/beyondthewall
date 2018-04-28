package com.moniquetrevisan.basic.campanhaservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusCliente implements Codable<Integer> {

	ATIVO(1, "Ativo"), 
	INATIVO(2, "Inativo");

	StatusCliente(Integer code, String descricao) {
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
	public static StatusCliente ofCode(Integer code) {
		return Codable.ofCode(StatusCliente.class, code);
	}

	public String toString() {
		return String.format("%s {code = %s, descricao = %s }", this.getClass().getSimpleName(), code, descricao);
	}

}