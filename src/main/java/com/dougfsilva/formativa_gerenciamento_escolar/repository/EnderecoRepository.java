package com.dougfsilva.formativa_gerenciamento_escolar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.ObjetoNaoEncontradoException;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	default Endereco findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException(
				String.format("Endereço com id %d não encontrado", id)));
	}
}
