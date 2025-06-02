package com.senai.classline.service.impl;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.curso.CursoDTO;
import com.senai.classline.dto.curso.CursoEditarDTO;
import com.senai.classline.exceptions.curso.CursoAlreadyExists;
import com.senai.classline.exceptions.curso.CursoChangeUnauthorized;
import com.senai.classline.exceptions.curso.CursoNotFound;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public Curso editar(Long id_curso, CursoEditarDTO body, String id_instituicao) {
        Optional<Curso> cursoExists = this.repository.findById(id_curso);
        if(cursoExists.isEmpty()){
            throw new CursoNotFound();
        }

        Curso curso = cursoExists.get();

        if (!curso.getInstituicao().getIdInstituicao().equals(id_instituicao)) {
            throw new CursoChangeUnauthorized();
        }

        if(body.nome() != null){curso.setNome(body.nome());}
        if(body.tipo() != null){curso.setTipo(body.tipo());}
        if(body.descricao() != null){curso.setDescricao(body.descricao());}
        if(body.qtde_semestres() > 0 ){curso.setQtde_semestres(body.qtde_semestres());}
        return this.repository.save(curso);
    }

    @Override
    public Curso inativar(Long id_curso, String id_instituicao) {
        Optional<Curso> cursoExists = this.repository.findById(id_curso);
        if(cursoExists.isEmpty()){
            throw new CursoNotFound();
        }

        Curso curso = cursoExists.get();

        if (!curso.getInstituicao().getIdInstituicao().equals(id_instituicao)) {
            throw new CursoChangeUnauthorized();
        }

        curso.setAtivo(false);
        return this.repository.save(curso);
    }

    @Override
    public Curso getCursoById(Long id_curso) {
        Optional<Curso> cursoExists = this.repository.findById(id_curso);

        if(cursoExists.isEmpty()){
            throw new CursoNotFound();
        }

        return cursoExists.get();
    }

    @Override
    public List<Curso> getCursosByInstituicao(String id_instituicao) {
        List<Curso> cursos = repository.findByInstituicao_IdInstituicao(id_instituicao);
        if(cursos.isEmpty()){
            throw new CursoNotFound();
        }

        return cursos;
    }
}
