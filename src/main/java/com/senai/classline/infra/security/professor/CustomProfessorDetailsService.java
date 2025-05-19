package com.senai.classline.infra.security.professor;

import com.senai.classline.domain.professor.Professor;
import com.senai.classline.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomProfessorDetailsService implements UserDetailsService {
    @Autowired
    private ProfessorRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Professor professor = this.repository.findByCpf(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        return new org.springframework.security.core.userdetails.User(professor.getCpf(), professor.getSenha(), new ArrayList<>());
    }

}