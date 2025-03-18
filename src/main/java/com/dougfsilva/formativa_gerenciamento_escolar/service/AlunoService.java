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

import com.dougfsilva.formativa_gerenciamento_escolar.dto.CriaAlunoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.dto.EditaAlunoForm;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.ObjetoNaoEncontradoException;
import com.dougfsilva.formativa_gerenciamento_escolar.exception.OperacaoNaoPermitidaException;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Aluno;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Curso;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Email;
import com.dougfsilva.formativa_gerenciamento_escolar.model.Endereco;
import com.dougfsilva.formativa_gerenciamento_escolar.repository.AlunoRepository;
import com.dougfsilva.formativa_gerenciamento_escolar.repository.CursoRepository;
import com.dougfsilva.formativa_gerenciamento_escolar.utilidades.NormalizaNomeImagem;

import jakarta.transaction.Transactional;

@Service
public class AlunoService {
	
	@Value("${fotos.dir}")
	private String fotosDir;
	
	private final AlunoRepository repository;
	private final CursoRepository cursoRepository;
	private final ImagemService imagemService;
	
	public AlunoService(AlunoRepository repository, CursoRepository cursoRepository, ImagemService imagemService) {
		this.repository = repository;
		this.cursoRepository = cursoRepository;
		this.imagemService = imagemService;
	}

	@Transactional
	public Aluno criar(CriaAlunoForm form) {
		if (repository.existsByMatricula(form.matricula())) {
			throw new OperacaoNaoPermitidaException(
					String.format("Matrícula %s já existe no banco de dados", form.matricula()));
		}
		if (repository.existsByEmail(new Email(form.email()))) {
			throw new OperacaoNaoPermitidaException(
					String.format("Email %s já existe no banco de dados", form.email()));
		}
		Endereco endereco = new Endereco(form.pais(), form.estado(), form.cidade(), form.bairro(), form.cep(),
				form.rua(), form.numero());
		Curso curso = cursoRepository.findByIdOrElseThrow(form.cursoId());
		Aluno aluno = new Aluno(curso, form.matricula(), form.nome(), form.email(), form.telefone(), endereco);
		aluno.setFoto(getNomeDaImagemPadrao());
		Aluno alunoSalvo = repository.save(aluno);
		return repository.save(alunoSalvo);
	}
	
	@Transactional
	public Aluno salvarFoto(Long id, MultipartFile foto) {
		Aluno aluno = repository.findByIdOrElseThrow(id);
		Path pathDaFoto = getPathDaImagem(foto, aluno);
		imagemService.salvarImagem(foto, pathDaFoto);
		aluno.setFoto(pathDaFoto.getFileName().toString());
		return repository.save(aluno);
	}

	@Transactional
	public void deletar(Long id) {
		Aluno aluno = repository.findByIdOrElseThrow(id);
		if(!aluno.getFoto().equals(getNomeDaImagemPadrao())) {
			Path pathDaImagem = getPathPastaImagensAlunos().resolve(aluno.getFoto());
			imagemService.deletarImagem(pathDaImagem);
		}
		repository.delete(aluno);
	}

	@Transactional
	public Aluno editar(Long id, EditaAlunoForm form) {
		Aluno aluno = repository.findByIdOrElseThrow(id);
		form.cursoId().ifPresent(cursoId -> {
			Curso curso = cursoRepository.findByIdOrElseThrow(cursoId);
			aluno.setCurso(curso);
		});
		form.matricula().ifPresent(matricula -> {
			if (matricula.equalsIgnoreCase(aluno.getMatricula())) {
				return;
			}
			if (repository.existsByMatricula(matricula)) {
				throw new OperacaoNaoPermitidaException(
						String.format("Matrícula %s já existe no banco de dados", form.matricula().get()));
			}
			aluno.setMatricula(matricula);
		});
		form.nome().ifPresent(aluno::setNome);
		if (form.email() != null 
			&& !form.email().isBlank() 
			&& !form.email().equalsIgnoreCase(aluno.getEmail().getEmail())) {
				Email email = new Email(form.email());
				if (repository.existsByEmail(email)) {
					throw new OperacaoNaoPermitidaException(
							String.format("Email %s já existe no banco de dados", form.email()));
				}
				aluno.setEmail(new Email(form.email()));
		}
		form.telefone().ifPresent(aluno::setTelefone);
		form.pais().ifPresent(aluno.getEndereco()::setPais);
		form.estado().ifPresent(aluno.getEndereco()::setEstado);
		form.cidade().ifPresent(aluno.getEndereco()::setCidade);
		form.bairro().ifPresent(aluno.getEndereco()::setBairro);
		form.cep().ifPresent(aluno.getEndereco()::setCep);
		form.rua().ifPresent(aluno.getEndereco()::setRua);
		form.numero().ifPresent(aluno.getEndereco()::setNumero);
		return repository.save(aluno);
	}

	public Aluno buscarPeloId(Long id) {
		return repository.findByIdOrElseThrow(id);
	}

	public Aluno buscarPelaMatricula(String matricula) {
		return repository.findByMatricula(matricula).orElseThrow(() -> new ObjetoNaoEncontradoException(
				String.format("Aluno com matrícula %s não encontrado", matricula)));
	}

	public Page<Aluno> buscarPeloCurso(Long cursoId, Pageable paginacao) {
		Curso curso = cursoRepository.findByIdOrElseThrow(cursoId);
		return repository.findByCurso(curso, paginacao);
	}
	
	public Page<Aluno> buscarPeloNome(String nome, Pageable paginacao) {
		return repository.findByNomeContaining(nome, paginacao);
	}
	
	public Page<Aluno> buscarTodos(Pageable paginacao) {
		return repository.findAll(paginacao);
	}
	
	public Resource buscarImagem(String nomeDaImagem) throws IOException {
		Path pathDaImagem = getPathPastaImagensAlunos().resolve(nomeDaImagem.toLowerCase());
		Resource recurso = new UrlResource(pathDaImagem.toUri());
		return recurso;
	}
	
	private Path getPathDaImagem(MultipartFile foto, Aluno aluno) {
		String extensao = "." + StringUtils.getFilenameExtension(foto.getOriginalFilename().toLowerCase().trim());
		String nomeDaImagem = NormalizaNomeImagem.normalizar(aluno.getId().toString() + "_" + aluno.getNome()) + extensao;
		return getPathPastaImagensAlunos().resolve(nomeDaImagem.toLowerCase());
	}

	private String getNomeDaImagemPadrao() {
		return "aluno_padrao.jpg";
	}
	
	private Path getPathPastaImagensAlunos() {
		return Paths.get(this.fotosDir).resolve("alunos");
	}

}
