package com.senai.classline.controllers.aluno; 

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.dto.Aluno.*;
import com.senai.classline.service.impl.AlunoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/aluno")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoServiceImpl alunoService;

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PostMapping()
    public ResponseEntity<String> alunoRegister(@RequestBody @Valid AlunoDTO body) {
        Aluno alunoSalvo = this.alunoService.salvar(body);
        return ResponseEntity.status(HttpStatus.CREATED).body("Aluno '" + alunoSalvo.getNome() + "' criado com sucesso!");
    }


    @PreAuthorize("hasRole('INSTITUICAO') or (hasRole('ALUNO') and #id_aluno == principal.id)")
    @GetMapping("/{id_aluno}")
    public ResponseEntity<AlunoResponseDTO> getAlunoById(@PathVariable String id_aluno) {
        AlunoResponseDTO aluno = alunoService.getById(id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/instituicao/{id_instituicao}")
    public ResponseEntity<List<AlunoResponseDTO>> getAlunoByInstituicao(@PathVariable String id_instituicao) {
        List<AlunoResponseDTO> alunos = alunoService.getByInstituicao(id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/turma/{id_turma}")
    public ResponseEntity<List<AlunoResponseDTO>> getAlunoByTurma(@PathVariable Long id_turma) {
        List<AlunoResponseDTO> alunos = alunoService.getByTurma(id_turma);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/curso/{id_curso}")
    public ResponseEntity<List<AlunoResponseDTO>> getAlunoByCurso(@PathVariable Long id_curso) {
        List<AlunoResponseDTO> alunos = alunoService.getByCurso(id_curso);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or (hasRole('ALUNO') and #id_aluno == principal.id)")
    @PutMapping("/{id_aluno}")
    public ResponseEntity<AlunoResponseDTO> updateAluno(
            @PathVariable String id_aluno,
            @RequestBody @Valid AlunoEditarDTO alunoEditarDTO) {
        AlunoResponseDTO alunoEditado = this.alunoService.editar(alunoEditarDTO, id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body(alunoEditado);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_aluno}")
    public ResponseEntity<Void> inactiveAluno(@PathVariable String id_aluno) {
        alunoService.inativar(id_aluno);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/disciplina/{id_disciplina}")
    public ResponseEntity<List<AlunoDisciplinaDTO>> getAlunoByDisciplina(@PathVariable Long id_disciplina) {
        List<AlunoDisciplinaDTO> alunos = alunoService.getAlunoByDisciplina(id_disciplina);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('ALUNO') or hasRole('PROFESSOR')")
    @GetMapping("/boletim/{id_aluno}")
    public ResponseEntity<AlunoBoletimDTO> getBoletim(@PathVariable String id_aluno) {
        AlunoBoletimDTO boletim = alunoService.getBoletimByAluno(id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body(boletim);
    }

    @PreAuthorize("hasRole('ALUNO')")
    @GetMapping("/{idAluno}/comparativo-geral")
    public ResponseEntity<Set<ComparativoMediaDisciplinaDTO>> getComparativoGeral(@PathVariable String idAluno) {
        Set<ComparativoMediaDisciplinaDTO> comparativo = alunoService.getComparativoMediasPorDisciplina(idAluno);
        return ResponseEntity.ok(comparativo);
    }


}
