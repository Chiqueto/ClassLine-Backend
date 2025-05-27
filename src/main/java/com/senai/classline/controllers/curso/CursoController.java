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

}
