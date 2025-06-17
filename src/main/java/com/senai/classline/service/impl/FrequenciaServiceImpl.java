package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.aula.Aula;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.frequencia.Frequencia;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.aula.AulaDTO;
import com.senai.classline.dto.aula.AulaResponseDTO;
import com.senai.classline.dto.frequencia.AlunoFrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.*;
import com.senai.classline.service.FrequenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FrequenciaServiceImpl implements FrequenciaService {
    final ProfessorRepository professorRepository;
    final DisciplinaRepository disciplinaRepository;
    final FrequenciaRepository frequenciaRepository;
    final AulaServiceImpl aulaService;
    final AlunoRepository alunoRepository;
    final AulaRepository aulaRepository;

    @Override
    public List<FrequenciaResponseDTO> lancarFrequencia(List<FrequenciaDTO> body, Long idDisciplina, String idProfessor, AulaDTO aulaBody) {
        Professor professor = this.professorRepository.findById(idProfessor)
                .orElseThrow(() -> new NotFoundException("Professor não encontrado com ID: " + idProfessor));

        Disciplina disciplina = this.disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com ID: " + idDisciplina));

        Aula aula = aulaService.criar(idProfessor, idDisciplina, aulaBody);

        List<Frequencia> frequenciaParaSalvar = new ArrayList<>();

        for (FrequenciaDTO freqDTO : body) {
            Aluno aluno = this.alunoRepository.findById(freqDTO.idAluno())
                    .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + freqDTO.idAluno()));

            Frequencia novaFrequencia = new Frequencia();
            novaFrequencia.setAluno(aluno);
            novaFrequencia.setAula(aula);
            novaFrequencia.setPresente(freqDTO.presente());
            novaFrequencia.setProfessor(professor);

            frequenciaParaSalvar.add(novaFrequencia);
        }

        // Salva todas as presenças de uma só vez. É muito mais rápido.
        List<Frequencia> frequencias = this.frequenciaRepository.saveAll(frequenciaParaSalvar);

        return frequencias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FrequenciaResponseDTO convertToDTO(Frequencia frequencia) {
        // Extração segura dos IDs para evitar erros de NullPointer
        String alunoId = frequencia.getAluno() != null ? frequencia.getAluno().getIdAluno() : null;
        Long aulaId = frequencia.getAula() != null ? frequencia.getAula().getIdAula() : null;
        String professorId = (frequencia.getAula() != null && frequencia.getAula().getProfessor() != null)
                ? frequencia.getAula().getProfessor().getIdProfessor()
                : null;

        return new FrequenciaResponseDTO(
                frequencia.getIdFrequencia(),
                alunoId,
                aulaId,
                professorId,
                frequencia.getPresente()
        );
    }

    public List<AlunoFrequenciaDTO> getFrequenciaPorDiaDeAula(Long idDisciplina, String idProfessor, LocalDate data) {
        Professor professor = professorRepository.findById(idProfessor)
                .orElseThrow(() -> new NotFoundException("Professor não encontrado com ID: " + idProfessor));

        Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com ID: " + idDisciplina));

        Aula aula = aulaRepository.findByProfessorAndDisciplinaAndData(professor, disciplina, data)
                .orElseThrow(() -> new NotFoundException("Nenhuma aula encontrada para os critérios informados."));

        List<Frequencia> frequencias = frequenciaRepository.findByAulaIdComAlunos(aula.getIdAula());

        return frequencias.stream()
                .map(frequencia -> new AlunoFrequenciaDTO(
                        frequencia.getAluno().getIdAluno(),
                        frequencia.getAluno().getNome(),
                        frequencia.getPresente()
                ))
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public List<FrequenciaResponseDTO> editarFrequencia(List<FrequenciaDTO> body, Long idDisciplina, String idProfessor, AulaDTO aulaBody) {

        // --- PASSO 1: BUSCAR A AULA-ALVO ---
        // Esta parte do seu código já estava correta.
        Professor professor = this.professorRepository.findById(idProfessor)
                .orElseThrow(() -> new NotFoundException("Professor não encontrado com ID: " + idProfessor));

        Disciplina disciplina = this.disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com ID: " + idDisciplina));

        Aula aula = this.aulaRepository.findByProfessorAndDisciplinaAndData(professor, disciplina, aulaBody.data())
                .orElseThrow(() -> new NotFoundException("Aula não encontrada para os critérios informados. Impossível editar."));


        // --- PASSO 2: A LÓGICA DE EDIÇÃO CORRETA COMEÇA AQUI ---

        // 2.1. Busca todos os registros de frequência que JÁ EXISTEM para esta aula.
        List<Frequencia> frequenciasExistentes = frequenciaRepository.findByAula_IdAula(aula.getIdAula());

        // 2.2. Organiza os registros em um mapa para acesso instantâneo pelo ID do aluno.
        Map<String, Frequencia> mapaFrequenciasPorAluno = frequenciasExistentes.stream()
                .collect(Collectors.toMap(f -> f.getAluno().getIdAluno(), Function.identity()));

        // 2.3. Itera sobre os dados da requisição para aplicar as mudanças.
        for (FrequenciaDTO freqDTO : body) {
            // Encontra o registro de frequência correspondente no mapa.
            Frequencia frequenciaParaAtualizar = mapaFrequenciasPorAluno.get(freqDTO.idAluno());

            // Se o registro existir, MODIFICA seu estado. Não cria um novo.
            if (frequenciaParaAtualizar != null) {
                frequenciaParaAtualizar.setPresente(freqDTO.presente());
            }
            // Se não existir, ignoramos, pois a intenção é apenas editar.
        }

        // --- PASSO 3: PERSISTIR E RETORNAR ---

        // Salva todas as entidades que foram modificadas em memória.
        // O Hibernate é inteligente e saberá que deve executar UPDATEs.
        List<Frequencia> frequenciasSalvas = this.frequenciaRepository.saveAll(frequenciasExistentes);

        return frequenciasSalvas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
