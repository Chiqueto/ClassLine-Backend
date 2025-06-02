package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.Aluno.AlunoDTO;
import com.senai.classline.dto.Aluno.AlunoEditarDTO;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.exceptions.global.AlreadyExists;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.AlunoRepository;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.TurmaRepository;
import com.senai.classline.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {
    private final AlunoRepository repository;
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;

    @Override
    public Aluno salvar(AlunoDTO alunoDTO) {
        Optional<Aluno> alunoExists = this.repository.findByCpf(alunoDTO.cpf());
        if(alunoExists.isPresent()){
            throw new AlreadyExists("Aluno com esse CPF já cadastrado");
        }

        Optional<Aluno> emailAlreadyInUse = this.repository.findByEmail(alunoDTO.email());
        if(emailAlreadyInUse.isPresent()){
            throw new AlreadyExists("E-mail já em uso!");
        }

        Optional<Turma> turma = this.turmaRepository.findById(alunoDTO.id_turma());
        Optional<Curso> curso = this.cursoRepository.findById(alunoDTO.id_curso());

        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());
        aluno.setSenha(alunoDTO.senha());
        aluno.setCpf(alunoDTO.cpf());
        aluno.setDt_nascimento(alunoDTO.dt_nascimento());
        aluno.setGenero(alunoDTO.genero());
        aluno.setLogradouro(alunoDTO.logradouro());
        aluno.setBairro(alunoDTO.bairro());
        aluno.setNumero(alunoDTO.numero());
        aluno.setCidade(alunoDTO.cidade());
        aluno.setTelefone(alunoDTO.telefone());
        aluno.setTurno(alunoDTO.turno());
        aluno.setStatus(alunoDTO.status());
        aluno.setTurma(turma.isPresent() ? turma.get() : null);
        aluno.setCurso(curso.isPresent() ? curso.get() : null);
        aluno.setDt_inicio(alunoDTO.dt_inicio());
        aluno.setDt_fim(alunoDTO.dt_fim());

        return this.repository.save(aluno);

    }

    @Override
    public Aluno editar(AlunoEditarDTO body, String id_aluno) {
        return null;
    }

    @Override
    public Aluno inativar(String id, String id_aluno) {
        return null;
    }

    @Override
    public ResponseDTO login(PessoaLoginRequestDTO body) {
        return null;
    }

    @Override
    public Aluno getById(String idAluno) {
        return null;
    }

    @Override
    public List<Aluno> getByTurma(Long idTurma) {
        return List.of();
    }

    @Override
    public List<Aluno> getByCurso(Long idCurso) {
        return List.of();
    }

    @Override
    public List<Aluno> getByInstituicao(String idInstituicao) {
        return List.of();
    }

}
