package com.senai.classline.service.impl;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;
import com.senai.classline.exceptions.curso.CursoNotFound;
import com.senai.classline.exceptions.turma.TurmaNotFound;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.TurmaRepository;
import com.senai.classline.service.TurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TurmaServiceImpl implements TurmaService {
    private final TurmaRepository repository;
    private final CursoRepository cursoRepository;

    @Override
    public Turma criar(Long id_curso, TurmaDTO body) {
        Optional<Curso> cursoExists = cursoRepository.findById(id_curso.intValue());

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
        Optional<Turma> turmaExists = repository.findById(id_turma.intValue());
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
    public Turma inativa(Long id_turma) {
        return null;
    }
}
