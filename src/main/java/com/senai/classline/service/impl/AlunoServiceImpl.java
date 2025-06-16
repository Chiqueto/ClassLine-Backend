package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.instituicao.Instituicao; // Assuming Instituicao can be accessed
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.Aluno.AlunoBoletimDTO;
import com.senai.classline.dto.Aluno.AlunoDTO;
import com.senai.classline.dto.Aluno.AlunoEditarDTO;
import com.senai.classline.dto.Aluno.AlunoResponseDTO;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.dto.disciplina.DisciplinaBoletimDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType; // Assuming UserType.ALUNO is defined
import com.senai.classline.exceptions.global.AlreadyExists;
import com.senai.classline.exceptions.global.LoginFail; // Assuming this exception exists
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.exceptions.global.UnauthorizedException; // Generic unauthorized
import com.senai.classline.infra.security.TokenService; // To be injected
import com.senai.classline.repositories.*;
import com.senai.classline.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // To be injected
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlunoServiceImpl implements AlunoService {
    private final DisciplinaRepository disciplinaRepository;
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final InstituicaoRepository instituicaoRepository;
    private final DisciplinaSemestreRepository disciplinaSemestreRepository;

    @Override
    public Aluno salvar(AlunoDTO alunoDTO) {
        Optional<Aluno> alunoExists = this.alunoRepository.findByCpf(alunoDTO.cpf());
        if (alunoExists.isPresent()) {
            throw new AlreadyExists("Aluno com esse CPF já cadastrado");
        }

        Optional<Aluno> emailAlreadyInUse = this.alunoRepository.findByEmail(alunoDTO.email());
        if (emailAlreadyInUse.isPresent()) {
            throw new AlreadyExists("E-mail já em uso!");
        }

        Turma turma = this.turmaRepository.findById(alunoDTO.id_turma())
                .orElseThrow(() -> new NotFoundException("Turma não encontrada com ID: " + alunoDTO.id_turma()));
        Curso curso = this.cursoRepository.findById(alunoDTO.id_curso())
                .orElseThrow(() -> new NotFoundException("Curso não encontrado com ID: " + alunoDTO.id_curso()));



         String idInstituicaoContext = turma.getCurso().getInstituicao().getIdInstituicao();

        Aluno aluno = new Aluno();
        aluno.setNome(alunoDTO.nome());
        aluno.setEmail(alunoDTO.email());
        aluno.setSenha(passwordEncoder.encode(alunoDTO.senha())); // Encode password
        aluno.setCpf(alunoDTO.cpf());
        aluno.setDt_nascimento(alunoDTO.dt_nascimento());
        aluno.setGenero(alunoDTO.genero());
        aluno.setLogradouro(alunoDTO.logradouro());
        aluno.setBairro(alunoDTO.bairro());
        aluno.setNumero(alunoDTO.numero());
        aluno.setCidade(alunoDTO.cidade());
        aluno.setTelefone(alunoDTO.telefone());
        aluno.setTurno(alunoDTO.turno());
        aluno.setStatus(StatusPessoa.ATIVO); // Default to ATIVO on creation
        aluno.setTurma(turma);
        aluno.setCurso(curso);
        aluno.setDt_inicio(alunoDTO.dt_inicio());
        aluno.setDt_fim(alunoDTO.dt_fim());


        return this.alunoRepository.save(aluno);
    }

    @Override
    // MODIFICADO: Retorna AlunoResponseDTO
    public AlunoResponseDTO editar(AlunoEditarDTO body, String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        // ... (lógica de autorização e validação permanece a mesma) ...

        if (body.email() != null && !body.email().equals(aluno.getEmail())) {
            alunoRepository.findByEmail(body.email()).ifPresent(a -> {
                throw new AlreadyExists("E-mail já cadastrado!");
            });
            aluno.setEmail(body.email());
        }

        if (body.nome() != null) aluno.setNome(body.nome());
        if (body.senha() != null && !body.senha().isEmpty()) aluno.setSenha(passwordEncoder.encode(body.senha()));
        if (body.dt_nascimento() != null) aluno.setDt_nascimento(body.dt_nascimento());
        // ... (outras atribuições permanecem as mesmas) ...
        if (body.id_turma() != null) {
            Turma turma = turmaRepository.findById(body.id_turma())
                    .orElseThrow(() -> new NotFoundException("Turma não encontrada com ID: " + body.id_turma()));
            aluno.setTurma(turma);
        }
        if (body.id_curso() != null) {
            Curso curso = cursoRepository.findById(body.id_curso())
                    .orElseThrow(() -> new NotFoundException("Curso não encontrado com ID: " + body.id_curso()));
            aluno.setCurso(curso);
        }

        Aluno alunoEditado = this.alunoRepository.save(aluno);
        return convertToResponseDTO(alunoEditado); // MODIFICADO: Converte para DTO antes de retornar
    }

    @Override
    // MODIFICADO: Retorna AlunoResponseDTO
    public AlunoResponseDTO inativar(String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        // ... (lógica de autorização permanece a mesma) ...

        aluno.setStatus(StatusPessoa.INATIVO);
        aluno.setDt_fim(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));

        Aluno alunoInativado = this.alunoRepository.save(aluno);
        return convertToResponseDTO(alunoInativado); // MODIFICADO: Converte para DTO antes de retornar
    }


    @Override
    public ResponseDTO login(PessoaLoginRequestDTO body) {
        Optional<Aluno> alunoOptional = alunoRepository.findByCpf(body.cpf());

        if (alunoOptional.isEmpty() || !passwordEncoder.matches(body.senha(), alunoOptional.get().getSenha())) {
            throw new LoginFail("Login falhou: CPF ou senha inválidos.");
        }
        if (alunoOptional.get().getStatus() == StatusPessoa.INATIVO) {
            throw new LoginFail("Login falhou: Usuário inativo.");
        }

        Aluno aluno = alunoOptional.get();
        String token = tokenService.generateToken(aluno, UserType.ALUNO); // Assuming UserType.ALUNO exists

        String idInstituicao = null;
        if (aluno.getCurso().getInstituicao() != null) {
            idInstituicao = aluno.getCurso().getInstituicao().getIdInstituicao();
        } else if (aluno.getCurso() != null && aluno.getCurso().getInstituicao() != null) {
            idInstituicao = aluno.getCurso().getInstituicao().getIdInstituicao();
        } else if (aluno.getTurma() != null && aluno.getTurma().getCurso() != null && aluno.getTurma().getCurso().getInstituicao() != null) {
            idInstituicao = aluno.getTurma().getCurso().getInstituicao().getIdInstituicao();
        }
        // If idInstituicao is mandatory for ResponseDTO, and might be null, consider how to handle.
        // For now, it can be null if not found through the chain.

        return new ResponseDTO(aluno.getIdAluno(), token); // Assuming ResponseDTO can take idAluno, idInstituicao, token, userType
    }

    @Override
    // MODIFICADO: Retorna AlunoResponseDTO
    public AlunoResponseDTO getById(String idAluno) {
        Aluno aluno = this.alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + idAluno));
        return convertToResponseDTO(aluno); // MODIFICADO: Converte para DTO
    }

    @Override
    // MODIFICADO: Retorna List<AlunoResponseDTO>
    public List<AlunoResponseDTO> getByTurma(Long idTurma) {
        if (!turmaRepository.existsById(idTurma)) {
            throw new NotFoundException("Turma não encontrada com ID: " + idTurma);
        }
        List<Aluno> alunos = this.alunoRepository.findByTurma_idTurma(idTurma);
        // MODIFICADO: Converte a lista de entidades para uma lista de DTOs
        return alunos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    // MODIFICADO: Retorna List<AlunoResponseDTO>
    public List<AlunoResponseDTO> getByCurso(Long idCurso) {
        if (!cursoRepository.existsById(idCurso)) {
            throw new NotFoundException("Curso não encontrado com ID: " + idCurso);
        }
        List<Aluno> alunos = this.alunoRepository.findByCurso_idCurso(idCurso);
        // MODIFICADO: Converte a lista
        return alunos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    // MODIFICADO: Retorna List<AlunoResponseDTO>
    public List<AlunoResponseDTO> getByInstituicao(String idInstituicao) {
        if (!instituicaoRepository.existsById(idInstituicao)) {
            throw new NotFoundException("Instituição não encontrada com ID: " + idInstituicao);
        }
        List<Curso> cursosDaInstituicao = this.cursoRepository.findByInstituicao_IdInstituicao(idInstituicao);
        if (cursosDaInstituicao.isEmpty()) {
            return Collections.emptyList();
        }

        List<Aluno> todosAlunosDaInstituicao = new ArrayList<>();
        for (Curso curso : cursosDaInstituicao) {
            todosAlunosDaInstituicao.addAll(alunoRepository.findByCurso_idCurso(curso.getIdCurso()));
        }

        // MODIFICADO: Converte a lista
        return todosAlunosDaInstituicao.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    // MODIFICADO: Retorna List<AlunoResponseDTO>
    public List<AlunoResponseDTO> getAlunoByDisciplina(Long disciplinaId) {
        if (!disciplinaRepository.existsById(disciplinaId)) {
            throw new NotFoundException("Disciplina com o ID informado não foi encontrada.");
        }
        List<Aluno> alunos = alunoRepository.findAlunosByDisciplinaId(disciplinaId);
        // MODIFICADO: Converte a lista
        return alunos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AlunoBoletimDTO getBoletimByAluno(String idAluno) {

        Set<DisciplinaSemestre> disciplinasSemestre = disciplinaSemestreRepository.findByAluno(idAluno);
        Set<Disciplina> disciplinasByAluno = disciplinasSemestre.stream()
                .map(DisciplinaSemestre::getDisciplina) // Method reference para o método getDisciplina
                .collect(Collectors.toSet());

        Set<DisciplinaBoletimDTO> disciplinas = disciplinasByAluno.stream().map(
                disciplina -> new DisciplinaBoletimDTO(
                        disciplina.getIdDisciplina(),
                        disciplina.getNome(),
                        avaliacaoRepository.findByAluno()


                )
                ))
        );


        AlunoBoletimDTO boletim = new AlunoBoletimDTO(

        );

        return null;
    }


    private AlunoResponseDTO convertToResponseDTO(Aluno aluno) {
        // Lógica para obter o ID da Instituição de forma segura
        String idInstituicao = (aluno.getCurso() != null && aluno.getCurso().getInstituicao() != null)
                ? aluno.getCurso().getInstituicao().getIdInstituicao()
                : null;

        // Lógica para obter IDs de Turma e Curso de forma segura
        Long idTurma = (aluno.getTurma() != null) ? aluno.getTurma().getIdTurma() : null;
        Long idCurso = (aluno.getCurso() != null) ? aluno.getCurso().getIdCurso() : null;

        return new AlunoResponseDTO(
                aluno.getIdAluno(),
                idInstituicao,
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCpf(),
                aluno.getDt_nascimento(),
                aluno.getGenero(),
                aluno.getLogradouro(),
                aluno.getBairro(),
                aluno.getNumero(),
                aluno.getCidade(),
                aluno.getTelefone(),
                aluno.getTurno(),
                aluno.getStatus(),
                aluno.getDt_inicio(),
                aluno.getDt_fim(),
                idTurma,
                idCurso
        );
    }

}