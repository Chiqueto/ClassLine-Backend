package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestreId;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.dto.disciplinaSemestre.DisciplinaSemestreResponseDTO;
import com.senai.classline.dto.disciplinaSemestre.TrocarProfessorDTO;
import com.senai.classline.enums.StatusSemestre;
import com.senai.classline.exceptions.global.AlreadyExists;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.*;
import com.senai.classline.service.DisciplinaSemestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisciplinaSemestreServiceImpl implements DisciplinaSemestreService {

    private final DisciplinaSemestreRepository disciplinaSemestreRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final SemestreRepository semestreRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;
    private final AlunoRepository alunoRepository;



    @Override
    @Transactional
    public DisciplinaSemestre addDisciplina(Long id_disciplina, Long id_semestre, String id_professor) {
        DisciplinaSemestreId id = new DisciplinaSemestreId(id_disciplina, id_semestre, id_professor);
        if (disciplinaSemestreRepository.existsById(id)) {
            throw new RuntimeException("Esta disciplina já foi adicionada para este semestre e professor.");
        }

        Disciplina disciplina = disciplinaRepository.findById(id_disciplina)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        Semestre semestre = semestreRepository.findById(id_semestre)
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado."));
        Professor professor = professorRepository.findById(id_professor)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));

        Date data_atual = new Date();

        StatusSemestre statusSemestre;

        if (data_atual.after(semestre.getDt_inicio()) && data_atual.before(semestre.getDt_fim())) {
            statusSemestre = StatusSemestre.EM_ANDAMENTO;
        } else {
            statusSemestre = StatusSemestre.NAO_INICIADO;
        }

        DisciplinaSemestre novaEntrada = new DisciplinaSemestre(
                id,
                disciplina,
                semestre,
                professor,
                statusSemestre
        );

        return disciplinaSemestreRepository.save(novaEntrada);
    }

    @Override
    @Transactional
    public DisciplinaSemestre concludeSemester(Long id_disciplina, Long id_semestre, String id_professor) {
        DisciplinaSemestreId id = new DisciplinaSemestreId(id_disciplina, id_semestre, id_professor);
        DisciplinaSemestre disciplinaSemestre = disciplinaSemestreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        disciplinaSemestre.setStatus(StatusSemestre.CONCLUIDO);
        return disciplinaSemestreRepository.save(disciplinaSemestre);
    }



    @Override
    @Transactional
    public DisciplinaSemestre inactivateSemester(Long id_disciplina, Long id_semestre, String id_professor) {
        DisciplinaSemestreId id = new DisciplinaSemestreId(id_disciplina, id_semestre, id_professor);
        DisciplinaSemestre disciplinaSemestre = disciplinaSemestreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        disciplinaSemestre.setStatus(StatusSemestre.INATIVO);
        return disciplinaSemestreRepository.save(disciplinaSemestre);
    }

    @Override
    public List<DisciplinaSemestreResponseDTO> getGradeByTurma(Long idTurma) {
            // 1. Validação: verificar se a turma realmente existe
            if (!turmaRepository.existsById(idTurma)) {
                throw new NotFoundException("Turma com ID " + idTurma + " não encontrada.");
            }

            // 2. Execução: chamar o método do repositório
            var disciplinasDaTurma = disciplinaSemestreRepository.findByTurmaId(idTurma);

            // 3. Transformação: converter a lista de entidades para uma lista de DTOs
            return disciplinasDaTurma.stream()
                    .map(DisciplinaSemestreResponseDTO::new) // Usa o construtor do DTO
                    .collect(Collectors.toList());
        }

    @Override
    @Transactional(readOnly = true)
    public Set<DisciplinaSemestreResponseDTO> getDisciplinasSemestreByAluno(String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        Set<DisciplinaSemestre> disciplinasDoAluno = disciplinaSemestreRepository.findByAluno(aluno.getIdAluno());

        return disciplinasDoAluno.stream()
                .map(DisciplinaSemestreResponseDTO::new) // Usa o construtor do DTO
                .collect(Collectors.toSet());

    }

    @Override
    @Transactional
    public DisciplinaSemestreResponseDTO trocarProfessor(TrocarProfessorDTO dto) {

        // --- PASSO 1: VALIDAÇÃO DOS IDs (permanece igual) ---
        Disciplina disciplina = disciplinaRepository.findById(dto.idDisciplina())
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        Semestre semestre = semestreRepository.findById(dto.idSemestre())
                .orElseThrow(() -> new NotFoundException("Semestre não encontrado."));
        Professor professorAntigo = professorRepository.findById(dto.idProfessorAntigo())
                .orElseThrow(() -> new NotFoundException("Professor antigo não encontrado."));
        Professor professorNovo = professorRepository.findById(dto.idProfessorNovo())
                .orElseThrow(() -> new NotFoundException("Novo professor não encontrado."));

        // --- PASSO 2: ENCONTRAR E INATIVAR O REGISTRO ANTIGO ---

        DisciplinaSemestreId idAntigo = new DisciplinaSemestreId(
                disciplina.getIdDisciplina(), semestre.getIdSemestre(), professorAntigo.getIdProfessor());

        DisciplinaSemestre registroAntigo = disciplinaSemestreRepository.findById(idAntigo)
                .orElseThrow(() -> new NotFoundException("A associação com o professor antigo não foi encontrada."));

        // Guarda o status atual antes de inativar
        StatusSemestre statusOriginal = registroAntigo.getStatus();

        // ** A MUDANÇA PRINCIPAL: Em vez de apagar, mudamos o status **
        registroAntigo.setStatus(StatusSemestre.INATIVO);


        // --- PASSO 3: CRIAR O NOVO REGISTRO ATIVO ---

        DisciplinaSemestreId idNovo = new DisciplinaSemestreId(
                disciplina.getIdDisciplina(), semestre.getIdSemestre(), professorNovo.getIdProfessor());

        // Verifica se já não existe um registro ativo para o novo professor (segurança extra)
        if (disciplinaSemestreRepository.findById(idNovo).isPresent()) {
            throw new AlreadyExists("Este professor já está alocado para esta disciplina/semestre.");
        }

        DisciplinaSemestre novoRegistro = new DisciplinaSemestre();
        novoRegistro.setId(idNovo);
        novoRegistro.setDisciplina(disciplina);
        novoRegistro.setSemestre(semestre);
        novoRegistro.setProfessor(professorNovo);
        // O novo registro herda o status que o antigo tinha (ex: EM_ANDAMENTO)
        novoRegistro.setStatus(statusOriginal);


        // --- PASSO 4: SALVAR AMBAS AS ALTERAÇÕES E RETORNAR ---

        // Salva o registro antigo (agora inativo) e o novo registro (ativo)
        disciplinaSemestreRepository.saveAll(List.of(registroAntigo, novoRegistro));

        // Retorna o DTO do novo registro, que é o que está ativo.
        return new DisciplinaSemestreResponseDTO(novoRegistro);
    }

}
