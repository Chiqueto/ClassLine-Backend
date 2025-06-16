package com.senai.classline.infra.security;

import com.senai.classline.enums.UserType;
import com.senai.classline.repositories.AlunoRepository;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceFactory {

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    public UserDetails loadUserByLogin(String login, UserType type) {
        switch (type) {
            case INSTITUICAO:
                var inst = instituicaoRepository.findByEmail(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Instituição não encontrada"));
                return new org.springframework.security.core.userdetails.User(
                        inst.getEmail(), inst.getSenha(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_INSTITUICAO")));
            case PROFESSOR:
                var prof = professorRepository.findByCpf(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Professor não encontrado"));
                return new org.springframework.security.core.userdetails.User(
                        prof.getCpf(), prof.getSenha(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_PROFESSOR")));
            case ALUNO:
                var aluno = alunoRepository.findByCpf(login)
                        .orElseThrow(() -> new UsernameNotFoundException("Aluno não encontrado"));
                return new org.springframework.security.core.userdetails.User(
                        aluno.getCpf(), aluno.getSenha(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ALUNO")));
            default:
                throw new IllegalArgumentException("Tipo inválido");
        }
    }
}