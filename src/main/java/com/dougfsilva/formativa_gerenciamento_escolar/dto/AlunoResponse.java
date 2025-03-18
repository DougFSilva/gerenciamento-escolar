package com.dougfsilva.formativa_gerenciamento_escolar.dto;

import com.dougfsilva.formativa_gerenciamento_escolar.model.Aluno;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Endereco;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AlunoResponse {

	private Long id;
	private Curso curso;
	private String matricula;
	private String nome;
	private String email;
	private String telefone;
	private Endereco endereco;
	private String fotoUrl;
	
	public AlunoResponse(Aluno aluno, String baseUrl) {
		 this.id = aluno.getId();
	        this.curso = aluno.getCurso();
	        this.matricula = aluno.getMatricula();
	        this.nome = aluno.getNome();
	        this.email = aluno.getEmail().getEmail();
	        this.telefone = aluno.getTelefone();
	        this.endereco = aluno.getEndereco();
	        this.fotoUrl = baseUrl + aluno.getFoto();
	}
	
	public static AlunoResponse paraDto(Aluno aluno, String baseUrl) {
		return new AlunoResponse(aluno, baseUrl);
	}
	
}
