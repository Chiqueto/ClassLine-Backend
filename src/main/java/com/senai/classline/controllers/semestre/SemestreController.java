package com.senai.classline.controllers.semestre;

import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.dto.semestre.SemestreResponseDTO;
import com.senai.classline.dto.turma.TurmaDTO;
import com.senai.classline.service.impl.SemestreServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semestre")
@RequiredArgsConstructor
public class SemestreController {
    final SemestreServiceImpl service;

    @GetMapping("/{id_grade}")
    @PreAuthorize("hasRole('INSTITUICAO')")
    public ResponseEntity getByGrade(@PathVariable Long id_grade){
        List<SemestreResponseDTO> semestres = this.service.getSemestreByGrade(id_grade);
        return ResponseEntity.status(HttpStatus.OK).body(semestres);
    }
}
