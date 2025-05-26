package com.senai.classline.service.impl;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.InstituicaoDTO;
import com.senai.classline.dto.InstituicaoLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.enums.UserType;
import com.senai.classline.exceptions.instituicao.InstituicaoAlreadyExists;
import com.senai.classline.exceptions.instituicao.InstituicaoLoginFail;
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
            System.out.println("Passou aqui");
            throw new InstituicaoAlreadyExists();
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

    @Override
    public ResponseDTO login(InstituicaoLoginRequestDTO loginRequest) {
        Optional<Instituicao> instituicao = repository.findByEmail(loginRequest.email());

        if (instituicao.isEmpty() || !passwordEncoder.matches(loginRequest.senha(), instituicao.get().getSenha())){
            throw new InstituicaoLoginFail();
        }

        String token = tokenService.generateToken(instituicao.get(), UserType.INSTITUICAO);
        return new ResponseDTO(instituicao.get().getId_instituicao(), token);
    }
}

