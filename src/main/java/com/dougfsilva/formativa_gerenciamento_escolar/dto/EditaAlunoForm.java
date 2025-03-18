package com.dougfsilva.formativa_gerenciamento_escolar.dto;

import java.util.Optional;

import jakarta.validation.constraints.Email;

public record EditaAlunoForm(
		
		Optional<Long> cursoId,
		
		Optional<String> matricula,
		
		Optional<String> nome,
		
		@Email(message = "O E-mail é inválido")
		String email,
		
		Optional<String> telefone,
		
		Optional<String> pais,

	    Optional<String> estado,

	    Optional<String> cidade,

	    Optional<String> bairro,

	    Optional<String> cep,

	    Optional<String> rua,
	    
	    Optional<String> numero
		) {

}
