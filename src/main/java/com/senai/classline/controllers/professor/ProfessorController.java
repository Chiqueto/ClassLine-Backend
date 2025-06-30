package com.senai.classline.controllers.professor;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.professor.ProfessorDTO;
import com.senai.classline.dto.professor.ProfessorEditarDTO;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.impl.ProfessorServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorServiceImpl professorService;
    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/{id_professor}")
    public ResponseEntity<Professor> getProfessorById(@PathVariable String id_professor){
        Professor professor = professorService.getById(id_professor) ;
        return ResponseEntity.status(HttpStatus.OK).body(professor);
    }
    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/instituicao/{id_instituicao}")
    public ResponseEntity<List<Professor>> getProfessorByInstituicao(@PathVariable String id_instituicao){
        List<Professor> professores = professorService.getByInstituicao(id_instituicao) ;
        return ResponseEntity.status(HttpStatus.OK).body(professores);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/{id_instituicao}")
    public ResponseEntity professorRegister(@PathVariable String id_instituicao, @RequestBody @Valid ProfessorDTO body) {
        professorService.salvar(body, id_instituicao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_instituicao}/{id_professor}")
    public ResponseEntity<?> inactiveProfessor(
            @PathVariable String id_instituicao,
            @PathVariable String id_professor
    ) {
        professorService.inativar(id_instituicao, id_professor);
        return ResponseEntity.noContent().build(); 

    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @PutMapping("/{id_instituicao}/{id_professor}")
    public ResponseEntity updateProfessor(@PathVariable String id_professor, @PathVariable String id_instituicao, @RequestBody @Valid ProfessorEditarDTO professorEditarDTO) {
        this.professorService.editar(id_professor, professorEditarDTO, id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body("Professor editado com sucesso!");

    }

}
