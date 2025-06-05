package com.senai.classline.service.impl;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.disciplina.DisciplinaDTO;
import com.senai.classline.dto.disciplina.DisciplinaResponseDTO;
import com.senai.classline.exceptions.curso.CursoAlreadyExists;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.repositories.DisciplinaRepository;
import com.senai.classline.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {
    private final DisciplinaRepository repository;

    @Override
    public DisciplinaResponseDTO criar(String id_instituicao, DisciplinaDTO body) {
        Optional<Disciplina> curso = this.repository.findByNome(body.nome());
        if (curso.isPresent()) {
            throw new CursoAlreadyExists();
        }
        Optional<Instituicao> instituicaoExists = instituicaoRepository.findByIdInstituicao(id_instituicao);
        if(instituicaoExists.isEmpty()){
            throw new InstituicaoNotFound();
        }
        Disciplina newCurso = new Disciplina();
        newCurso.setNome(body.nome());
        newCurso.setInstituicao(instituicaoExists.get());
        newCurso.setCarga_horario(body.carga_horaria());
        newCurso.setStatus(true);

        return this.repository.save(newCurso);
    }

    @Override
    public Disciplina inativar(Long id_disciplina, String id_instituicao) {
        return null;
    }

    @Override
    public Disciplina getDisciplinaById(Long id_disciplina) {
        return null;
    }

    @Override
    public List<Disciplina> getDisciplinaByInstituicao(String id_instituicao) {
        return List.of();
    }
}
