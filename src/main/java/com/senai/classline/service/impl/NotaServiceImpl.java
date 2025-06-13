package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;
import com.senai.classline.dto.Nota.NotaDetalhesDTO;
import com.senai.classline.dto.Nota.NotaResponseDTO;
import com.senai.classline.exceptions.global.AlreadyExists;
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
    public NotaResponseDTO salvar(Set<NotaDTO> body, Long idAvaliacao) {
        if (body == null || body.isEmpty()) {
            // Retorna um resultado vazio e amigável
            return new NotaResponseDTO(0, 0, "Nenhuma nota foi enviada para processamento.", Set.of());
        }

        // --- Validações Iniciais ---
        Avaliacao avaliacaoEncontrada = avaliacaoRepository.findById(idAvaliacao)
                .orElseThrow(() -> new NotFoundException("Avaliação não encontrada com ID: " + idAvaliacao));

        Set<String> alunoIds = body.stream()
                .map(NotaDTO::idAluno)
                .collect(Collectors.toSet());

        List<Aluno> alunosEncontrados = alunoRepository.findAllById(alunoIds);
        if (alunosEncontrados.size() != alunoIds.size()) {
            Set<String> idsEncontrados = alunosEncontrados.stream().map(Aluno::getIdAluno).collect(Collectors.toSet());
            alunoIds.removeAll(idsEncontrados);
            throw new EntityNotFoundException("Alunos não encontrados com os IDs: " + alunoIds);
        }

        Map<String, Aluno> alunoMap = alunosEncontrados.stream()
                .collect(Collectors.toMap(Aluno::getIdAluno, Function.identity()));

        // --- Lógica de Criação/Atualização ---

        Map<Aluno, Nota> notasExistentesMap = repository.findAllByAlunoInAndAvaliacao(alunosEncontrados, avaliacaoEncontrada)
                .stream()
                .collect(Collectors.toMap(Nota::getAluno, Function.identity()));

        Set<Nota> notasParaSalvar = body.stream().map(dto -> {
            Aluno aluno = alunoMap.get(dto.idAluno());
            Nota nota = notasExistentesMap.get(aluno);

            if (nota != null) {
                nota.setValor(dto.valor());
            } else {
                nota = new Nota();
                nota.setAluno(aluno);
                nota.setAvaliacao(avaliacaoEncontrada);
                nota.setValor(dto.valor());
            }
            return nota;
        }).collect(Collectors.toSet());

        // --- Contagem, Persistência e Mapeamento para DTO (Parte Corrigida) ---

        // 3. Conte as notas novas e as atualizadas.
        //    Usei seu getter `getIdNota()`, que vi na sua versão.
        long notasAtualizadas = notasParaSalvar.stream().filter(n -> n.getIdNota() != null).count();
        long notasCriadas = notasParaSalvar.size() - notasAtualizadas;

        // 4. Salve todas as notas de uma vez.
        List<Nota> notasSalvas = repository.saveAll(notasParaSalvar);

        // 5. Crie a mensagem dinâmica.
        String mensagem = String.format("Operação concluída. Notas criadas: %d. Notas atualizadas: %d.", notasCriadas, notasAtualizadas);

        // 6. ALTERAÇÃO PRINCIPAL: Mapeie as entidades salvas para DTOs de detalhes.
        //    Isso evita o erro de serialização do Hibernate (Lazy Loading).
        Set<NotaDetalhesDTO> notasDetalhes = notasSalvas.stream()
                .map(nota -> new NotaDetalhesDTO(
                        nota.getIdNota(),
                        nota.getValor(),
                        nota.getAluno().getNome() // Assumindo que Aluno tem o método getNome()
                ))
                .collect(Collectors.toSet());

        // 7. Retorne o DTO de resultado completo, agora com o Set de DTOs de detalhes.
        return new NotaResponseDTO(
                (int) notasCriadas,
                (int) notasAtualizadas,
                mensagem,
                notasDetalhes // <-- Usando o Set de DTOs, não mais o de Entidades
        );
    }
}
