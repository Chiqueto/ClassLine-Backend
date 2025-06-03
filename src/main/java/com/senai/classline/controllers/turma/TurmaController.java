package com.senai.classline.controllers.turma;

import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.dto.turma.TurmaEditarDTO;
import com.senai.classline.dto.turma.TurmaResponseDTO;
import com.senai.classline.service.impl.TurmaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_turma}")
    public ResponseEntity inativarTurma(@PathVariable Long id_turma){
        this.service.inativar(id_turma);
        return ResponseEntity.status(HttpStatus.OK).body("Turma inativada com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/{id_turma}")
    public ResponseEntity getTurmaById(@PathVariable Long id_turma){
        TurmaResponseDTO turma = this.service.getTurmaById(id_turma);
        return ResponseEntity.status(HttpStatus.OK).body(turma);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/curso/{id_curso}")
    public ResponseEntity getTurmaByCurso(@PathVariable Long id_curso){
        List<Turma> turmas = this.service.GetTurmaByCurso(id_curso);
        return ResponseEntity.status(HttpStatus.OK).body(turmas);
    }
}
