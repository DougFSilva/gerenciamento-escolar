package com.dougfsilva.formativa_gerenciamento_escolar.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dougfsilva.formativa_gerenciamento_escolar.exception.ErroDeOperacaoComImagemException;

@Service
public class ImagemService {

	private static final List<String> EXTENSOES_PERMITIDAS = Arrays.asList("jpg");

	public void salvarImagem(MultipartFile imagem, Path caminhoArquivo) {
		if (imagem.getContentType() == null || !imagem.getContentType().startsWith("image/")) {
			throw new ErroDeOperacaoComImagemException("O arquivo enviado não é uma imagem válida.");
		}
		validarExtensaoDaImagem(imagem);
		try {
			Files.createDirectories(caminhoArquivo.getParent());
			Files.copy(imagem.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ErroDeOperacaoComImagemException(String.format("Erro ao salvar imagem: %s", e.getMessage()), e);
		}
	}

	public void deletarImagem(Path caminhoArquivo) {
		try {
			if (Files.exists(caminhoArquivo) && Files.isRegularFile(caminhoArquivo)) {
				Files.delete(caminhoArquivo);
			}
		} catch (IOException e) {
			throw new ErroDeOperacaoComImagemException(String.format("Erro ao deletar imagem: %s", e.getMessage()), e);
		}
	}

	private void validarExtensaoDaImagem(MultipartFile imagem) {
		String nomeDaImagem = imagem.getOriginalFilename();
		if (nomeDaImagem == null || nomeDaImagem.trim().isEmpty()) {
			throw new ErroDeOperacaoComImagemException("Nome do arquivo inválido.");
		}

		String fileExtension = getExtensaoDaImagem(nomeDaImagem);
		if (!EXTENSOES_PERMITIDAS.contains(fileExtension.toLowerCase())) {
			throw new ErroDeOperacaoComImagemException(String.format(
					"Apenas as extensões %s são permitidas", EXTENSOES_PERMITIDAS.toString()));
		}
	}

	private String getExtensaoDaImagem(String nomeDoArquivo) {
		return StringUtils.getFilenameExtension(nomeDoArquivo.toLowerCase().trim());
	}

}
