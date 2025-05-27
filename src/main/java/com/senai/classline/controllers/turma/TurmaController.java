package com.senai.classline.controllers.turma;

import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;
import com.senai.classline.service.impl.TurmaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turma")
@RequiredArgsConstructor
public class TurmaController {
    private final TurmaServiceImpl service;

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/{id_curso}")
    public ResponseEntity criar(@PathVariable Long id_curso, @RequestBody @Valid TurmaDTO body){
        this.service.criar(id_curso, body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Turma criada com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PutMapping("/{id_turma}")
    public ResponseEntity editarTurma(@PathVariable Long id_turma, @RequestBody TurmaEditarDTO body){
        this.service.editar(id_turma, body);
        return ResponseEntity.status(HttpStatus.OK).body("Turma editada com sucesso!");
    }
}
