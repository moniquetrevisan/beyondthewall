package com.moniquetrevisan.basic.campanhaservice.exception;

public class NotificacaoException extends Exception {

	private static final long serialVersionUID = 8750015370341055306L;

	public NotificacaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}