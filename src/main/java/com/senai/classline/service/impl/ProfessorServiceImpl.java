package com.senai.classline.service.impl;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.professor.ProfessorDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.dto.professor.ProfessorEditarDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType;
import com.senai.classline.exceptions.global.LoginFail;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.exceptions.professor.ProfessorAlreadyExists;
import com.senai.classline.exceptions.professor.ProfessorChangeUnauthorized;
import com.senai.classline.exceptions.professor.ProfessorNotFound;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final PasswordEncoder professorPasswordEncoder;
    private final ProfessorRepository professorRepository;
    private final TokenService professorTokenService;
    private final InstituicaoRepository instituicaoRepository;

    @Override
    public Professor salvar(ProfessorDTO professorDTO, String id_instituicao) {
        Optional<Professor> professor = this.professorRepository.findByCpf(professorDTO.cpf());

        if(professor.isEmpty()) {
            Professor newProfessor = converteDTO(professorDTO);
            Optional<Instituicao> instituicaoExists = instituicaoRepository.findByIdInstituicao(id_instituicao);
            if(instituicaoExists.isEmpty()){
                throw new InstituicaoNotFound();
            }
            newProfessor.setInstituicao(instituicaoExists.get());
            return this.professorRepository.save(newProfessor);
        }

        throw new ProfessorAlreadyExists();

    }

    @Override
    public Professor editar(String id_professor, ProfessorEditarDTO body, String id_instituicao) {
        Optional<Professor> professorExists = this.professorRepository.findById(id_professor);
        if(professorExists.isEmpty()) {
            throw new ProfessorNotFound();
        }

        Professor professor = professorExists.get();

        if (!professor.getInstituicao().getIdInstituicao().equals(id_instituicao)) {
            throw new ProfessorChangeUnauthorized();
        }

        if(body.email() != null && professorRepository.findByEmail(body.email()).isPresent()){
            throw new ProfessorAlreadyExists("Professor já cadastrado com esse e-mail!");
        }else{professor.setEmail(body.email());}
        if(body.bairro() != null){ professor.setBairro(body.bairro());}
        if(body.area_atuacao() != null){ professor.setArea_atuacao(body.area_atuacao());}
        if(body.cidade() != null){ professor.setCidade(body.cidade());}
        if(body.dt_admissao() != null){ professor.setDt_admissao(body.dt_admissao());}
        if(body.formacao() != null){ professor.setFormacao(body.formacao());}
        if(body.genero() != null){ professor.setGenero(body.genero());}
        if(body.logradouro() != null){ professor.setLogradouro(body.logradouro());}
        if(body.numero() != null){ professor.setNumero(body.numero());}
        if(body.senha() != null){ professorPasswordEncoder.encode(body.senha());}
        if(body.telefone() != null){ professor.setTelefone(body.telefone());}
        if(body.turno() != null){ professor.setTurno(body.turno());}

        return professorRepository.save(professor);
    }

    @Override
    public Professor inativar(String id_professor, String id_instituicao) {
        Optional<Professor> professorExists = this.professorRepository.findById(id_professor);
        if(professorExists.isEmpty()) {
            throw new ProfessorNotFound();
        }

        Professor professor = professorExists.get();

        if (!professor.getInstituicao().getIdInstituicao().equals(id_instituicao)) {
            throw new ProfessorChangeUnauthorized();
        }

        professor.setStatus(StatusPessoa.INATIVO);
        professor.setDt_desligamento(
                Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant())
        );

        return professorRepository.save(professor);
    }


    @Override
    public Professor converteDTO(ProfessorDTO body) {
        Professor newProfessor = new Professor();
        newProfessor.setNome(body.nome());
        newProfessor.setSenha(professorPasswordEncoder.encode(body.senha()));
        newProfessor.setEmail(body.email());
        newProfessor.setCpf(body.cpf());
        newProfessor.setDt_nascimento(body.dt_nascimento());
        newProfessor.setGenero(body.genero());
        newProfessor.setTelefone(body.telefone());
        newProfessor.setCidade(body.cidade());
        newProfessor.setBairro(body.bairro());
        newProfessor.setLogradouro(body.logradouro());
        newProfessor.setNumero(body.numero());
        newProfessor.setTurno(body.turno());
        newProfessor.setStatus(StatusPessoa.ATIVO);
        newProfessor.setFormacao(body.formacao());
        newProfessor.setArea_atuacao(body.area_atuacao());
        newProfessor.setDiploma(body.diploma());
        newProfessor.setDt_admissao(body.dt_admissao());

        return newProfessor;
    }

    @Override
    public ResponseDTO login(PessoaLoginRequestDTO body) {
        Optional<Professor> professor = professorRepository.findByCpf(body.cpf());

        if (professor.isEmpty() || !professorPasswordEncoder.matches(body.senha(), professor.get().getSenha())){
            throw new LoginFail();
        }

        String token = professorTokenService.generateToken(professor.get(), UserType.PROFESSOR);
        return new ResponseDTO(professor.get().getIdProfessor(), token);
    }

    @Override
    public Professor getById(String idProfessor) {
         Optional<Professor> professor = professorRepository.findById(idProfessor);

         if (professor.isEmpty()) {
             throw new ProfessorNotFound();
         }

         return professor.get();

    }

    @Override
    public List<Professor> getByInstituicao(String idInstituicao) {
        Optional<Instituicao> instituicao = instituicaoRepository.findById(idInstituicao);

        if(instituicao.isEmpty()){
            throw new InstituicaoNotFound();
        }

        return professorRepository.findByInstituicao_idInstituicao(idInstituicao);
    }
}


