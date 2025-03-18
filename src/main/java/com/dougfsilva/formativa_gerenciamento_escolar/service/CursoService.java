package com.dougfsilva.formativa_gerenciamento_escolar.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dougfsilva.formativa_gerenciamento_escolar.dto.CriaCursoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.dto.EditaCursoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.OperacaoNaoPermitidaException;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Modalidade;
import com.dougfsilva.formativa_gerenciamento_escolar.repository.CursoRepository;
import com.dougfsilva.formativa_gerenciamento_escolar.utilidades.NormalizaNomeImagem;

import jakarta.transaction.Transactional;

@Service
public class CursoService {

	@Value("${fotos.dir}")
	private String fotosDir;
	
	private final CursoRepository repository;
	private final ImagemService imagemService;

	public CursoService(CursoRepository repository, ImagemService imagemService) {
		this.repository = repository;
		this.imagemService = imagemService;
	}

	@Transactional
	public Curso criar(CriaCursoForm form) {
		if (repository.existsByTitulo(form.titulo())) {
			throw new OperacaoNaoPermitidaException(
					String.format("Curso com título %s já existe na base de dados", form.titulo()));	
		}
		Curso curso = new Curso(form.areaTecnologica(), Modalidade.paraEnum(form.modalidade()), form.titulo());
		curso.setImagem(getNomeDaImagemPadrao());
		Curso cursoSalvo = repository.save(curso);
		return repository.save(cursoSalvo);
	}

	@Transactional
	public Curso salvarImagem(Long id, MultipartFile imagem) {
		Curso curso = repository.findByIdOrElseThrow(id);
		Path pathDaImagem = getPathDaImagem(imagem, curso);
		imagemService.salvarImagem(imagem, pathDaImagem);
		curso.setImagem(pathDaImagem.getFileName().toString());
		return repository.save(curso);
	}

	@Transactional
	public void deletar(Long id) {
		Curso curso = repository.findByIdOrElseThrow(id);
		if(!curso.getImagem().equals(getNomeDaImagemPadrao())) {
			Path pathDaImagem = getPathPastaImagensCursos().resolve(curso.getImagem());
			imagemService.deletarImagem(pathDaImagem);
		}
		repository.delete(curso);
	}

	@Transactional
	public Curso editar(Long id, EditaCursoForm form) {
		Curso curso = repository.findByIdOrElseThrow(id);
		form.titulo().ifPresent(titulo -> {
			if (titulo.equalsIgnoreCase(curso.getTitulo())) {
				return;
			}
			if (repository.existsByTitulo(titulo)) {
				throw new OperacaoNaoPermitidaException(
					String.format("Curso com título %s já existe na base de dados", form.titulo().get()));
			}
			curso.setTitulo(titulo);
		});
		form.areaTecnologica().ifPresent(curso::setAreaTecnologica);
		form.modalidade().ifPresent(modalidade -> curso.setModalidade(Modalidade.paraEnum(modalidade)));
		return repository.save(curso);
	}

	public Curso buscarPeloId(Long id) {
		return repository.findByIdOrElseThrow(id);
	}

	public Page<Curso> buscarPelaAreaTecnologica(String areaTecnologica, Pageable paginacao) {
		return repository.findByAreaTecnologica(areaTecnologica, paginacao);
	}

	public Page<Curso> buscarPelaModalidade(Modalidade modalidade, Pageable paginacao) {
		return repository.findByModalidade(modalidade, paginacao);
	}

	public Page<Curso> buscarTodos(Pageable paginacao) {
		return repository.findAll(paginacao);
	}

	public Resource buscarImagem(String nomeDaImagem) throws IOException {
		Path pathDaImagem = getPathPastaImagensCursos().resolve(nomeDaImagem.toLowerCase());
		Resource recurso = new UrlResource(pathDaImagem.toUri());
		return recurso;
	}

	private Path getPathDaImagem(MultipartFile imagem, Curso curso) {
		String extensao = "." + StringUtils.getFilenameExtension(imagem.getOriginalFilename().toLowerCase().trim());
		String nomeDaImagem = NormalizaNomeImagem.normalizar(curso.getId().toString() + "_" + curso.getTitulo()) + extensao;
		return getPathPastaImagensCursos().resolve(nomeDaImagem.toLowerCase());
	}

	private String getNomeDaImagemPadrao() {
		return "curso_padrao.jpg";
	}
	
	private Path getPathPastaImagensCursos() {
		return Paths.get(this.fotosDir).resolve("cursos");
	}
	
}
