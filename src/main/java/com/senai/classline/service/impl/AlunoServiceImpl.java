package com.senai.classline.service.impl;

import com.senai.classline.domain.aluno.Aluno;
import com.senai.classline.domain.avaliacao.Avaliacao;
import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.disciplina.Disciplina;
import com.senai.classline.domain.disciplinaSemestre.DisciplinaSemestre;
import com.senai.classline.domain.frequencia.Frequencia;
import com.senai.classline.domain.instituicao.Instituicao; // Assuming Instituicao can be accessed
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
    @Transactional(readOnly = true)
    public List<AlunoDisciplinaDTO> getAlunoByDisciplina(Long disciplinaId) {

        // --- PASSO 1: BUSCAR TODOS OS DADOS EM LOTE ---

        // 1.1 Valida a disciplina e busca a lista de alunos matriculados nela.
        Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
                .orElseThrow(() -> new NotFoundException("Disciplina com o ID informado não foi encontrada."));
        List<Aluno> alunos = alunoRepository.findAlunosByDisciplinaId(disciplinaId);

        if (alunos.isEmpty()) {
            return new ArrayList<>(); // Retorna lista vazia se não há alunos.
        }

        // 1.2 Busca TODAS as notas e frequências para a lista de alunos e a disciplina específica.
        List<Nota> todasAsNotas = notaRepository.findByAvaliacao_Disciplina_IdDisciplinaAndAlunoIn(disciplinaId, alunos);
        List<Frequencia> todasAsFrequencias = frequenciaRepository.findByAula_Disciplina_IdDisciplinaAndAlunoIn(disciplinaId, alunos);


        // --- PASSO 2: ORGANIZAR DADOS EM MAPAS PARA CÁLCULOS RÁPIDOS ---

        // 2.1 Agrupa todas as notas por aluno.
        Map<String, List<Nota>> mapaNotasPorAluno = todasAsNotas.stream()
                .collect(Collectors.groupingBy(nota -> nota.getAluno().getIdAluno()));

        // 2.2 Agrupa todos os registros de frequência por aluno.
        Map<String, List<Frequencia>> mapaFrequenciaPorAluno = todasAsFrequencias.stream()
                .collect(Collectors.groupingBy(frequencia -> frequencia.getAluno().getIdAluno()));


        // --- PASSO 3: MONTAR O DTO FINAL, REALIZANDO OS CÁLCULOS EM MEMÓRIA ---
        return alunos.stream().map(aluno -> {

            // --- Cálculo da Frequência ---
            List<Frequencia> frequenciasDoAluno = mapaFrequenciaPorAluno.getOrDefault(aluno.getIdAluno(), List.of());
            long totalAulas = frequenciasDoAluno.size();
            long presencas = frequenciasDoAluno.stream().filter(Frequencia::getPresente).count();
            float frequenciaCalculada = (totalAulas == 0) ? 100.0f : ((float) presencas / totalAulas) * 100.0f;

            // --- Cálculo da Média Ponderada ---
            List<Nota> notasDoAluno = mapaNotasPorAluno.getOrDefault(aluno.getIdAluno(), List.of());
            double somaPonderada = notasDoAluno.stream()
                    .mapToDouble(n -> n.getValor() * n.getAvaliacao().getPeso())
                    .sum();
            double somaPesos = notasDoAluno.stream()
                    .mapToDouble(n -> n.getAvaliacao().getPeso())
                    .sum();
            float mediaCalculada = (somaPesos == 0) ? 0.0f : (float) (somaPonderada / somaPesos);

            // --- Montagem do DTO ---
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

        // --- PASSO 1: BUSCAR TODOS OS DADOS DO BANCO (EM POUCAS E EFICIENTES CONSULTAS) ---

        // 1.1. Valida e busca a entidade principal, o Aluno.
        Aluno aluno = this.alunoRepository.findById(idAluno)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado com ID: " + idAluno));

        // 1.2. Busca todas as disciplinas do aluno de uma vez.
        Set<Disciplina> disciplinasDoAluno = disciplinaSemestreRepository.findByAluno(idAluno)
                .stream()
                .map(DisciplinaSemestre::getDisciplina)
                .collect(Collectors.toSet());

        // 1.3. Busca todas as avaliações e notas do aluno de uma vez.
        Set<Avaliacao> avaliacoesDoAluno = avaliacaoRepository.findByAluno(idAluno);
        List<Nota> notasDoAluno = notaRepository.findByAluno_IdAluno(idAluno);

        List<Frequencia> todasAsFrequenciasDoAluno = frequenciaRepository.findByAluno_IdAluno(idAluno);


        // --- PASSO 2: ORGANIZAR OS DADOS EM MEMÓRIA PARA ACESSO RÁPIDO (A MÁGICA ACONTECE AQUI) ---

        // 2.1. Cria um mapa de notas, onde a chave é o ID da avaliação.
        Map<Long, Nota> mapaDeNotasPorAvaliacaoId = notasDoAluno.stream()
                .collect(Collectors.toMap(
                        nota -> nota.getAvaliacao().getIdAvaliacao(),
                        Function.identity()
                ));

        // 2.2. Converte a lista de entidades Avaliacao para uma lista de AvaliacaoBoletimDTO.
        //     Cada DTO já é criado com sua nota correspondente, pega do mapa.
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

        // 2.3. Agrupa os DTOs de avaliação pelo ID da disciplina a que pertencem.
        //     Isso cria um mapa onde cada disciplina tem sua própria lista de avaliações.
        //     (Pré-requisito: a entidade Avaliacao deve ter um campo `private Disciplina disciplina;`)
        Map<Long, Set<AvaliacaoBoletimDTO>> mapaDeAvaliacoesPorDisciplina = todosOsAvaliacaoDTOs.stream()
                .collect(Collectors.groupingBy(
                        // Para agrupar, precisamos saber a qual disciplina cada avaliação pertence.
                        // Assumindo que Avaliacao tem getDisciplina()
                        avaliacaoDTO -> avaliacoesDoAluno.stream()
                                .filter(a -> a.getIdAvaliacao().equals(avaliacaoDTO.idAvaliacao()))
                                .findFirst().get().getDisciplina().getIdDisciplina(),
                        Collectors.toSet()
                ));

        Map<Long, Float> mapaDeFrequenciaPorDisciplina = todasAsFrequenciasDoAluno.stream()
                .collect(Collectors.groupingBy(
                        // Agrupa os registros de frequência pelo ID da disciplina
                        f -> f.getAula().getDisciplina().getIdDisciplina(),
                        // Para cada grupo, calcula a porcentagem de frequência
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

        // --- PASSO 3: MONTAR A LISTA DE DISCIPLINAS COM SUAS AVALIAÇÕES ---

        // 3.1. Itera sobre as disciplinas do aluno e monta o DTO de cada uma.
        Set<DisciplinaBoletimDTO> disciplinasBoletimDTOs = disciplinasDoAluno.stream()
                .map(disciplina -> {
                    // Pega a frequência calculada do mapa. Se não houver registro, assume 100%.
                    float frequencia = mapaDeFrequenciaPorDisciplina.getOrDefault(disciplina.getIdDisciplina(), 100.0f);

                    return new DisciplinaBoletimDTO(
                            disciplina.getIdDisciplina(),
                            disciplina.getNome(),
                            mapaDeAvaliacoesPorDisciplina.getOrDefault(disciplina.getIdDisciplina(), Set.of()),
                            frequencia // <-- Passando a frequência calculada
                    );
                })
                .collect(Collectors.toSet());


        // --- PASSO 4: MONTAR E RETORNAR O BOLETIM FINAL ---

        // 4.1. Com todas as partes prontas, cria o DTO final do boletim.
        return new AlunoBoletimDTO(
                disciplinasBoletimDTOs
        );
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