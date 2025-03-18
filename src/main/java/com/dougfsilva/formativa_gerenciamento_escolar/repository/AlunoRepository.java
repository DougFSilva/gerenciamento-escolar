package com.dougfsilva.formativa_gerenciamento_escolar.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.ObjetoNaoEncontradoException;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Aluno;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Email;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	default Aluno findByIdOrElseThrow(Long id) {
		return findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException(
				String.format("Aluno com id %d não encontrado", id)));
	}
	
	Optional<Aluno> findByMatricula(String matricula);
	
	Page<Aluno> findByCurso(Curso curso, Pageable paginacao);
	
	Page<Aluno> findByNomeContaining(String nome, Pageable paginacao);
	
	boolean existsByMatricula(String matricula);
	
	boolean existsByEmail(Email email);
	
}
