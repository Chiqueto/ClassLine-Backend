package com.senai.classline.service.impl;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.grade.Grade;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.curso.CursoResponseDTO;
import com.senai.classline.dto.instituicao.InstituicaoDTO;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;
import com.senai.classline.dto.turma.TurmaResponseDTO;
import com.senai.classline.exceptions.curso.CursoNotFound;
import com.senai.classline.exceptions.turma.TurmaNotFound;
import com.senai.classline.repositories.*;
import com.senai.classline.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaServiceImpl implements TurmaService {
    private final TurmaRepository repository;
    private final CursoRepository cursoRepository;
    private final GradeRepository gradeRepository;
    private final SemestreRepository semestreRepository;
    private final ProfessorRepository professorRepository;

    @Override
    public Turma criar(Long id_curso, TurmaDTO body) {
        Optional<Curso> cursoExists = cursoRepository.findById(id_curso);

        if(cursoExists.isEmpty()){
            throw new CursoNotFound();
        }

        Curso curso = cursoExists.get();

        Turma turma = new Turma();
        turma.setNome(body.nome());
        turma.setCurso(curso);
        turma.setDt_fim(body.dt_fim());
        turma.setDt_inicio(body.dt_inicio());
        turma.setObservacao(body.observacao());
        turma.setTurno(body.turno());
        turma.setAtivo(true);

        Turma newTurma = repository.save(turma);
        Grade grade = new Grade();
        grade.setDescricao("Grade curricular referente à turma " + turma.getNome());
        grade.setTurma(newTurma);
        Grade newGrade =  gradeRepository.save(grade);
        turma.setGrade(newGrade);

        for (int nroSemestre = 1; nroSemestre <= newTurma.getCurso().getQtde_semestres(); nroSemestre++) {
            Semestre semestre = new Semestre();
            semestre.setSemestre(nroSemestre);
            semestre.setGrade(newGrade);
            Semestre newSemestre = semestreRepository.save(semestre);
            System.out.println(newSemestre);
        }

        return newTurma;
    }

    @Override
    public Turma editar(Long id_turma, TurmaEditarDTO body) {
        Optional<Turma> turmaExists = repository.findById(id_turma);
        if(turmaExists.isEmpty()){
            throw new TurmaNotFound("Turma não encontrada!");
        }

        Turma turma = turmaExists.get();

        if(body.nome() != null){turma.setNome(body.nome());}
        if(body.observacao() != null){turma.setObservacao(body.observacao());}
        if(body.dt_inicio() != null){turma.setDt_inicio(body.dt_inicio());}
        if(body.dt_fim() != null){turma.setDt_fim(body.dt_fim());}
        if(body.turno() != null){turma.setTurno(body.turno());}

        return this.repository.save(turma);
    }

    @Override
    public Turma inativar(Long id_turma) {
        Optional<Turma> turmaExists = repository.findById(id_turma);

        if(turmaExists.isEmpty()){
            throw new TurmaNotFound("Turma não encontrada!");
        }

        Turma turma = turmaExists.get();

        turma.setAtivo(false);
        repository.save(turma);
        return turma;

    }

    @Override
    public TurmaResponseDTO getTurmaById(Long id_turma) {
        Optional<Turma> turma = repository.findById(id_turma);

        if(turma.isEmpty()){
            throw new TurmaNotFound("Turma não encontrada");
        }
        Turma turmaEntity = turma.get();
        Curso cursoEntity = turmaEntity.getCurso();
        CursoResponseDTO curso = new CursoResponseDTO(
               cursoEntity.getIdCurso(),
                cursoEntity.getInstituicao().getIdInstituicao(),
                cursoEntity.getNome(),
                cursoEntity.getDescricao(),
                cursoEntity.getQtde_semestres(),
                cursoEntity.getTipo(),
                cursoEntity.getAtivo()
        );
        TurmaResponseDTO turmaDTO = new TurmaResponseDTO(
                turmaEntity.getIdTurma(),
                turmaEntity.getNome(),
                turmaEntity.getObservacao(),
                turmaEntity.getTurno(),
                turmaEntity.getDt_inicio(),
                turmaEntity.getDt_fim(),
                turmaEntity.getAtivo(),
                curso,
                turmaEntity.getGrade().getIdGrade()
        );
        System.out.println(turmaDTO.curso());
        return turmaDTO;
    }

    @Override
    public List<TurmaResponseDTO> GetTurmaByCurso(Long id_curso) {
        Optional<Curso> cursoExists = cursoRepository.findById(id_curso);

        if(cursoExists.isEmpty()){
            throw new CursoNotFound("Curso não encontrado"); // É uma boa prática adicionar uma mensagem na exceção
        }

        List<Turma> turmasEntity = repository.findByCurso_idCurso(id_curso);
        List<TurmaResponseDTO> turmasDTO = new ArrayList<>(); // Crie uma lista para armazenar os DTOs

        if(turmasEntity.isEmpty()){

            return turmasDTO;
        }

        for (Turma turmaEntity : turmasEntity) {
            Curso cursoEntity = turmaEntity.getCurso();


            CursoResponseDTO cursoDTO = new CursoResponseDTO(
                    cursoEntity.getIdCurso(),
                    cursoEntity.getInstituicao().getIdInstituicao(),
                    cursoEntity.getNome(),
                    cursoEntity.getDescricao(),
                    cursoEntity.getQtde_semestres(),
                    cursoEntity.getTipo(),
                    cursoEntity.getAtivo()
            );

            TurmaResponseDTO turmaDTO = new TurmaResponseDTO(
                    turmaEntity.getIdTurma(),
                    turmaEntity.getNome(),
                    turmaEntity.getObservacao(),
                    turmaEntity.getTurno(),
                    turmaEntity.getDt_inicio(),
                    turmaEntity.getDt_fim(),
                    turmaEntity.getAtivo(),
                    cursoDTO,
                    turmaEntity.getGrade().getIdGrade()
            );
            turmasDTO.add(turmaDTO);
        }

        return turmasDTO;
    }

    public List<TurmaResponseDTO> getTurmasByInstituicao(String idInstituicao) {

        List<Curso> cursosDaInstituicao = cursoRepository.findByInstituicao_IdInstituicao(idInstituicao);

        List<TurmaResponseDTO> todasAsTurmasDTO = new ArrayList<>();

        if (cursosDaInstituicao.isEmpty()) {
            return todasAsTurmasDTO;
        }


        for (Curso cursoEntity : cursosDaInstituicao) {

            CursoResponseDTO cursoDTO = new CursoResponseDTO(
                    cursoEntity.getIdCurso(),
                    cursoEntity.getInstituicao().getIdInstituicao(),
                    cursoEntity.getNome(),
                    cursoEntity.getDescricao(),
                    cursoEntity.getQtde_semestres(),
                    cursoEntity.getTipo(),
                    cursoEntity.getAtivo()
            );

            List<Turma> turmasDoCurso = repository.findByCurso_idCurso(cursoEntity.getIdCurso());

            if (!turmasDoCurso.isEmpty()) {
                for (Turma turmaEntity : turmasDoCurso) {

                    TurmaResponseDTO turmaDTO = new TurmaResponseDTO(
                            turmaEntity.getIdTurma(),
                            turmaEntity.getNome(),
                            turmaEntity.getObservacao(),
                            turmaEntity.getTurno(),
                            turmaEntity.getDt_inicio(),
                            turmaEntity.getDt_fim(),
                            turmaEntity.getAtivo(),
                            cursoDTO,
                            turmaEntity.getGrade().getIdGrade()
                    );
                    todasAsTurmasDTO.add(turmaDTO);
                }
            }
        }
        return todasAsTurmasDTO;
    }

    @Override
    public List<TurmaResponseDTO> getTurmasByProfessor(String idProfessor) {
        Optional<Professor> professor = professorRepository.findByIdProfessor(idProfessor);
        List<TurmaResponseDTO> turmasDTO = new ArrayList<>();

        if(professor.isEmpty()){
            return turmasDTO;
        }

        List<Turma> turmasEntity = repository.findTurmasByProfessorId(idProfessor);

        for (Turma turmaEntity : turmasEntity) {
            Curso cursoEntity = turmaEntity.getCurso();


            CursoResponseDTO cursoDTO = new CursoResponseDTO(
                    cursoEntity.getIdCurso(),
                    cursoEntity.getInstituicao().getIdInstituicao(),
                    cursoEntity.getNome(),
                    cursoEntity.getDescricao(),
                    cursoEntity.getQtde_semestres(),
                    cursoEntity.getTipo(),
                    cursoEntity.getAtivo()
            );

            TurmaResponseDTO turmaDTO = new TurmaResponseDTO(
                    turmaEntity.getIdTurma(),
                    turmaEntity.getNome(),
                    turmaEntity.getObservacao(),
                    turmaEntity.getTurno(),
                    turmaEntity.getDt_inicio(),
                    turmaEntity.getDt_fim(),
                    turmaEntity.getAtivo(),
                    cursoDTO,
                    turmaEntity.getGrade().getIdGrade()
            );
            turmasDTO.add(turmaDTO);
        }

        return turmasDTO;

    }
}
