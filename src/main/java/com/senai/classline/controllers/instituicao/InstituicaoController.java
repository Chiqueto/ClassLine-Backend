package com.senai.classline.controllers.instituicao;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.CursoDTO;
import com.senai.classline.dto.ProfessorDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.UserType;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/instituicao")
@RequiredArgsConstructor
public class InstituicaoController {
	private final ProfessorRepository professorRepository;
	private final ProfessorService professorService;
	private final CursoRepository cursoRepository;

	@PreAuthorize("hasRole('INSTITUICAO')")
	@GetMapping
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("sucesso!");
	}


	@PreAuthorize("hasRole('INSTITUICAO')")
	@PostMapping("/{id_instituicao}/professor")
	public ResponseEntity professorRegister(@PathVariable String id_instituicao, @RequestBody ProfessorDTO body) {
		try{
			professorService.salvar(body, id_instituicao);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}catch(RuntimeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@DeleteMapping("/{id_instituicao}/professor/{id}")
	public ResponseEntity<?> inactiveProfessor(
			@PathVariable String id,
			@PathVariable String id_instituicao
	) {
		try {
			professorService.inativar(id, id_instituicao);
			return ResponseEntity.noContent().build(); // HTTP 204
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
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
