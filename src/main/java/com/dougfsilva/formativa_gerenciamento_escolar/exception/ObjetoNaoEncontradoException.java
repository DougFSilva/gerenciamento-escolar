package com.dougfsilva.formativa_gerenciamento_escolar.exception;

public class ObjetoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjetoNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjetoNaoEncontradoException(String message) {
		super(message);
	}
	
}
