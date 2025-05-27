package com.senai.classline.service.impl;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.curso.CursoDTO;
import com.senai.classline.dto.curso.CursoEditarDTO;
import com.senai.classline.exceptions.curso.CursoAlreadyExists;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {
    private final CursoRepository repository;
    private final InstituicaoRepository instituicaoRepository;

    @Override
    public Curso criar(String id_instituicao, CursoDTO body) {
        Optional<Curso> curso = this.repository.findByNome(body.nome());
        if (curso.isPresent()) {
            throw new CursoAlreadyExists();
        }
        Optional<Instituicao> instituicaoExists = instituicaoRepository.findByIdInstituicao(id_instituicao);
        if(instituicaoExists.isEmpty()){
            throw new InstituicaoNotFound();
        }
        Curso newCurso = new Curso();
        newCurso.setDescricao(body.descricao());
        newCurso.setTipo(body.tipo());
        newCurso.setNome(body.nome());
        newCurso.setInstituicao(instituicaoExists.get());
        newCurso.setQtde_semestres(body.qtde_semestres());

        return this.repository.save(newCurso);

    }

    @Override
    public Curso editar(Long idCurso, CursoEditarDTO body) {
        return null;
    }
}
