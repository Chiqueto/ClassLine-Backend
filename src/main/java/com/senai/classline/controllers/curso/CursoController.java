package com.senai.classline.controllers.curso;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.curso.CursoDTO;
import com.senai.classline.dto.curso.CursoEditarDTO;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.service.impl.CursoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/curso")
@RequiredArgsConstructor
public class CursoController {
    private final CursoServiceImpl service;

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/{id_instituicao}")
    public ResponseEntity cursoRegister(@RequestBody @Valid CursoDTO body, @PathVariable String id_instituicao) {
        this.service.criar(id_instituicao, body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Curso criado com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PutMapping("/{id_instituicao}/{id_curso}")
    public ResponseEntity cursoEdit(@RequestBody @Valid CursoEditarDTO body, @PathVariable String id_instituicao, @PathVariable Long id_curso){
        this.service.editar(id_curso, body, id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body("Curso editado com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_instituicao}/{id_curso}")
    public ResponseEntity cursoInactive( @PathVariable String id_instituicao, @PathVariable Long id_curso){
        this.service.inativar(id_curso, id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body("Curso Inativado com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/instituicao/{id_instituicao}")
    public ResponseEntity getCursosByInsituicao (@PathVariable String id_instituicao){
        List<Curso> cursos = this.service.getCursosByInstituicao(id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(cursos);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/{id_curso}")
    public ResponseEntity getCursoById (@PathVariable Long id_curso){
        Curso curso = this.service.getCursoById(id_curso);
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }

}
