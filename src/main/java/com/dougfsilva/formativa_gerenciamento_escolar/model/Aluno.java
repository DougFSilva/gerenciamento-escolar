package com.dougfsilva.formativa_gerenciamento_escolar.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "alunos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
	@JoinColumn(name = "curso_id", referencedColumnName = "id", nullable = false)
	private Curso curso;
	
	@Column(nullable = false, unique = true, length = 20)
	private String matricula;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Embedded
	@Column(nullable = false, unique = true, length = 100)
	private Email email;
	
	@Column(length = 20)
	private String telefone;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "endereco_id", referencedColumnName = "id", nullable = false)
	private Endereco endereco;
	
	@Column(length = 255)
	private String foto;
	
	public Aluno(Curso curso, String matricula, String nome, String email, String telefone, Endereco endereco) {
		this.curso = curso;
		this.matricula = matricula;
		this.nome = nome;
		this.email = new Email(email);
		this.telefone = telefone;
		this.endereco = endereco;
	}
	
}
