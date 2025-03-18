package com.dougfsilva.formativa_gerenciamento_escolar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50)
	private String areaTecnologica;
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false)
	private Modalidade modalidade;
	
	@Column(nullable = false, unique = true)
	private String titulo;
	
	@Column(length = 255)
	private String imagem;
	
	public Curso(String areaTecnologica, Modalidade modalidade, String titulo) {
		this.areaTecnologica = areaTecnologica;
		this.modalidade = modalidade;
		this.titulo = titulo;
	}
}
