package com.senai.classline.controllers.nota;

import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;
import com.senai.classline.dto.Nota.NotaDetalhesDTO;
import com.senai.classline.dto.Nota.NotaResponseDTO;
import com.senai.classline.dto.frequencia.FrequenciaResponseDTO;
import com.senai.classline.dto.frequencia.LancarFrequenciaRequest;
import com.senai.classline.service.NotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/nota")
@RequiredArgsConstructor
public class NotaController {
    private final NotaService service;

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @PostMapping("/avaliacao/{idAvaliacao}/notas") // Endpoint corrigido para seguir o padrão REST
    public ResponseEntity<NotaResponseDTO> lancarNotas(
            @PathVariable Long idAvaliacao,
            @RequestBody @Valid Set<NotaDTO> body
    ) {

        NotaResponseDTO resultado = service.salvar(body, idAvaliacao);


        HttpStatus status;

        if (resultado.atualizadas() == 0 && resultado.criadas() > 0) {

            status = HttpStatus.CREATED;
        } else {

            status = HttpStatus.OK;
        }

        return ResponseEntity.status(status).body(resultado);
    }

    @PreAuthorize("hasRole('INSTITUICAO') or hasRole('PROFESSOR')")
    @GetMapping("/avaliacao/{idAvaliacao}")
    public ResponseEntity<Set<NotaDetalhesDTO>> getNotasByAvaliacao(@PathVariable Long idAvaliacao){
        Set<NotaDetalhesDTO> resultado = service.getNotasByAvaliacao(idAvaliacao);

        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }
}
