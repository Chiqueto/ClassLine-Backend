package com.senai.classline.controllers.avaliacao;

import com.senai.classline.dto.avaliacao.AvaliacaoDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoResponseDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;
import com.senai.classline.dto.frequencia.LancarFrequenciaRequest;
import com.senai.classline.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {
    private final AvaliacaoService service;

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @PostMapping("/disciplina/{idDisciplina}/professor/{idProfessor}/turma/{idTurma}")
    public ResponseEntity<AvaliacaoResponseDTO> criarAvaliacao(
            @PathVariable Long idDisciplina,
            @PathVariable String idProfessor,
            @PathVariable Long idTurma,
            @Valid @RequestBody AvaliacaoDTO body
    ) {
        AvaliacaoResponseDTO response = this.service.criar(body, idDisciplina, idProfessor, idTurma);

        // Retorna o status 201 CREATED, que é semanticamente correto para POST
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/disciplina/{idDisciplina}/professor/{idProfessor}/turma/{idTurma}")
    public ResponseEntity <List<AvaliacaoResponseDTO>> buscarAvaliacoesByProfessor(
            @PathVariable Long idDisciplina,
            @PathVariable String idProfessor,
            @PathVariable Long idTurma){
        List<AvaliacaoResponseDTO> response = this.service.getByProfessorTurmaAndDisciplina( idProfessor, idDisciplina, idTurma);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
