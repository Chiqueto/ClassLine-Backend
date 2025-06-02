package com.senai.classline.controllers.aluno; // Pacote ajustado para aluno

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.dto.Aluno.AlunoDTO;
import com.senai.classline.dto.Aluno.AlunoEditarDTO;
// Se você tiver um AlunoResponseDTO específico para o getById, pode usá-lo.
 import com.senai.classline.dto.Aluno.AlunoResponseDTO;
import com.senai.classline.service.impl.AlunoServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Aluno> getAlunoById(@PathVariable String id_aluno) {
        Aluno aluno = alunoService.getById(id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body(aluno);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @GetMapping("/instituicao/{id_instituicao}")
    public ResponseEntity<List<Aluno>> getAlunoByInstituicao(@PathVariable String id_instituicao) {
        List<Aluno> alunos = alunoService.getByInstituicao(id_instituicao);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/turma/{id_turma}")
    public ResponseEntity<List<Aluno>> getAlunoByTurma(@PathVariable Long id_turma) {
        List<Aluno> alunos = alunoService.getByTurma(id_turma);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/curso/{id_curso}")
    public ResponseEntity<List<Aluno>> getAlunoByCurso(@PathVariable Long id_curso) {
        List<Aluno> alunos = alunoService.getByCurso(id_curso);
        return ResponseEntity.status(HttpStatus.OK).body(alunos);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or (hasRole('ALUNO') and #id_aluno == principal.id)")
    @PutMapping("/{id_aluno}")
    public ResponseEntity<String> updateAluno(
            @PathVariable String id_aluno,
            @RequestBody @Valid AlunoEditarDTO alunoEditarDTO) {
        Aluno alunoEditado = this.alunoService.editar(alunoEditarDTO, id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body("Aluno '" + alunoEditado.getNome() + "' editado com sucesso!");
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @DeleteMapping("/{id_aluno}")
    public ResponseEntity<Void> inactiveAluno(@PathVariable String id_aluno) {
        alunoService.inativar(id_aluno);
        return ResponseEntity.noContent().build();
    }
}