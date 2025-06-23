package com.senai.classline.service.impl;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.disciplina.DisciplinaDTO;
import com.senai.classline.dto.disciplina.DisciplinaResponseDTO;
import com.senai.classline.exceptions.curso.CursoAlreadyExists;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
import com.senai.classline.repositories.DisciplinaRepository;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.repositories.TurmaRepository;
import com.senai.classline.service.DisciplinaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DisciplinaServiceImpl implements DisciplinaService {
    private final DisciplinaRepository repository;
    private final InstituicaoRepository instituicaoRepository;
    private final TurmaRepository turmaRepository;

    @Override
    public DisciplinaResponseDTO criar(String id_instituicao, DisciplinaDTO body) {
        Optional<Disciplina> curso = this.repository.findByNome(body.nome());
        if (curso.isPresent()) {
            throw new CursoAlreadyExists();
        }
        Optional<Instituicao> instituicaoExists = instituicaoRepository.findByIdInstituicao(id_instituicao);
        if(instituicaoExists.isEmpty()){
            throw new InstituicaoNotFound();
        }

        Disciplina newDisciplina = new Disciplina();
        newDisciplina.setNome(body.nome());
        newDisciplina.setInstituicao(instituicaoExists.get());
        newDisciplina.setCarga_horaria(body.carga_horaria());
        newDisciplina.setStatus(true);


        this.repository.save(newDisciplina);
        DisciplinaResponseDTO disciplina =  new DisciplinaResponseDTO(
                newDisciplina.getIdDisciplina(),
                newDisciplina.getNome(),
                newDisciplina.getCarga_horaria(),
                newDisciplina.isStatus(),
                newDisciplina.getInstituicao().getIdInstituicao()
        );
        return (disciplina);
    }

    @Override
    public DisciplinaResponseDTO inativar(Long id_disciplina, String id_instituicao) {
        Instituicao instituicao = instituicaoRepository.findByIdInstituicao(id_instituicao)
                .orElseThrow(InstituicaoNotFound::new);

        Disciplina disciplina = repository.findByIdDisciplinaAndInstituicao(id_disciplina, instituicao)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada ou não pertence a esta instituição."));

        disciplina.setStatus(false);
        repository.save(disciplina);

        return toResponseDTO(disciplina);
    }

    @Override
    public DisciplinaResponseDTO getDisciplinaById(Long id_disciplina) {
        Disciplina disciplina = repository.findById(id_disciplina)
                .orElseThrow(() -> new NotFoundException("Disciplina com o ID informado não foi encontrada."));

        return toResponseDTO(disciplina);
    }

    @Override
    public List<DisciplinaResponseDTO> getDisciplinaByInstituicao(String id_instituicao) {
        Instituicao instituicao = instituicaoRepository.findByIdInstituicao(id_instituicao)
                .orElseThrow(InstituicaoNotFound::new);

        List<Disciplina> disciplinas = repository.findAllByInstituicao(instituicao);

        return disciplinas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DisciplinaResponseDTO> getDisciplinaByTurma(Long id_turma) {
        Turma turma = turmaRepository.findById(id_turma)
                .orElseThrow(() -> new NotFoundException("Turma não encontrada"));

        List<Disciplina> disciplinas = repository.findDisciplinasByTurmaId(id_turma);

        return disciplinas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private DisciplinaResponseDTO toResponseDTO(Disciplina disciplina) {
        return new DisciplinaResponseDTO(
                disciplina.getIdDisciplina(),
                disciplina.getNome(),
                disciplina.getCarga_horaria(),
                disciplina.isStatus(),
                disciplina.getInstituicao().getIdInstituicao()
        );
    }
    @Override
    @Transactional(readOnly = true)
    public List<DisciplinaResponseDTO> buscarPorProfessor(String idProfessor) {
        List<Disciplina> disciplinas = repository.findDistinctByDisciplinasSemestreProfessorIdProfessor(idProfessor);

        // 2. Converte a lista de Entidades para uma lista de DTOs usando um método de mapeamento.
        return disciplinas.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList()); // Usando toList() ou Collectors.toList()
    }


}
