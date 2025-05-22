package com.senai.classline.service.impl;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.InstituicaoDTO;
import com.senai.classline.infra.security.TokenService;
import com.senai.classline.repositories.InstituicaoRepository;
import com.senai.classline.service.InstituicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class InstituicaoServiceImpl implements InstituicaoService {
    private final InstituicaoRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Override
    public Instituicao salvar(InstituicaoDTO body) {
        Optional<Instituicao> instituicao = this.repository.findByEmail(body.email());

        if (instituicao.isPresent()) {
            throw new RuntimeException("Instituição já existe");
        }
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
        return this.repository.save(newInstituicao);
    }

        @Override
        public Instituicao editar () {
            return null;
        }
    }

