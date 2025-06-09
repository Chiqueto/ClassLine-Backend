package com.senai.classline.controllers.disciplina;


import com.senai.classline.dto.disciplina.DisciplinaDTO;
import com.senai.classline.dto.disciplina.DisciplinaResponseDTO;
import com.senai.classline.service.impl.DisciplinaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplina")
@RequiredArgsConstructor
public class DisciplinaController {
    final DisciplinaServiceImpl service;

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/{id_instituicao}")
    public ResponseEntity<DisciplinaResponseDTO> createDisciplina(@PathVariable String id_instituicao, @RequestBody DisciplinaDTO body){
        final DisciplinaResponseDTO disciplina = this.service.criar(id_instituicao, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/instituicao/{id_instituicao}")
    public ResponseEntity<List<DisciplinaResponseDTO>> getDisciplinaByInstituicao(@PathVariable String id_instituicao){
        final List<DisciplinaResponseDTO> disciplinas = this.service.getDisciplinaByInstituicao(id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/{id_disciplina}")
    public ResponseEntity<DisciplinaResponseDTO> getDisciplinaById(@PathVariable Long id_disciplina){
        final DisciplinaResponseDTO disciplina = this.service.getDisciplinaById(id_disciplina);
        return ResponseEntity.status(HttpStatus.OK).body(disciplina);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_disciplina}/{id_instituicao}")
    public ResponseEntity<DisciplinaResponseDTO> inactivateDisciplina(@PathVariable Long id_disciplina, @PathVariable String id_instituicao){
        final DisciplinaResponseDTO disciplina = this.service.inativar(id_disciplina, id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(disciplina);
    }
}
