package com.senai.classline.controllers.instituicao;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.CursoDTO;
import com.senai.classline.dto.ProfessorRegisterRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.enums.Formacao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/instituicao")
@RequiredArgsConstructor
public class InstituicaoController {
	private final ProfessorRepository professorRepository;
	private final PasswordEncoder professorPasswordEncoder;
	private final TokenService professorTokenService;
	private final CursoRepository cursoRepository;

	@PreAuthorize("hasRole('INSTITUICAO')")
	@GetMapping
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("sucesso!");
	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@PostMapping("/{id_instituicao}/professor")
	public ResponseEntity professorRegister(@PathVariable String id_instituicao, @RequestBody ProfessorRegisterRequestDTO body) {

		Optional<Professor> professor = this.professorRepository.findByCpf(body.cpf());

		if(professor.isEmpty()) {
			Professor newProfessor = new Professor();
			newProfessor.setId_instituicao(id_instituicao);
			newProfessor.setNome(body.nome());
			newProfessor.setSenha(professorPasswordEncoder.encode(body.senha()));
			newProfessor.setEmail(body.email());
			newProfessor.setCpf(body.cpf());
			newProfessor.setDt_nascimento(body.dt_nascimento());
			newProfessor.setGenero(body.genero());
			newProfessor.setTelefone(body.telefone());
			newProfessor.setCidade(body.cidade());
			newProfessor.setBairro(body.bairro());
			newProfessor.setLogradouro(body.logradouro());
			newProfessor.setNumero(body.numero());
			newProfessor.setTurno(body.turno());
			newProfessor.setStatus(StatusPessoa.ATIVO);
			newProfessor.setFormacao(body.formacao());
			newProfessor.setArea_atuacao(body.area_atuacao());
			newProfessor.setDiploma(body.diploma());
			newProfessor.setDt_admissao(body.dt_admissao());


			System.out.println(newProfessor);
			this.professorRepository.save(newProfessor);

			String token = this.professorTokenService.generateToken(newProfessor, UserType.PROFESSOR);
			return ResponseEntity.ok(new ResponseDTO(newProfessor.getId_professor(),token));

		}
		return ResponseEntity.badRequest().build();

	}
	@PreAuthorize("hasRole('INSTITUICAO')")
	@DeleteMapping("/{id_instituicao}/professor/{id}")
	public ResponseEntity inactiveProfessor(@PathVariable String id, @PathVariable String id_instituicao) {
		Optional<Professor> professor = this.professorRepository.findById(id);

		if(professor.isEmpty()){
			return ResponseEntity.notFound().build();
		}

		try{
			Professor entity = professor.get();
				entity.setStatus(StatusPessoa.INATIVO);
				entity.setDt_desligamento(
						Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant())
				);
				professorRepository.save(entity);

				return ResponseEntity.noContent().build();
		} catch (RuntimeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@PutMapping("/{id_instituicao}/professor/{id}")
	public ResponseEntity updateProfessor(@PathVariable String id_instituicao, @PathVariable String id) {
		Optional<Professor> professor = this.professorRepository.findById(id);

		if(professor.isEmpty()){
			return ResponseEntity.notFound().build();
		}

		try{
			Professor entity = professor.get();
			entity.setStatus(StatusPessoa.INATIVO);
			entity.setDt_desligamento(
					Date.from(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant())
			);
			professorRepository.save(entity);

			return ResponseEntity.noContent().build();
		} catch (RuntimeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@PostMapping("/{id_instituicao}/curso")
	public ResponseEntity cursoRegister(@RequestBody CursoDTO body, @PathVariable String id_instituicao) {
		Optional<Curso> curso = this.cursoRepository.findByNome(body.nome());
		if (curso.isPresent()) {
			return ResponseEntity.status(409).body("Curso já cadastrado.");
		}
		try {
			Curso newCurso = new Curso();
			newCurso.setDescricao(body.descricao());
			newCurso.setTipo(body.tipo());
			newCurso.setNome(body.nome());
			newCurso.setId_instituicao(id_instituicao);
			newCurso.setQtde_semestres(body.qtde_semestres());

			System.out.println(newCurso);
			this.cursoRepository.save(newCurso);

			return ResponseEntity.noContent().build();
		}catch (RuntimeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
