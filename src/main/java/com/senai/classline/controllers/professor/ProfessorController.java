package com.senai.classline.controllers.professor;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.ProfessorDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.ProfessorService;
import com.senai.classline.service.impl.ProfessorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorRepository professorRepository;
    private final ProfessorServiceImpl professorService;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping("/{id_instituicao}/")
    public ResponseEntity professorRegister(@PathVariable String id_instituicao, @RequestBody ProfessorDTO body) {
        professorService.salvar(body, id_instituicao);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_instituicao}/{id_professor}")
    public ResponseEntity<?> inactiveProfessor(
            @PathVariable String id_instituicao,
            @PathVariable String id_professor
    ) {
        professorService.inativar(id_professor, id_instituicao);
        return ResponseEntity.noContent().build(); // HTTP 204

    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PutMapping("/{id_instituicao}/{id_professor}")
    public ResponseEntity updateProfessor(@PathVariable String id_instituicao, @PathVariable String id_professor) {
        Optional<Professor> professor = this.professorRepository.findById(id_professor);

//		if(professor.isEmpty()){
//			return ResponseEntity.notFound().build();
//		}
//
//		try{
//			Professor entity = professor.get();
//			entity.setStatus(StatusPessoa.INATIVO);
//			entity.setDt_desligamento(
//					Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant())
//			);
//			professorRepository.save(entity);
//
//			return ResponseEntity.noContent().build();
//		} catch (RuntimeException e){
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
        return null;
    }

}
