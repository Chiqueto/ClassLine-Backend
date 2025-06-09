package com.senai.classline.service.impl;

import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestreId;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.enums.StatusSemestre;
import com.senai.classline.repositories.DisciplinaRepository;
import com.senai.classline.repositories.DisciplinaSemestreRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.repositories.SemestreRepository;
import com.senai.classline.service.DisciplinaSemestreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaSemestreServiceImpl implements DisciplinaSemestreService {

    private final DisciplinaSemestreRepository disciplinaSemestreRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final SemestreRepository semestreRepository;
    private final ProfessorRepository professorRepository;

    // Injeção de dependências
    public DisciplinaSemestreServiceImpl(DisciplinaSemestreRepository disciplinaSemestreRepository, DisciplinaRepository disciplinaRepository, SemestreRepository semestreRepository, ProfessorRepository professorRepository) {
        this.disciplinaSemestreRepository = disciplinaSemestreRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.semestreRepository = semestreRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    @Transactional
    public DisciplinaSemestre addDisciplina(Long id_disciplina, Long id_semestre, String id_professor) {
        DisciplinaSemestreId id = new DisciplinaSemestreId(id_disciplina, id_semestre, id_professor);
        if (disciplinaSemestreRepository.existsById(id)) {
            throw new RuntimeException("Esta disciplina já foi adicionada para este semestre e professor.");
        }

        Disciplina disciplina = disciplinaRepository.findById(id_disciplina)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        Semestre semestre = semestreRepository.findById(id_semestre)
                .orElseThrow(() -> new RuntimeException("Semestre não encontrado."));
        Professor professor = professorRepository.findById(id_professor)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado."));

        DisciplinaSemestre novaEntrada = new DisciplinaSemestre(
                id,
                disciplina,
                semestre,
                professor,
                StatusSemestre.EM_ANDAMENTO
        );

        return disciplinaSemestreRepository.save(novaEntrada);
    }

    @Override
    @Transactional
    public DisciplinaSemestre concludeSemester(Long id_disciplina, Long id_semestre, String id_professor) {
        DisciplinaSemestreId id = new DisciplinaSemestreId(id_disciplina, id_semestre, id_professor);
        DisciplinaSemestre disciplinaSemestre = disciplinaSemestreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        disciplinaSemestre.setStatus(StatusSemestre.CONCLUIDO);
        return disciplinaSemestreRepository.save(disciplinaSemestre);
    }



    @Override
    @Transactional
    public DisciplinaSemestre inactivateSemester(Long id_disciplina, Long id_semestre, String id_professor) {
        DisciplinaSemestreId id = new DisciplinaSemestreId(id_disciplina, id_semestre, id_professor);
        DisciplinaSemestre disciplinaSemestre = disciplinaSemestreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        disciplinaSemestre.setStatus(StatusSemestre.INATIVO);
        return disciplinaSemestreRepository.save(disciplinaSemestre);
    }
//
//    /**
//     * ATENÇÃO: Um método de "edição" genérico geralmente precisa de mais informações
//     * do que apenas os IDs. Por exemplo, "o que você quer editar?".
//     * Um cenário comum seria trocar o professor. Abaixo, um exemplo de como seria
//     * para trocar o professor, que precisaria de um parâmetro a mais.
//     * A sua interface original não pode ser implementada de forma útil.
//     */
//    @Override
//    @Transactional
//    public DisciplinaSemestre editSemester(Long id_disciplina, Long id_semestre, String id_professor_antigo) {
//        // Este método está incompleto pois não sabemos O QUE editar.
//        // Se fosse para trocar o professor, a assinatura do método deveria ser:
//        // editSemester(Long id_disciplina, Long id_semestre, String id_professor_antigo, String id_professor_novo)
//
//        throw new UnsupportedOperationException(
//                "Método de edição não implementado. Especifique o que deve ser alterado (ex: um novo professor)."
//        );
//    }
}
