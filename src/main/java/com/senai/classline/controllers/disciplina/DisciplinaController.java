package com.senai.classline.controllers.disciplina;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.instituicao.Instituicao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplina")
@RequiredArgsConstructor
public class DisciplinaController {
    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/{id_instituicao}")
    public ResponseEntity<Disciplina> createDisciplina(@PathVariable String id_instituicao){
        final DisciplinaResponseDTO disciplina = this.service.createDisciplina(id_instituicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(disciplina);
    }
}
