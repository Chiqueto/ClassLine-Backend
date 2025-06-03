package com.senai.classline.service.impl;

import com.senai.classline.domain.grade.Grade;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.dto.grade.GradeResponseDTO;
import com.senai.classline.dto.semestre.SemestreResponseDTO;
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.repositories.GradeRepository;
import com.senai.classline.repositories.SemestreRepository;
import com.senai.classline.service.SemestreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SemestreServiceImpl implements SemestreService {
    final SemestreRepository repository;
    final GradeRepository gradeReposiory;

    @Override
    @Transactional
    public List<SemestreResponseDTO> getSemestreByGrade(Long idGrade) {
        Optional<Grade> gradeExists = gradeReposiory.findById(idGrade);

        if(gradeExists.isEmpty()){
            throw new NotFoundException("Grade Curricular Não Encontrada");
        }

        List<Semestre> semestres = repository.findByGrade_idGrade(idGrade);
        List<SemestreResponseDTO> semestresDTO = new ArrayList<>(); // Alteração aqui
        for(Semestre semestre : semestres){
            final GradeResponseDTO newGrade = new GradeResponseDTO(
                    semestre.getGrade().getIdGrade(),
                    semestre.getGrade().getDescricao()
            );
            SemestreResponseDTO newSemestre = new SemestreResponseDTO(
                    semestre.getIdSemestre(),
                    newGrade,
                    semestre.getSemestre()
            );
            semestresDTO.add(newSemestre);
        }
        System.out.println(semestresDTO);
        return semestresDTO;
    }
}
