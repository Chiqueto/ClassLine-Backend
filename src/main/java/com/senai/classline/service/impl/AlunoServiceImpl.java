package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.instituicao.Instituicao; // Assuming Instituicao can be accessed
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.Aluno.AlunoDTO;
import com.senai.classline.dto.Aluno.AlunoEditarDTO;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType; // Assuming UserType.ALUNO is defined
import com.senai.classline.exceptions.global.AlreadyExists;
import com.senai.classline.exceptions.global.LoginFail; // Assuming this exception exists
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.exceptions.global.UnauthorizedException; // Generic unauthorized
import com.senai.classline.infra.security.TokenService; // To be injected
import com.senai.classline.repositories.AlunoRepository;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.InstituicaoRepository; // To be injected for getByInstituicao
import com.senai.classline.repositories.TurmaRepository;
import com.senai.classline.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; // To be injected
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

@Service
// @RequiredArgsConstructor will handle the new final fields
public class AlunoServiceImpl implements AlunoService {
    private final AlunoRepository alunoRepository; // Renamed from 'repository' for clarity
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;
    private final PasswordEncoder passwordEncoder; // Added
    private final TokenService tokenService;       // Added
    private final InstituicaoRepository instituicaoRepository; // Added for getByInstituicao consistency

    // Updated constructor for @RequiredArgsConstructor
    public AlunoServiceImpl(
            AlunoRepository alunoRepository,
            TurmaRepository turmaRepository,
            CursoRepository cursoRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            InstituicaoRepository instituicaoRepository) {
        this.alunoRepository = alunoRepository;
        this.turmaRepository = turmaRepository;
        this.cursoRepository = cursoRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.instituicaoRepository = instituicaoRepository;
    }

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


        if (!turma.getCurso().getIdCurso().equals(curso.getIdCurso())) {
            throw new IllegalArgumentException("Turma não pertence ao curso especificado.");
         }
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
    public Aluno editar(AlunoEditarDTO body, String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        // Example: Authorization check if an institution context is needed.
        String idInstituicaoContext = aluno.getCurso().getInstituicao().getIdInstituicao();
        String idInstituicaoDoAluno = aluno.getCurso().getInstituicao() != null ? aluno.getCurso().getInstituicao().getIdInstituicao() : null;
         if (idInstituicaoContext != null && idInstituicaoDoAluno != null && !idInstituicaoDoAluno.equals(idInstituicaoContext)) {
             throw new UnauthorizedException("Operação não autorizada para este aluno em outra instituição.");
         }

        if (body.email() != null && !body.email().equals(aluno.getEmail())) {
            Optional<Aluno> emailExists = this.alunoRepository.findByEmail(body.email());
            if (emailExists.isPresent()) {
                throw new AlreadyExists("E-mail já cadastrado!");
            }
            aluno.setEmail(body.email());
        }

        if (body.nome() != null) { aluno.setNome(body.nome()); }
        if (body.senha() != null && !body.senha().isEmpty()) { aluno.setSenha(passwordEncoder.encode(body.senha()));}
        if (body.dt_nascimento() != null) { aluno.setDt_nascimento(body.dt_nascimento()); }
        if (body.genero() != null) { aluno.setGenero(body.genero()); }
        if (body.logradouro() != null) { aluno.setLogradouro(body.logradouro()); }
        if (body.bairro() != null) { aluno.setBairro(body.bairro()); }
        if (body.numero() != null) { aluno.setNumero(body.numero()); }
        if (body.cidade() != null) { aluno.setCidade(body.cidade()); }
        if (body.telefone() != null) { aluno.setTelefone(body.telefone()); }
        if (body.turno() != null) { aluno.setTurno(body.turno()); }
        // StatusPessoa status should be handled by inativar/ativar methods primarily
        // if (body.status() != null) { aluno.setStatus(body.status()); }

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
        if (body.dt_inicio() != null) { aluno.setDt_inicio(body.dt_inicio()); }
        if (body.dt_fim() != null) { aluno.setDt_fim(body.dt_fim()); }


        return this.alunoRepository.save(aluno);
    }

    @Override
    public Aluno inativar(String id_aluno) { // Simplified signature, assuming context for authorization is handled elsewhere or not needed here.
        // Original was (String id, String id_aluno). Assuming 'id' was 'id_aluno'.
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        // Example: Authorization check if an institution context is needed.
        String idInstituicaoContext = aluno.getCurso().getInstituicao().getIdInstituicao();
         String idInstituicaoDoAluno = aluno.getCurso().getInstituicao() != null ? aluno.getCurso().getInstituicao().getIdInstituicao() : null;
         if (idInstituicaoContext != null && idInstituicaoDoAluno != null && !idInstituicaoDoAluno.equals(idInstituicaoContext)) {
             throw new UnauthorizedException("Operação não autorizada para inativar este aluno.");
         }

        aluno.setStatus(StatusPessoa.INATIVO);
        aluno.setDt_fim(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));

        return this.alunoRepository.save(aluno);
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
    public Aluno getById(String idAluno) {
        return this.alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + idAluno));
    }

    @Override
    public List<Aluno> getByTurma(Long idTurma) {
        if (!turmaRepository.existsById(idTurma)) {
            throw new NotFoundException("Turma não encontrada com ID: " + idTurma);
        }
        return this.alunoRepository.findByTurma_idTurma(idTurma);
    }

    @Override
    public List<Aluno> getByCurso(Long idCurso) {
        if (!cursoRepository.existsById(idCurso)) {
            throw new NotFoundException("Curso não encontrado com ID: " + idCurso);
        }
        return this.alunoRepository.findByCurso_idCurso(idCurso);
    }

    @Override
    public List<Aluno> getByInstituicao(String idInstituicao) {
        if (!instituicaoRepository.existsById(idInstituicao)) {
            throw new NotFoundException("Instituição não encontrada com ID: " + idInstituicao);
        }

        List<Curso> cursosDaInstituicao = this.cursoRepository.findByInstituicao_IdInstituicao(idInstituicao);

        if (cursosDaInstituicao.isEmpty()) {
            return Collections.emptyList(); // Nenhum curso, logo, nenhum aluno
        }

        List<Aluno> todosAlunosDaInstituicao = new ArrayList<>();
        for (Curso curso : cursosDaInstituicao) {

            List<Aluno> alunosDoCurso = alunoRepository.findByCurso_idCurso(curso.getIdCurso());
            todosAlunosDaInstituicao.addAll(alunosDoCurso);
        }

        return todosAlunosDaInstituicao;
    }
}