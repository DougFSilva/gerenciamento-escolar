package com.dougfsilva.formativa_gerenciamento_escolar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriaAlunoForm(
		
		@NotNull(message = "O Id do curso deve ser informado")
		Long cursoId,
		
		@NotBlank(message = "O campo matrícula não pode ser vazio")
		String matricula,
		
		@NotBlank(message = "O campo nome não pode ser vazio")
		String nome,
		
		@NotBlank(message = "O campo email não pode ser vazio")
		String email,
		
		String telefone,
		
		@NotBlank(message = "O campo email não pode ser vazio")
	    String pais,

		@NotBlank(message = "O campo email não pode ser vazio")
	    String estado,

		@NotBlank(message = "O campo email não pode ser vazio")
	    String cidade,

		@NotBlank(message = "O campo email não pode ser vazio")
	    String bairro,

		@NotBlank(message = "O campo email não pode ser vazio")
	    String cep,

		@NotBlank(message = "O campo email não pode ser vazio")
	    String rua,
	    
		@NotBlank(message = "O campo email não pode ser vazio")
	    String numero
		) {

}
