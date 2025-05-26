package com.senai.classline.service.impl;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ProfessorDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType;
import com.senai.classline.exceptions.global.LoginFail;
import com.senai.classline.exceptions.professor.ProfessorAlreadyExists;
import com.senai.classline.exceptions.professor.ProfessorNotFound;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {
    private final PasswordEncoder professorPasswordEncoder;
    private final ProfessorRepository professorRepository;
    private final TokenService professorTokenService;

    @Override
    public Professor salvar(ProfessorDTO professorDTO, String id_instituicao) {
        Optional<Professor> professor = this.professorRepository.findByCpf(professorDTO.cpf());

        if(professor.isEmpty()) {
            Professor newProfessor = converteDTO(professorDTO);
            newProfessor.setIdInstituicao(id_instituicao);
//            String token = this.professorTokenService.generateToken(newProfessor, UserType.PROFESSOR);
            return this.professorRepository.save(newProfessor);
        }

        throw new ProfessorAlreadyExists();

    }

    @Override
    public Professor editar(Professor professor) {
        return null;
    }

    @Override
    public Professor inativar(String id_professor, String id_instituicao) {
        Professor professor = this.professorRepository.findById(id_professor).get();

        if(professor == null){
            throw  new ProfessorNotFound();
        }

        if (!professor.getIdInstituicao().equals(id_instituicao)) {
            throw new RuntimeException("Você não tem permissão para alterar esse professor");
        }

        professor.setStatus(StatusPessoa.INATIVO);
        professor.setDt_desligamento(
                Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant())
        );

        return professorRepository.save(professor);
    }

    @Override
    public void validar(Professor professor) {

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
        return new ResponseDTO(professor.get().getIdInstituicao(), token);
    }
}


