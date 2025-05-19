package com.senai.classline.controllers.instituicao;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.dto.ProfessorRegisterRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.infra.security.professor.ProfessorTokenService;
import com.senai.classline.repositories.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/instituicao")
@RequiredArgsConstructor
public class InstituicaoController {
	private final ProfessorRepository professorRepository;
	private final PasswordEncoder professorPasswordEncoder;
	private final ProfessorTokenService professorTokenService;

	@GetMapping
	public ResponseEntity<String> getUser(){
		return ResponseEntity.ok("sucesso!");
	}

	@PostMapping("/professor")
	public ResponseEntity register(@RequestBody ProfessorRegisterRequestDTO body) {

		Optional<Professor> professor = this.professorRepository.findByCpf(body.cpf());

		if(professor.isEmpty()) {
			Professor newProfessor = new Professor();
			newProfessor.setId_instituicao(body.id_instituicao());
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
			newProfessor.setStatus(body.status());
			newProfessor.setFormacao(body.formacao());
			newProfessor.setArea_atuacao(body.area_atuacao());
			newProfessor.setDiploma(body.diploma());
			newProfessor.setDt_admissao(body.dt_admissao());


			System.out.println(newProfessor);
			this.professorRepository.save(newProfessor);

			String token = this.professorTokenService.generateToken(newProfessor);
			return ResponseEntity.ok(new ResponseDTO(newProfessor.getNome(), token));

		}
		return ResponseEntity.badRequest().build();

	}


}
