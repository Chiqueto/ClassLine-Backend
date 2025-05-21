package com.senai.classline.controllers.professor;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.*;
import com.senai.classline.enums.UserType;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("professor/auth")
@RequiredArgsConstructor
public class ProfessorAuthController {

    private final ProfessorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody PessoaLoginRequestDTO body) {
        Professor professor = repository.findByCpf(body.cpf())
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        if (passwordEncoder.matches(body.senha(), professor.getSenha())) {
            String token = tokenService.generateToken(professor, UserType.PROFESSOR);
            return ResponseEntity.ok(new ResponseDTO(professor.getId_professor(), token));
        }

        return ResponseEntity.badRequest().build();
    }
}