package com.senai.classline.controllers.frequencia;

import com.senai.classline.dto.aula.AulaDTO;
import com.senai.classline.dto.frequencia.AlunoFrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;
import com.senai.classline.dto.frequencia.LancarFrequenciaRequest;
import com.senai.classline.service.FrequenciaService;
import com.senai.classline.service.impl.FrequenciaServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/frequencia")
@RequiredArgsConstructor
public class FrequenciaController {
    private final FrequenciaService service;

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')") // Corrigido
    @PostMapping("/disciplina/{idDisciplina}/professor/{idProfessor}")
    public ResponseEntity<List<FrequenciaResponseDTO>> lancarFrequencia(
            @PathVariable Long idDisciplina,
            @PathVariable String idProfessor,
            @RequestBody @Valid LancarFrequenciaRequest request
    ) {
        List<FrequenciaResponseDTO> response = service.lancarFrequencia(
                request.presencas(),
                idDisciplina,
                idProfessor,
                request.aula()
        );

        // Retorna o status 201 CREATED, que é semanticamente correto para POST
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/disciplina/{idDisciplina}/professor/{idProfessor}/data/{data}")
    public ResponseEntity<List<AlunoFrequenciaDTO>> getFrequencia(
            @PathVariable Long idDisciplina,
            @PathVariable String idProfessor,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data
    ) {
        List<AlunoFrequenciaDTO> listaFrequencia = service.getFrequenciaPorDiaDeAula(
                idDisciplina,
                idProfessor,
                data
        );
        return ResponseEntity.ok(listaFrequencia);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @PutMapping("/disciplina/{idDisciplina}/professor/{idProfessor}/data/{data}")
    public ResponseEntity<List<FrequenciaResponseDTO>> putFrequencia(
            @PathVariable Long idDisciplina,
            @PathVariable String idProfessor,
            @RequestBody @Valid LancarFrequenciaRequest body
    ) {
        List<FrequenciaResponseDTO> response = service.editarFrequencia(
                body.presencas(),
                idDisciplina,
                idProfessor,
                body.aula()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
