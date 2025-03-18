package com.dougfsilva.formativa_gerenciamento_escolar.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.ErroDeOperacaoComImagemException;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.ErroPadrao;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.ModalidadeInvalidaException;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.ObjetoNaoEncontradoException;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.OperacaoNaoPermitidaException;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;

@Hidden
@RestControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontradoExceptiom(ObjetoNaoEncontradoException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ExceptionHandler(ErroDeOperacaoComImagemException.class)
	public ResponseEntity<ErroPadrao> erroDeOperacaoComImagemException(ErroDeOperacaoComImagemException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(ModalidadeInvalidaException.class)
	public ResponseEntity<ErroPadrao> modalidadeInvalidaException(ModalidadeInvalidaException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(OperacaoNaoPermitidaException.class)
	public ResponseEntity<ErroPadrao> operacaoNaoPermitidaException(OperacaoNaoPermitidaException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErroPadrao> illegalArgumentException(IllegalArgumentException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErroPadrao> handleDataIntegrityViolation(DataIntegrityViolationException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de integridade de dados: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);

	}

	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<ErroPadrao> handleTransactionSystem(TransactionSystemException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação dos dados: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErroPadrao> handleConstraintViolation(ConstraintViolationException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro de validação: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<ErroPadrao> handleDatabaseException(DataAccessException e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro ao acessar o banco de dados: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErroPadrao> handleGenericException(Exception e) {
		ErroPadrao erro = new ErroPadrao(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Erro inesperado: " + e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
