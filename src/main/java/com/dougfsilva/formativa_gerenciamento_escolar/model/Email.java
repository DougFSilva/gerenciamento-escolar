package com.dougfsilva.formativa_gerenciamento_escolar.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.EmailInvalidoException;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"email"})
@ToString
public class Email {

	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
	private String email;

	public Email(String email) {
		validar(email);
		this.email = email;
	}

	private void validar(String email) {
		if (email == null || email.isBlank()) {
			throw new EmailInvalidoException("O email não pode ser vazio");
		}

		Pattern pattern = Pattern.compile(EMAIL_REGEX);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches()) {
			throw new EmailInvalidoException("O email fornecido não é válido");
		}
	}

	public void setEmail(String email) {
		validar(email);
		this.email = email;
	}
	
	

}
