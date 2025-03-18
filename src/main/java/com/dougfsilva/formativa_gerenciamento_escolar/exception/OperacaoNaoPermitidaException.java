package com.dougfsilva.formativa_gerenciamento_escolar.exception;

public class OperacaoNaoPermitidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OperacaoNaoPermitidaException(String message, Throwable cause) {
		super(message, cause);
	}

	public OperacaoNaoPermitidaException(String message) {
		super(message);
	}

}
