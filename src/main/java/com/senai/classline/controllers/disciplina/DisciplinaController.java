package com.senai.classline.controllers.disciplina;


import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.dto.disciplina.DisciplinaDTO;
import com.senai.classline.dto.disciplina.DisciplinaResponseDTO;
import com.senai.classline.service.impl.DisciplinaServiceImpl;
import jakarta.validation.Valid;
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
    public ResponseEntity<DisciplinaResponseDTO> createDisciplina(@PathVariable String id_instituicao, @RequestBody @Valid DisciplinaDTO body){
        final DisciplinaResponseDTO disciplina = this.service.criar(id_instituicao, body);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/instituicao/{id_instituicao}")
    public ResponseEntity<List<DisciplinaResponseDTO>> getDisciplinaByInstituicao(@PathVariable String id_instituicao){
        final List<DisciplinaResponseDTO> disciplinas = this.service.getDisciplinaByInstituicao(id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('ALUNO')")
    @GetMapping("/{id_disciplina}")
    public ResponseEntity<DisciplinaResponseDTO> getDisciplinaById(@PathVariable Long id_disciplina){
        final DisciplinaResponseDTO disciplina = this.service.getDisciplinaById(id_disciplina);
        return ResponseEntity.status(HttpStatus.OK).body(disciplina);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/turma/{id_turma}")
    public ResponseEntity<List<DisciplinaResponseDTO>> getDisciplinaByTurma(@PathVariable Long id_turma){
        final List<DisciplinaResponseDTO> disciplinas = this.service.getDisciplinaByTurma(id_turma);
        return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_disciplina}/{id_instituicao}")
    public ResponseEntity<DisciplinaResponseDTO> inactivateDisciplina(@PathVariable Long id_disciplina, @PathVariable String id_instituicao){
        final DisciplinaResponseDTO disciplina = this.service.inativar(id_disciplina, id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(disciplina);
    }

    @PreAuthorize("hasRole('PROFESSOR')")
    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<DisciplinaResponseDTO>> getDisciplinasByProfessor(@PathVariable String idProfessor) {
        List<DisciplinaResponseDTO> disciplinas = service.buscarPorProfessor(idProfessor);

        if (disciplinas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(disciplinas);
    }
}
