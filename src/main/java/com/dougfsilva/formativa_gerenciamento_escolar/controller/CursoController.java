package com.dougfsilva.formativa_gerenciamento_escolar.controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dougfsilva.formativa_gerenciamento_escolar.dto.CriaCursoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.dto.CursoResponse;
import com.dougfsilva.formativa_gerenciamento_escolar.dto.EditaCursoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Modalidade;
import com.dougfsilva.formativa_gerenciamento_escolar.service.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Value("${app.url.base}")
	private String urlBase;

	private final CursoService cursoService;

	public CursoController(CursoService cursoService) {
		this.cursoService = cursoService;
	}

	@PostMapping
	@Operation(summary = "Cria um curso através de um objeto CriaCursoForm", description = "Retorna um curso específico")
	public ResponseEntity<Void> criarCurso(@Valid @RequestBody CriaCursoForm form) {
		Curso curso = cursoService.criar(form);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(curso.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@Operation(summary = "Salva a imagen de um curso", description = "Retorna um curso específico")
	@PostMapping("/imagem/{id}")
	public ResponseEntity<CursoResponse> salvarFotoDeAluno(@PathVariable Long id,
			@RequestParam("file") MultipartFile file) {
		Curso curso = cursoService.salvarImagem(id, file);
		return ResponseEntity.ok().body(CursoResponse.paraDto(curso, getImagemUriBase()));
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Edita um curso através de um objeto EditaCursoForm", description = "Retorna um curso específico")
	public ResponseEntity<CursoResponse> editarCurso(@Valid @RequestBody EditaCursoForm form, @PathVariable Long id) {
		Curso curso = cursoService.editar(id, form);
		return ResponseEntity.ok().body(CursoResponse.paraDto(curso, getImagemUriBase()));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um curso pelo id", description = "Sem retorno")
	public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
		cursoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Busca um curso pelo id", description = "Retorna um curso específico")
	public ResponseEntity<CursoResponse> buscarCursoPeloId(@PathVariable Long id) {
		Curso curso = cursoService.buscarPeloId(id);
		return ResponseEntity.ok().body(CursoResponse.paraDto(curso, getImagemUriBase()));
	}

	@GetMapping("/area/{area}")
	@Operation(summary = "Busca cursos pela area tecnológica com paginação", description = "Retorna uma página cursos")
	public ResponseEntity<Page<CursoResponse>> buscarCursosPelaAreaTecnologica(@PathVariable String area,
			Pageable paginacao) {
		String urlImagem = getImagemUriBase();
		Page<CursoResponse> cursos = cursoService.buscarPelaAreaTecnologica(area, paginacao)
				.map(curso -> CursoResponse.paraDto(curso, urlImagem));
		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/modalidade/{modalidade}")
	@Operation(summary = "Busca cursos pela modalidade com paginação", description = "Retorna uma página cursos")
	public ResponseEntity<Page<CursoResponse>> buscarCursosPelaModalidade(@PathVariable Modalidade modalidade,
			Pageable paginacao) {
		String urlImagem = getImagemUriBase();
		Page<CursoResponse> cursos = cursoService.buscarPelaModalidade(modalidade, paginacao)
				.map(curso -> CursoResponse.paraDto(curso, urlImagem));
		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping
	@Operation(summary = "Busca todos cursos com paginação", description = "Retorna uma página cursos")
	public ResponseEntity<Page<CursoResponse>> buscarTodosCursos(Pageable paginacao) {
		String urlImagem = getImagemUriBase();
		Page<CursoResponse> cursos = cursoService.buscarTodos(paginacao)
				.map(curso -> CursoResponse.paraDto(curso, urlImagem));
		return ResponseEntity.ok().body(cursos);
	}

	@GetMapping("/imagem/{nomeDaImagem}")
	@Operation(summary = "Busca uma imagem de um curso", description = "Retorna uma imagem de um curso específico")
	public ResponseEntity<Resource> buscarImagem(@PathVariable String nomeDaImagem) throws IOException {
		Resource arquivo = cursoService.buscarImagem(nomeDaImagem);

		if (arquivo.exists() && arquivo.isReadable()) {
			String contentType = Files.probeContentType(arquivo.getFile().toPath());
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contentType).body(arquivo);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	private String getImagemUriBase() {
		return String.format("%s/cursos/imagem/", urlBase);
	}

}
