package com.senai.classline.controllers.instituicao;

import java.util.Optional;

import com.senai.classline.enums.UserType;
import com.senai.classline.infra.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.InstituicaoRegisterRequestDTO;
import com.senai.classline.dto.InstituicaoLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.repositories.InstituicaoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("instituicao/auth")
@RequiredArgsConstructor
public class InstituicaoAuthController {
	private final InstituicaoRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody InstituicaoLoginRequestDTO body) {
		System.out.println(body.toString());
		Instituicao instituicao = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
		if(passwordEncoder.matches(body.senha(), instituicao.getSenha())) {
			String token = tokenService.generateToken(instituicao, UserType.INSTITUICAO);
			return ResponseEntity.ok(new ResponseDTO(instituicao.getId_instituicao(), token));
		}
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody InstituicaoRegisterRequestDTO body) {

		Optional<Instituicao> instituicao = this.repository.findByEmail(body.email());

		if(instituicao.isEmpty()) {
			Instituicao newInstituicao = new Instituicao();
			newInstituicao.setNome(body.nome());
			newInstituicao.setSenha(passwordEncoder.encode(body.senha()));
			newInstituicao.setEmail(body.email());
			newInstituicao.setTelefone(body.telefone());
			newInstituicao.setCidade(body.cidade());
			newInstituicao.setBairro(body.bairro());
			newInstituicao.setLogradouro(body.logradouro());
			newInstituicao.setNumero(body.numero());

			System.out.println(newInstituicao);
			this.repository.save(newInstituicao);

			String token = tokenService.generateToken(newInstituicao, UserType.INSTITUICAO);
			return ResponseEntity.ok(new ResponseDTO(newInstituicao.getId_instituicao(), token));

		}
		return ResponseEntity.badRequest().build();

	}
}
