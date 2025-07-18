package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.frequencia.Frequencia;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.nota.Nota;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.semestre.Semestre;
import com.senai.classline.domain.turma.Turma;
import com.senai.classline.dto.Aluno.*;
import com.senai.classline.dto.Nota.NotaBoletimDTO;
import com.senai.classline.dto.PessoaLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.dto.avaliacao.AvaliacaoBoletimDTO;
import com.senai.classline.dto.disciplina.DisciplinaBoletimDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType; 
import com.senai.classline.exceptions.global.AlreadyExists;
import com.senai.classline.exceptions.global.LoginFail; 
import com.senai.classline.exceptions.global.NotFoundException;
import com.senai.classline.exceptions.global.UnauthorizedException;
import com.senai.classline.infra.security.TokenService; 
import com.senai.classline.repositories.*;
import com.senai.classline.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
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
    private final NotaRepository notaRepository;
    private final FrequenciaRepository frequenciaRepository;

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
        aluno.setSenha(passwordEncoder.encode(alunoDTO.senha()));
        aluno.setCpf(alunoDTO.cpf());
        aluno.setDt_nascimento(alunoDTO.dt_nascimento());
        aluno.setGenero(alunoDTO.genero());
        aluno.setLogradouro(alunoDTO.logradouro());
        aluno.setBairro(alunoDTO.bairro());
        aluno.setNumero(alunoDTO.numero());
        aluno.setCidade(alunoDTO.cidade());
        aluno.setTelefone(alunoDTO.telefone());
        aluno.setTurno(alunoDTO.turno());
        aluno.setStatus(StatusPessoa.ATIVO);
        aluno.setTurma(turma);
        aluno.setCurso(curso);
        aluno.setDt_inicio(alunoDTO.dt_inicio());
        aluno.setDt_fim(alunoDTO.dt_fim());


        return this.alunoRepository.save(aluno);
    }

    @Override
    @Transactional
    public AlunoResponseDTO editar(AlunoEditarDTO body, String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));
        if (body.nome() != null && !body.nome().isBlank()) {
            aluno.setNome(body.nome());
        }
        if (body.email() != null && !body.email().equals(aluno.getEmail())) {
            alunoRepository.findByEmail(body.email()).ifPresent(a -> {
                if (!a.getIdAluno().equals(aluno.getIdAluno())) {
                    throw new AlreadyExists("E-mail já cadastrado!");
                }
            });
            aluno.setEmail(body.email());
        }
        if (body.senha() != null && !body.senha().isBlank()) {
            aluno.setSenha(passwordEncoder.encode(body.senha()));
        }
        if (body.cpf() != null) {
            aluno.setCpf(body.cpf());
        }
        if (body.dt_nascimento() != null) {
            aluno.setDt_nascimento(body.dt_nascimento());
        }
        if (body.genero() != null) {
            aluno.setGenero(body.genero());
        }
        if (body.telefone() != null) {
            aluno.setTelefone(body.telefone());
        }

        if (body.logradouro() != null) aluno.setLogradouro(body.logradouro());
        if (body.bairro() != null) aluno.setBairro(body.bairro());
        if (body.numero() != null) aluno.setNumero(body.numero());
        if (body.cidade() != null) aluno.setCidade(body.cidade());

        if (body.turno() != null) {
            aluno.setTurno(body.turno());
        }
        if (body.status() != null) {
            aluno.setStatus(body.status());
        }
        if (body.dt_inicio() != null) {
            aluno.setDt_inicio(body.dt_inicio());
        }
        if (body.dt_fim() != null) {
            aluno.setDt_fim(body.dt_fim());
        }

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

        return convertToResponseDTO(alunoEditado);
    }

    @Override
    public AlunoResponseDTO inativar(String id_aluno) {
        Aluno aluno = this.alunoRepository.findById(id_aluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + id_aluno));

        aluno.setStatus(StatusPessoa.INATIVO);
        aluno.setDt_fim(Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant()));

        Aluno alunoInativado = this.alunoRepository.save(aluno);
        return convertToResponseDTO(alunoInativado);
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
        String token = tokenService.generateToken(aluno, UserType.ALUNO); 

        String idInstituicao = null;
        if (aluno.getCurso().getInstituicao() != null) {
            idInstituicao = aluno.getCurso().getInstituicao().getIdInstituicao();
        } else if (aluno.getCurso() != null && aluno.getCurso().getInstituicao() != null) {
            idInstituicao = aluno.getCurso().getInstituicao().getIdInstituicao();
        } else if (aluno.getTurma() != null && aluno.getTurma().getCurso() != null && aluno.getTurma().getCurso().getInstituicao() != null) {
            idInstituicao = aluno.getTurma().getCurso().getInstituicao().getIdInstituicao();
        }

        return new ResponseDTO(aluno.getIdAluno(), token); 
    }

    @Override
    public AlunoResponseDTO getById(String idAluno) {
        Aluno aluno = this.alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + idAluno));
        return convertToResponseDTO(aluno);
    }

    @Override
    public List<AlunoResponseDTO> getByTurma(Long idTurma) {
        if (!turmaRepository.existsById(idTurma)) {
            throw new NotFoundException("Turma não encontrada com ID: " + idTurma);
        }
        List<Aluno> alunos = this.alunoRepository.findByTurma_idTurma(idTurma);
        return alunos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlunoResponseDTO> getByCurso(Long idCurso) {
        if (!cursoRepository.existsById(idCurso)) {
            throw new NotFoundException("Curso não encontrado com ID: " + idCurso);
        }
        List<Aluno> alunos = this.alunoRepository.findByCurso_idCurso(idCurso);
        return alunos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
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

        return todosAlunosDaInstituicao.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<AlunoDisciplinaDTO> getAlunoByDisciplina(Long disciplinaId) {

        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new NotFoundException("Disciplina com o ID informado não foi encontrada."));
        List<Aluno> alunos = alunoRepository.findAlunosByDisciplinaId(disciplinaId);

        if (alunos.isEmpty()) {
            return new ArrayList<>();
        }

        List<Nota> todasAsNotas = notaRepository.findByAvaliacao_Disciplina_IdDisciplinaAndAlunoIn(disciplinaId, alunos);
        List<Frequencia> todasAsFrequencias = frequenciaRepository.findByAula_Disciplina_IdDisciplinaAndAlunoIn(disciplinaId, alunos);

        Map<String, List<Nota>> mapaNotasPorAluno = todasAsNotas.stream()
                .collect(Collectors.groupingBy(nota -> nota.getAluno().getIdAluno()));

        Map<String, List<Frequencia>> mapaFrequenciaPorAluno = todasAsFrequencias.stream()
                .collect(Collectors.groupingBy(frequencia -> frequencia.getAluno().getIdAluno()));


        return alunos.stream().map(aluno -> {

            List<Frequencia> frequenciasDoAluno = mapaFrequenciaPorAluno.getOrDefault(aluno.getIdAluno(), List.of());
            long totalAulas = frequenciasDoAluno.size();
            long presencas = frequenciasDoAluno.stream().filter(Frequencia::getPresente).count();
            float frequenciaCalculada = (totalAulas == 0) ? 100.0f : ((float) presencas / totalAulas) * 100.0f;

            List<Nota> notasDoAluno = mapaNotasPorAluno.getOrDefault(aluno.getIdAluno(), List.of());
            double somaPonderada = notasDoAluno.stream()
                    .mapToDouble(n -> n.getValor() * n.getAvaliacao().getPeso())
                    .sum();
            double somaPesos = notasDoAluno.stream()
                    .mapToDouble(n -> n.getAvaliacao().getPeso())
                    .sum();
            float mediaCalculada = (somaPesos == 0) ? 0.0f : (float) (somaPonderada / somaPesos);

            return new AlunoDisciplinaDTO(
                    aluno.getIdAluno(),
                    aluno.getNome(),
                    aluno.getEmail(),
                    disciplina.getIdDisciplina(),
                    disciplina.getNome(),
                    mediaCalculada,
                    frequenciaCalculada
            );

        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AlunoBoletimDTO getBoletimByAluno(String idAluno) {

        Aluno aluno = this.alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + idAluno));

        Set<Disciplina> disciplinasDoAluno = disciplinaSemestreRepository.findByAluno(idAluno)
                .stream()
                .map(DisciplinaSemestre::getDisciplina)
                .collect(Collectors.toSet());

        Set<Avaliacao> avaliacoesDoAluno = avaliacaoRepository.findByAluno(idAluno);
        List<Nota> notasDoAluno = notaRepository.findByAluno_IdAluno(idAluno);

        List<Frequencia> todasAsFrequenciasDoAluno = frequenciaRepository.findByAluno_IdAluno(idAluno);

        Map<Long, Nota> mapaDeNotasPorAvaliacaoId = notasDoAluno.stream()
                .collect(Collectors.toMap(
                        nota -> nota.getAvaliacao().getIdAvaliacao(),
                        Function.identity()
                ));

        List<AvaliacaoBoletimDTO> todosOsAvaliacaoDTOs = avaliacoesDoAluno.stream()
                .map(avaliacao -> {
                    Nota notaCorrespondente = mapaDeNotasPorAvaliacaoId.get(avaliacao.getIdAvaliacao());
                    NotaBoletimDTO notaDTO = (notaCorrespondente != null) ? new NotaBoletimDTO(notaCorrespondente) : null;
                    return new AvaliacaoBoletimDTO(
                            avaliacao.getIdAvaliacao(),
                            avaliacao.getTipo(),
                            avaliacao.getPeso(),
                            avaliacao.getData(),
                            avaliacao.getConcluida(),
                            notaDTO
                    );
                })
                .toList();

        Map<Long, Set<AvaliacaoBoletimDTO>> mapaDeAvaliacoesPorDisciplina = todosOsAvaliacaoDTOs.stream()
                .collect(Collectors.groupingBy(

                        avaliacaoDTO -> avaliacoesDoAluno.stream()
                                .filter(a -> a.getIdAvaliacao().equals(avaliacaoDTO.idAvaliacao()))
                                .findFirst().get().getDisciplina().getIdDisciplina(),
                        Collectors.toSet()
                ));

        Map<Long, Float> mapaDeFrequenciaPorDisciplina = todasAsFrequenciasDoAluno.stream()
                .collect(Collectors.groupingBy(
                        f -> f.getAula().getDisciplina().getIdDisciplina(),
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                listaDeFrequencias -> {
                                    long totalAulas = listaDeFrequencias.size();
                                    if (totalAulas == 0) return 100.0f; // Se não houve aula, 100% de frequência
                                    long presencas = listaDeFrequencias.stream().filter(Frequencia::getPresente).count();
                                    return ((float) presencas / totalAulas) * 100.0f;
                                }
                        )
                ));

        Set<DisciplinaBoletimDTO> disciplinasBoletimDTOs = disciplinasDoAluno.stream()
                .map(disciplina -> {
                    float frequencia = mapaDeFrequenciaPorDisciplina.getOrDefault(disciplina.getIdDisciplina(), 100.0f);

                    return new DisciplinaBoletimDTO(
                            disciplina.getIdDisciplina(),
                            disciplina.getNome(),
                            mapaDeAvaliacoesPorDisciplina.getOrDefault(disciplina.getIdDisciplina(), Set.of()),
                            frequencia
                    );
                })
                .collect(Collectors.toSet());


        return new AlunoBoletimDTO(
                disciplinasBoletimDTOs
        );
    }


    private AlunoResponseDTO convertToResponseDTO(Aluno aluno) {
        String idInstituicao = (aluno.getCurso() != null && aluno.getCurso().getInstituicao() != null)
                ? aluno.getCurso().getInstituicao().getIdInstituicao()
                : null;

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

    @Override
    @Transactional(readOnly = true)
    public Set<ComparativoMediaDisciplinaDTO> getComparativoMediasPorDisciplina(String idAluno) {

        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + idAluno));
        Turma turma = aluno.getTurma();

        Set<Disciplina> disciplinasDoAluno = disciplinaSemestreRepository.findByAluno(idAluno)
                .stream().map(DisciplinaSemestre::getDisciplina).collect(Collectors.toSet());

        List<Nota> notasDaTurmaInteira = notaRepository.findByAvaliacao_Turma_IdTurma(turma.getIdTurma());


        Map<Disciplina, List<Nota>> mapaNotasTurmaPorDisciplina = notasDaTurmaInteira.stream()
                .collect(Collectors.groupingBy(nota -> nota.getAvaliacao().getDisciplina()));

        Map<Disciplina, List<Nota>> mapaNotasAlunoPorDisciplina = notasDaTurmaInteira.stream()
                .filter(nota -> nota.getAluno().getIdAluno().equals(idAluno))
                .collect(Collectors.groupingBy(nota -> nota.getAvaliacao().getDisciplina()));

        return disciplinasDoAluno.stream().map(disciplina -> {
            List<Nota> notasDaDisciplinaParaAluno = mapaNotasAlunoPorDisciplina.getOrDefault(disciplina, List.of());
            List<Nota> notasDaDisciplinaParaTurma = mapaNotasTurmaPorDisciplina.getOrDefault(disciplina, List.of());

            float mediaAluno = calcularMediaPonderada(notasDaDisciplinaParaAluno);
            float mediaTurma = calcularMediaPonderada(notasDaDisciplinaParaTurma);

            return new ComparativoMediaDisciplinaDTO(
                    disciplina.getNome(),
                    mediaAluno,
                    mediaTurma
            );
        }).collect(Collectors.toSet());
    }

    private float calcularMediaPonderada(List<Nota> notas) {
        if (notas == null || notas.isEmpty()) {
            return 0.0f;
        }

        double somaPonderada = notas.stream()
                .mapToDouble(n -> n.getValor() * n.getAvaliacao().getPeso())
                .sum();

        double somaPesos = notas.stream()
                .mapToDouble(n -> n.getAvaliacao().getPeso())
                .sum();

        return (somaPesos == 0) ? 0.0f : (float) (somaPonderada / somaPesos);
    }

}
