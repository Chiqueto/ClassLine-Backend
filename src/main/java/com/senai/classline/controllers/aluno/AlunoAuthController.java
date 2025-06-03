package com.senai.classline.controllers.aluno;

import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.AlunoRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.AlunoService;
import com.senai.classline.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("aluno/auth")
@RequiredArgsConstructor
public class AlunoAuthController {

    private final AlunoRepository repository;
    private final TokenService tokenService;
    private final AlunoService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody PessoaLoginRequestDTO body) {
        ResponseDTO response = service.login(body);
        return ResponseEntity.ok(response);
    }
}
