package com.senai.classline.controllers.aluno;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.dto.Aluno.AlunoDTO;
import com.senai.classline.dto.professor.ProfessorDTO;
import com.senai.classline.service.impl.AlunoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {
    private final AlunoServiceImpl service;
    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping()
    public ResponseEntity professorRegister( @RequestBody @Valid AlunoDTO body) {
        this.service.salvar(body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aluno criado com sucesso");
    }
}
