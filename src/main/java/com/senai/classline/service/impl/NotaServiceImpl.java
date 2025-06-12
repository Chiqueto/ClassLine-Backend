package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.AlunoRepository;
import com.senai.classline.repositories.AvaliacaoRepository;
import com.senai.classline.repositories.NotaRepository;
import com.senai.classline.service.NotaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {
    private final NotaRepository repository;
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    @Transactional
    public Set<Nota> salvar(Set<NotaDTO> body) {
        if (body == null || body.isEmpty()) {
            return Set.of();
        }

        Set<String> alunoIds = body.stream()
                .map(NotaDTO::idAluno)
                .collect(Collectors.toSet());

        Set<Long> avaliacaoIds = body.stream()
                .map(NotaDTO::idAvaliacao)
                .collect(Collectors.toSet());

        List<Aluno> alunosEncontrados = alunoRepository.findAllById(alunoIds);
        List<Avaliacao> avaliacoesEncontradas = avaliacaoRepository.findAllById(avaliacaoIds);

        if (alunosEncontrados.size() != alunoIds.size()) {
            Set<String> idsEncontrados = alunosEncontrados.stream().map(Aluno::getIdAluno).collect(Collectors.toSet());
            alunoIds.removeAll(idsEncontrados);
            throw new EntityNotFoundException("Alunos não encontrados com os IDs: " + alunoIds);
        }

        if (avaliacoesEncontradas.size() != avaliacaoIds.size()) {
            Set<Long> idsEncontrados = avaliacoesEncontradas.stream().map(Avaliacao::getIdAvaliacao).collect(Collectors.toSet());
            avaliacaoIds.removeAll(idsEncontrados);
            throw new EntityNotFoundException("Avaliações não encontradas com os IDs: " + avaliacaoIds);
        }

        Map<String, Aluno> alunoMap = alunosEncontrados.stream()
                .collect(Collectors.toMap(Aluno::getIdAluno, Function.identity()));

        Map<Long, Avaliacao> avaliacaoMap = avaliacoesEncontradas.stream()
                .collect(Collectors.toMap(Avaliacao::getIdAvaliacao, Function.identity()));

        Set<Nota> notasParaSalvar = body.stream().map(dto -> {
            Aluno aluno = alunoMap.get(dto.idAluno());
            Avaliacao avaliacao = avaliacaoMap.get(dto.idAvaliacao());

            Nota nota = new Nota();
            nota.setValor(dto.valor());
            nota.setAluno(aluno);
            nota.setAvaliacao(avaliacao);

            return nota;
        }).collect(Collectors.toSet());

        List<Nota> notasSalvas = repository.saveAll(notasParaSalvar);

        return Set.copyOf(notasSalvas);
    }
}
