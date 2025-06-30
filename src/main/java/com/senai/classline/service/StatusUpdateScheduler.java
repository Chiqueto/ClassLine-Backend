package com.senai.classline.service;

import com.senai.classline.repositories.DisciplinaSemestreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusUpdateScheduler {

    private final DisciplinaSemestreRepository disciplinaSemestreRepository;

    /**
     * Método agendado para rodar todos os dias às 02:00 da manhã.
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void verificarEAtualizarStatus() {
        log.info("INICIANDO TAREFA AGENDADA: Verificação de status de DisciplinaSemestre...");

        Date hoje = new Date();

        int iniciados = disciplinaSemestreRepository.atualizarStatusParaEmAndamento(hoje);
        if (iniciados > 0) {
            log.info("Tarefa Agendada: {} DisciplinaSemestre tiveram seu status alterado para EM_ANDAMENTO.", iniciados);
        }

        int concluidos = disciplinaSemestreRepository.atualizarStatusParaConcluido(hoje);
        if (concluidos > 0) {
            log.info("Tarefa Agendada: {} DisciplinaSemestre tiveram seu status alterado para CONCLUIDO.", concluidos);
        }

        if (iniciados == 0 && concluidos == 0) {
            log.info("Tarefa Agendada: Nenhuma alteração de status necessária hoje.");
        }

        log.info("TAREFA AGENDADA CONCLUÍDA.");
    }
}
