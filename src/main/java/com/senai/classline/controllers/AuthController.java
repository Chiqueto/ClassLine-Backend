package com.senai.classline.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.InstituicaoRegisterRequestDTO;
import com.senai.classline.dto.LoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.InstituicaoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final InstituicaoRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDTO body) {
		Instituicao instituicao = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Instituição não encontrada"));
		if(passwordEncoder.matches(body.senha(), instituicao.getSenha())) {
			String token = this.tokenService.generateToken(instituicao);
			return ResponseEntity.ok(new ResponseDTO(instituicao.getNome(), token));
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody InstituicaoRegisterRequestDTO body) {
		
		System.out.print("Oáaaaaa");
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
			
			String token = this.tokenService.generateToken(newInstituicao);
			return ResponseEntity.ok(new ResponseDTO(newInstituicao.getNome(), token));
			
		}
		return ResponseEntity.badRequest().build();
		
	}
}
