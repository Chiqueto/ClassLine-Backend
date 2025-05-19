package com.senai.classline.infra.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.repositories.InstituicaoRepository;

@Component
public class CustomInstituicaoDetailsService implements UserDetailsService {
	@Autowired
	private InstituicaoRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Instituicao instituicao = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		return new org.springframework.security.core.userdetails.User(instituicao.getEmail(), instituicao.getSenha(), new ArrayList<>());
	}

}
