package com.senai.classline.service.impl;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.curso.CursoResponseDTO;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;
import com.senai.classline.dto.turma.TurmaResponseDTO;
import com.senai.classline.exceptions.curso.CursoNotFound;
import com.senai.classline.exceptions.turma.TurmaNotFound;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.TurmaRepository;
import com.senai.classline.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaServiceImpl implements TurmaService {
    private final TurmaRepository repository;
    private final CursoRepository cursoRepository;

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

        return repository.save(turma);
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
                cursoEntity.getInstituicao(),
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
                turmaEntity.getId_grade()
        );
        System.out.println(turmaDTO.curso());
        return turmaDTO;
    }

    @Override
    public List<TurmaResponseDTO> GetTurmaByCurso(Long id_curso) {
        Optional<Curso> cursoExists = cursoRepository.findById(id_curso);

        if(cursoExists.isEmpty()){
            throw new CursoNotFound();
        }

        List<Turma> turmaEntity = repository.findByCurso_idCurso(id_curso);
        return null;
    }
}
