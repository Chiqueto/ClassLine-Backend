package com.senai.classline.controllers.disciplinasemestre;

import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.dto.Aluno.AlunoDesempenhoDTO;
import com.senai.classline.dto.disciplinaSemestre.DisciplinaSemestreResponseDTO;
import com.senai.classline.dto.disciplinaSemestre.TrocarProfessorDTO;
import com.senai.classline.service.DisciplinaSemestreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/disciplinasemestre")
@RequiredArgsConstructor
public class DisciplinaSemestreController {
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

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/turma/{idTurma}")
    public ResponseEntity<List<DisciplinaSemestreResponseDTO>> buscarPorTurma(@PathVariable Long idTurma) {
        List<DisciplinaSemestreResponseDTO> gradeDaTurma = service.getGradeByTurma(idTurma);
        return ResponseEntity.ok(gradeDaTurma);
    }

    @PreAuthorize("hasRole('ALUNO')")
    @GetMapping("/aluno/{id_aluno}")
    public ResponseEntity<Set<DisciplinaSemestreResponseDTO>> getDisciplinasSemestreByAluno(@PathVariable String id_aluno) {
        Set<DisciplinaSemestreResponseDTO> response = service.getDisciplinasSemestreByAluno(id_aluno);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('INSTITUICAO')")
    @PutMapping("/trocar-professor")
    public ResponseEntity<DisciplinaSemestreResponseDTO> trocarProfessor(@RequestBody @Valid TrocarProfessorDTO dto) {
        DisciplinaSemestreResponseDTO response = service.trocarProfessor(dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/desempenho-alunos/disciplina/{idDisciplina}/semestre/{idSemestre}/professor/{idProfessor}")
    public ResponseEntity<List<AlunoDesempenhoDTO>> getDesempenhoAlunos(
            @RequestParam Long idDisciplina,
            @RequestParam Long idSemestre,
            @RequestParam String idProfessor) {

        List<AlunoDesempenhoDTO> desempenho = service.getDesempenhoAlunosPorTurma(
                idDisciplina,
                idSemestre,
                idProfessor
        );

        return ResponseEntity.status(HttpStatus.OK).body(desempenho);
    }
}
