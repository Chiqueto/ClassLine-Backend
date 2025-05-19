package com.senai.classline.controllers.professor;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.*;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import com.senai.classline.infra.security.professor.ProfessorTokenService;
import com.senai.classline.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("professor/auth")
@RequiredArgsConstructor
public class ProfessorAuthController {
    private final ProfessorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ProfessorTokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody PessoaLoginRequestDTO body) {
        Professor professor = this.repository.findByCpf(body.cpf()).orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
        if(passwordEncoder.matches(body.senha(), professor.getSenha())) {
            String token = this.tokenService.generateToken(professor);
            return ResponseEntity.ok(new ResponseDTO(professor.getNome(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody ProfessorRegisterRequestDTO body) {

        Optional<Professor> professor = this.repository.findByCpf(body.cpf());

        if(professor.isEmpty()) {
            Professor newProfessor = new Professor();
            newProfessor.setId_instituicao(body.id_instituicao());
            newProfessor.setNome(body.nome());
            newProfessor.setSenha(passwordEncoder.encode(body.senha()));
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
            newProfessor.setStatus(body.status());
            newProfessor.setFormacao(body.formacao());
            newProfessor.setArea_atuacao(body.area_atuacao());
            newProfessor.setDiploma(body.diploma());
            newProfessor.setDt_admissao(body.dt_admissao());


            System.out.println(newProfessor);
            this.repository.save(newProfessor);

            String token = this.tokenService.generateToken(newProfessor);
            return ResponseEntity.ok(new ResponseDTO(newProfessor.getNome(), token));

        }
        return ResponseEntity.badRequest().build();

    }
}