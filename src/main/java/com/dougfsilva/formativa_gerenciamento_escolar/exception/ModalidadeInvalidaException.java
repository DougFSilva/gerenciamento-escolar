package com.dougfsilva.formativa_gerenciamento_escolar.exception;

public class ModalidadeInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ModalidadeInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	public ModalidadeInvalidaException(String message) {
		super(message);
	}

}
