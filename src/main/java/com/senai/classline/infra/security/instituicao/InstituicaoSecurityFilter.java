package com.senai.classline.infra.security.instituicao;

import java.util.Collections;

import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.repositories.InstituicaoRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class InstituicaoSecurityFilter extends OncePerRequestFilter {
	@Autowired
	InstituicaoTokenService tokenService;
	@Autowired
	InstituicaoRepository instituicaoRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// ✅ Verifica se a rota começa com /instituicao
		if (!request.getRequestURI().startsWith("/instituicao")) {
			filterChain.doFilter(request, response);
			return;
		}

		var token = this.recoverToken(request);
		var login = tokenService.validateToken(token);

		if (login != null) {
			Instituicao instituicao = instituicaoRepository.findByEmail(login)
					.orElseThrow(() -> new RuntimeException("Instituicao Not Found"));
			var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_INSTITUICAO"));
			var authentication = new UsernamePasswordAuthenticationToken(instituicao, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}
}