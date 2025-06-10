package com.senai.classline.service.impl;

import com.senai.classline.domain.aula.Aula;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.aula.AulaDTO;
import com.senai.classline.dto.aula.AulaResponseDTO;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.AulaRepository;
import com.senai.classline.repositories.DisciplinaRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AulaServiceImpl implements AulaService {
    final AulaRepository aulaRepository;
    final ProfessorRepository professorRepository;
    final DisciplinaRepository disciplinaRepository;

    @Override
    public AulaResponseDTO criar(String idProfessor, Long idDisciplina, AulaDTO body) {
        Professor professor = this.professorRepository.findById(idProfessor)
                .orElseThrow(() -> new NotFoundException("Professor não encontrado com ID: " + idProfessor));

        Disciplina disciplina = this.disciplinaRepository.findById(idDisciplina)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com ID: " + idDisciplina));

        Aula newAula = new Aula();
        newAula.setProfessor(professor);
        newAula.setDisciplina(disciplina);
        newAula.setData(body.data());
        newAula.setConteudo(body.conteudo());

        Aula aula = aulaRepository.save(newAula);

        return convertToResponseDTO(aula);
    }

    private AulaResponseDTO convertToResponseDTO(Aula aula) {
        // Extrai os IDs dos objetos relacionados de forma segura
        String idProfessor = (aula.getProfessor() != null) ? aula.getProfessor().getIdProfessor() : null;
        Long idDisciplina = (aula.getDisciplina() != null) ? aula.getDisciplina().getIdDisciplina() : null;

        return new AulaResponseDTO(
                aula.getIdAula(),      // Obtém o ID da própria aula
                idProfessor,
                idDisciplina,
                aula.getData(),
                aula.getConteudo()
        );
    }
}
