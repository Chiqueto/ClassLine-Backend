package com.senai.classline.controllers.disciplinasemestre;

import com.senai.classline.service.DisciplinaSemestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinasemestre")
@RequiredArgsConstructor
public class DisciplinaSemestre {
    private final DisciplinaSemestreService service;

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/disciplina/{idDisciplina}/semestre/{idSemestre}/professor/{idProfessor}")
    public ResponseEntity<String> addDisciplina(
            @PathVariable Long idDisciplina,
            @PathVariable Long idSemestre,
            @PathVariable String idProfessor) {
        service.addDisciplina(idDisciplina, idSemestre, idProfessor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Disciplina adicionada ao semestre com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PutMapping("/disciplina/{idDisciplina}/semestre/{idSemestre}/professor/{idProfessor}/concluir")
    public ResponseEntity<String> concludeSemester(
            @PathVariable Long idDisciplina,
            @PathVariable Long idSemestre,
            @PathVariable String idProfessor) {
        service.concludeSemester(idDisciplina, idSemestre, idProfessor);
        return ResponseEntity.ok("Disciplina marcada como concluída com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/disciplina/{idDisciplina}/semestre/{idSemestre}/professor/{idProfessor}/inativar")
    public ResponseEntity<String> inactivateSemester(
            @PathVariable Long idDisciplina,
            @PathVariable Long idSemestre,
            @PathVariable String idProfessor) {
        service.inactivateSemester(idDisciplina, idSemestre, idProfessor);
        return ResponseEntity.ok("Disciplina inativada com sucesso!");
    }

}
