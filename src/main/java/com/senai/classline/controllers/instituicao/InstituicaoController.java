package com.senai.classline.controllers.instituicao;

import com.senai.classline.domain.curso.Curso;
import com.senai.classline.domain.instituicao.Instituicao;
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
	private final InstituicaoService service;

	@PreAuthorize("hasRole('INSTITUICAO')")
	@GetMapping("/{id_instituicao}")
	public ResponseEntity<Instituicao> getInstituicaoById(@PathVariable String id_instituicao){
			final Instituicao instituicao = this.service.getById(id_instituicao);
			return ResponseEntity.status(HttpStatus.OK).body(instituicao);
	}

	@PreAuthorize("hasRole('INSTITUICAO')")
	@PutMapping("/{idInstituicao}")
	public ResponseEntity editarInstituicao(@PathVariable String idInstituicao, @RequestBody @Valid InstituicaoEditarDTO body){
		this.service.editar(idInstituicao, body);
		return ResponseEntity.status(HttpStatus.OK).body("Instituição atualizada com sucesso!");
	}

}
