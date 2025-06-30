package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestreId;
import com.senai.classline.domain.frequencia.Frequencia;
import com.senai.classline.domain.grade.Grade;
import com.senai.classline.domain.nota.Nota;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.Aluno.AlunoDesempenhoDTO;
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

import java.util.*;
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
    private final NotaRepository notaRepository;
    private final FrequenciaRepository frequenciaRepository;



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

            if (!turmaRepository.existsById(idTurma)) {
                throw new NotFoundException("Turma com ID " + idTurma + " não encontrada.");
            }

            var disciplinasDaTurma = disciplinaSemestreRepository.findByTurmaId(idTurma);

            return disciplinasDaTurma.stream()
                    .map(DisciplinaSemestreResponseDTO::new)
                    .collect(Collectors.toList());
        }

    @Override
    @Transactional(readOnly = true)
    public Set<DisciplinaSemestreResponseDTO> getDisciplinasSemestreByAluno(String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        Set<DisciplinaSemestre> disciplinasDoAluno = disciplinaSemestreRepository.findByAluno(aluno.getIdAluno());

        return disciplinasDoAluno.stream()
                .map(DisciplinaSemestreResponseDTO::new) 
                .collect(Collectors.toSet());

    }

    @Override
    @Transactional
    public DisciplinaSemestreResponseDTO trocarProfessor(TrocarProfessorDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(dto.idDisciplina())
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        Semestre semestre = semestreRepository.findById(dto.idSemestre())
                .orElseThrow(() -> new NotFoundException("Semestre não encontrado."));
        Professor professorAntigo = professorRepository.findById(dto.idProfessorAntigo())
                .orElseThrow(() -> new NotFoundException("Professor antigo não encontrado."));
        Professor professorNovo = professorRepository.findById(dto.idProfessorNovo())
                .orElseThrow(() -> new NotFoundException("Novo professor não encontrado."));

        DisciplinaSemestreId idAntigo = new DisciplinaSemestreId(
                disciplina.getIdDisciplina(), semestre.getIdSemestre(), professorAntigo.getIdProfessor());

        DisciplinaSemestre registroAntigo = disciplinaSemestreRepository.findById(idAntigo)
                .orElseThrow(() -> new NotFoundException("A associação com o professor antigo não foi encontrada."));

        StatusSemestre statusOriginal = registroAntigo.getStatus();

        registroAntigo.setStatus(StatusSemestre.INATIVO);

        DisciplinaSemestreId idNovo = new DisciplinaSemestreId(
                disciplina.getIdDisciplina(), semestre.getIdSemestre(), professorNovo.getIdProfessor());

        if (disciplinaSemestreRepository.findById(idNovo).isPresent()) {
            throw new AlreadyExists("Este professor já está alocado para esta disciplina/semestre.");
        }

        DisciplinaSemestre novoRegistro = new DisciplinaSemestre();
        novoRegistro.setId(idNovo);
        novoRegistro.setDisciplina(disciplina);
        novoRegistro.setSemestre(semestre);
        novoRegistro.setProfessor(professorNovo);
        novoRegistro.setStatus(statusOriginal);

        disciplinaSemestreRepository.saveAll(List.of(registroAntigo, novoRegistro));

        return new DisciplinaSemestreResponseDTO(novoRegistro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlunoDesempenhoDTO> getDesempenhoAlunosPorTurma(Long idDisciplina, Long idSemestre, String idProfessor) {

        DisciplinaSemestre disciplinaSemestre = disciplinaSemestreRepository
                .findById_IdDisciplinaAndId_IdSemestreAndId_IdProfessor(idDisciplina, idSemestre, idProfessor)
                .orElseThrow(() -> new NotFoundException("Nenhuma classe (Disciplina/Semestre/Professor) encontrada para os filtros fornecidos."));

        Semestre semestre = disciplinaSemestre.getSemestre();
        if (semestre == null || semestre.getGrade() == null) {
            throw new IllegalStateException("A classe encontrada não possui Semestre ou Grade associada.");
        }
        Grade grade = semestre.getGrade();

        Turma turma = turmaRepository.findByGrade(grade)
                .orElseThrow(() -> new NotFoundException("Nenhuma Turma encontrada para a grade da classe."));

        Set<Aluno> alunosDaTurma = turma.getAluno();
        if (alunosDaTurma.isEmpty()) {
            return List.of();
        }
        Long idTurma = turma.getIdTurma();

        List<Nota> notasDaTurma = notaRepository
                .findByAvaliacao_Turma_IdTurmaAndAvaliacao_Disciplina_IdDisciplina(idTurma, idDisciplina);

        List<Frequencia> frequenciasDaTurma = frequenciaRepository
                .findByAluno_Turma_IdTurmaAndAula_Disciplina_IdDisciplina(idTurma, idDisciplina);


        Map<String, List<Nota>> mapaDeNotasPorIdAluno = notasDaTurma.stream()
                .collect(Collectors.groupingBy(nota -> nota.getAluno().getIdAluno()));

        Map<String, List<Frequencia>> mapaDeFrequenciasPorIdAluno = frequenciasDaTurma.stream()
                .collect(Collectors.groupingBy(frequencia -> frequencia.getAluno().getIdAluno()));

        return alunosDaTurma.stream()
                .map(aluno -> {
                    List<Nota> notasDoAluno = mapaDeNotasPorIdAluno.getOrDefault(aluno.getIdAluno(), List.of());
                    List<Frequencia> frequenciasDoAluno = mapaDeFrequenciasPorIdAluno.getOrDefault(aluno.getIdAluno(), List.of());

                    float mediaFinal = calcularMediaPonderada(notasDoAluno);
                    float percentualFrequencia = calcularPercentualFrequencia(frequenciasDoAluno);

                    return new AlunoDesempenhoDTO(
                            aluno.getIdAluno(),
                            aluno.getNome(),
                            mediaFinal,
                            percentualFrequencia
                    );
                })
                .sorted(Comparator.comparing(AlunoDesempenhoDTO::nomeAluno))
                .collect(Collectors.toList());
    }

    private float calcularMediaPonderada(List<Nota> notas) {
        if (notas == null || notas.isEmpty()) return 0.0f;
        double somaPonderada = notas.stream().mapToDouble(n -> n.getValor() * n.getAvaliacao().getPeso()).sum();
        double somaPesos = notas.stream().mapToDouble(n -> n.getAvaliacao().getPeso()).sum();
        return (somaPesos == 0) ? 0.0f : (float) (somaPonderada / somaPesos);
    }

    private float calcularPercentualFrequencia(List<Frequencia> frequencias) {
        if (frequencias == null || frequencias.isEmpty()) return 100.0f; // Se não houve aulas, 100%
        long totalAulasRegistradas = frequencias.size();
        long presencas = frequencias.stream().filter(Frequencia::getPresente).count();
        return ((float) presencas / totalAulasRegistradas) * 100.0f;
    }

}
