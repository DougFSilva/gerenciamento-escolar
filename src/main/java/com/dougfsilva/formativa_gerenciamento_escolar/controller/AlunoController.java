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

import com.dougfsilva.formativa_gerenciamento_escolar.dto.AlunoResponse;
import com.dougfsilva.formativa_gerenciamento_escolar.dto.CriaAlunoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.dto.EditaAlunoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Aluno;
import com.dougfsilva.formativa_gerenciamento_escolar.service.AlunoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Gerenciamento de alunos")
public class AlunoController {
	
	@Value("${app.url.base}")
	private String urlBase;

	private final AlunoService alunoService;
	
	public AlunoController(AlunoService alunoService) {
		this.alunoService = alunoService;
	}

    @Operation(summary = "Cria um aluno através de um objeto CriaAlunoForm", description = "Retorna um aluno específico")
	@PostMapping
	public ResponseEntity<Void> criarAluno(@Valid @RequestBody CriaAlunoForm form) {
		Aluno aluno = alunoService.criar(form);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(aluno.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
    
    @Operation(summary = "Salva a foto de um aluno", description = "Retorna um aluno específico")
	@PostMapping("/foto/{id}")
	public ResponseEntity<AlunoResponse> salvarFotoDeAluno(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		Aluno aluno = alunoService.salvarFoto(id, file);
		return ResponseEntity.ok().body(AlunoResponse.paraDto(aluno, getImagemUriBase()));
	}
	
    @Operation(summary = "Edita um aluno através de um objeto EditaAlunoForm", description = "Retorna um aluno específico")
	@PatchMapping("/{id}")
	public ResponseEntity<AlunoResponse> editarAluno(@Valid @RequestBody EditaAlunoForm form, @PathVariable Long id) {
		Aluno aluno = alunoService.editar(id, form);
		return ResponseEntity.ok().body(AlunoResponse.paraDto(aluno, getImagemUriBase()));
	}
	
    @Operation(summary = "Deleta um aluno pelo id", description = "Sem retorno")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarAluno(@PathVariable Long id){
		alunoService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
    @Operation(summary = "Busca um aluno pelo id", description = "Retorna um aluno específico")
	@GetMapping("/{id}")
	public ResponseEntity<AlunoResponse> buscarAlunoPeloId(@PathVariable Long id) {
		Aluno aluno = alunoService.buscarPeloId(id);
		return ResponseEntity.ok().body(AlunoResponse.paraDto(aluno, getImagemUriBase()));
	}
	
    @Operation(summary = "Busca um aluno pela matrícula", description = "Retorna um aluno específico")
	@GetMapping("/matricula/{matricula}")
	public ResponseEntity<AlunoResponse> buscarAlunoPelaMatricula(@PathVariable String matricula) {
		Aluno aluno = alunoService.buscarPelaMatricula(matricula);
		return ResponseEntity.ok().body(AlunoResponse.paraDto(aluno, getImagemUriBase()));
	}
	
    @Operation(summary = "Busca alunos pelo nome que contém com paginação", description = "Retorna uma página de alunos")
	@GetMapping("/nome/{nome}")
	public ResponseEntity<Page<AlunoResponse>> buscarAlunosPeloNome(@PathVariable String nome, Pageable paginacao) {
    	String urlImagem = getImagemUriBase();
    	Page<AlunoResponse> alunos = alunoService.buscarPeloNome(nome, paginacao)
				.map(aluno -> AlunoResponse.paraDto(aluno, urlImagem));
		return ResponseEntity.ok().body(alunos);
	}
	
    @Operation(summary = "Busca todos alunos com paginação", description = "Retorna uma página de alunos")
	@GetMapping
	public ResponseEntity<Page<AlunoResponse>> buscarTodosAlunos(Pageable paginacao) {
    	String urlImagem = getImagemUriBase();
    	Page<AlunoResponse> alunos = alunoService.buscarTodos(paginacao)
				.map(aluno -> AlunoResponse.paraDto(aluno, urlImagem));
		return ResponseEntity.ok().body(alunos);
	}
    
    @GetMapping("/imagem/{nomeDaImagem}")
	@Operation(summary = "Busca uma imagem de um aluno", description = "Retorna uma imagem de um aluno específico")
	public ResponseEntity<Resource> buscarImagem(@PathVariable String nomeDaImagem) throws IOException {
	    Resource arquivo = alunoService.buscarImagem(nomeDaImagem); 

	    if (arquivo.exists() && arquivo.isReadable()) {
	        String contentType = Files.probeContentType(arquivo.getFile().toPath());
	        if (contentType == null) {
	            contentType = "application/octet-stream"; 
	        }

	        return ResponseEntity.ok()
	                .header(HttpHeaders.CONTENT_TYPE, contentType)
	                .body(arquivo);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	    }
	}
    
    private String getImagemUriBase() {
		return String.format("%s/alunos/imagem/", urlBase);
	}
  
}
