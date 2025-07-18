package com.senai.classline.controllers.instituicao;

import com.senai.classline.enums.UserType;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.service.impl.InstituicaoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.instituicao.InstituicaoDTO;
import com.senai.classline.dto.instituicao.InstituicaoLoginRequestDTO;
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
	private final InstituicaoServiceImpl instituicaoService;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody InstituicaoLoginRequestDTO body) {
		ResponseDTO response = instituicaoService.login(body);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/register")
	public ResponseEntity register(@RequestBody @Valid InstituicaoDTO body) {

		final Instituicao instituicao = instituicaoService.salvar(body);

		String token = tokenService.generateToken(instituicao, UserType.INSTITUICAO);
		return ResponseEntity.ok(new ResponseDTO(instituicao.getIdInstituicao(), token));


	}
}
