package com.senai.classline.controllers.instituicao;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.dto.curso.CursoDTO;
import com.senai.classline.dto.instituicao.InstituicaoEditarDTO;
import com.senai.classline.repositories.CursoRepository;
import com.senai.classline.repositories.ProfessorRepository;
import com.senai.classline.service.InstituicaoService;
import com.senai.classline.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/instituicao")
@RequiredArgsConstructor
public class InstituicaoController {
	private final ProfessorRepository professorRepository;
	private final InstituicaoService service;
	private final CursoRepository cursoRepository;

	@PreAuthorize("hasRole('INSTITUICAO')")
	@GetMapping
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("sucesso!");
	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@PutMapping("/{idInstituicao}")
	public ResponseEntity editarInstituicao(@PathVariable String idInstituicao, @RequestBody @Valid InstituicaoEditarDTO body){
		this.service.editar(idInstituicao, body);
		return ResponseEntity.status(HttpStatus.OK).body("Instituição atualizada com sucesso!");
	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@PostMapping("/{id_instituicao}/curso")
	public ResponseEntity cursoRegister(@RequestBody @Valid CursoDTO body, @PathVariable String id_instituicao) {
		Optional<Curso> curso = this.cursoRepository.findByNome(body.nome());
		if (curso.isPresent()) {
			return ResponseEntity.status(409).body("Curso já cadastrado.");
		}
		try {
			Curso newCurso = new Curso();
			newCurso.setDescricao(body.descricao());
			newCurso.setTipo(body.tipo());
			newCurso.setNome(body.nome());
			newCurso.setIdInstituicao(id_instituicao);
			newCurso.setQtde_semestres(body.qtde_semestres());

			System.out.println(newCurso);
			this.cursoRepository.save(newCurso);

			return ResponseEntity.noContent().build();
		}catch (RuntimeException e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
