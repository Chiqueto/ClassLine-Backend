package com.senai.classline.controllers.nota;

import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;
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
    @PostMapping("")
    public ResponseEntity<Set<Nota>> lancarNotas(
            @RequestBody @Valid Set<NotaDTO> body
    ) {
        Set<Nota> response = service.salvar(body);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
