package com.senai.classline.controllers.nota;

import com.senai.classline.domain.nota.Nota;
import com.senai.classline.dto.Nota.NotaDTO;
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
        // 1. Chame o serviço e receba o objeto de resultado completo
        NotaResponseDTO resultado = service.salvar(body, idAvaliacao);

        // 2. (Opcional, mas recomendado) Lógica para escolher o melhor Status HTTP
        HttpStatus status;

        if (resultado.atualizadas() == 0 && resultado.criadas() > 0) {
            // Se NENHUMA nota foi atualizada e pelo menos UMA foi criada,
            // o status 201 Created é o mais apropriado.
            status = HttpStatus.CREATED;
        } else {
            // Se qualquer nota foi atualizada (ou se nada foi feito),
            // o status 200 OK é o mais adequado para uma operação mista.
            status = HttpStatus.OK;
        }

        // 3. Retorne a resposta com o status correto e o corpo do resultado
        return ResponseEntity.status(status).body(resultado);
    }
}
