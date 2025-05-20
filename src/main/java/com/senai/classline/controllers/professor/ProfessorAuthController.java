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
            return ResponseEntity.ok(new ResponseDTO(professor.getId_professor(), token));
        }
        return ResponseEntity.badRequest().build();
    }


}