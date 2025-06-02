package com.senai.classline.service.impl;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.instituicao.InstituicaoDTO;
import com.senai.classline.dto.instituicao.InstituicaoEditarDTO;
import com.senai.classline.dto.instituicao.InstituicaoLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;
import com.senai.classline.enums.UserType;
import com.senai.classline.exceptions.instituicao.InstituicaoAlreadyExists;
import com.senai.classline.exceptions.global.LoginFail;
import com.senai.classline.exceptions.instituicao.InstituicaoNotFound;
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
    public Instituicao editar (String idInstituicao, InstituicaoEditarDTO body) {
        Instituicao instituicao = repository.findByIdInstituicao(idInstituicao).get();

        if(instituicao == null){
            throw new InstituicaoNotFound();
        }

        if(body.nome() != null){ instituicao.setNome(body.nome());}
        if(body.bairro() != null){ instituicao.setBairro(body.bairro());}
        if(body.cidade() != null){ instituicao.setCidade(body.cidade());}
        if(body.logradouro() != null){ instituicao.setLogradouro(body.logradouro());}
        if(body.numero() != null){ instituicao.setNumero(body.numero());}
        if(body.email() != null){
            if(repository.findByEmail(body.email()).isPresent() && body.email() != instituicao.getEmail()){
                throw new InstituicaoAlreadyExists();
            } else{
                instituicao.setEmail(body.email());
            }
        }
        if(body.senha() != null){ instituicao.setSenha(passwordEncoder.encode(body.senha()));}
        if(body.telefone() != null){ instituicao.setTelefone(body.telefone());}

        return repository.save(instituicao);

    }

    @Override
    public ResponseDTO login(InstituicaoLoginRequestDTO loginRequest) {
        Optional<Instituicao> instituicao = repository.findByEmail(loginRequest.email());

        if (instituicao.isEmpty() || !passwordEncoder.matches(loginRequest.senha(), instituicao.get().getSenha())){
            throw new LoginFail();
        }

        String token = tokenService.generateToken(instituicao.get(), UserType.INSTITUICAO);
        return new ResponseDTO(instituicao.get().getIdInstituicao(), token);
    }

    @Override
    public Instituicao getById(String idInstituicao) {
        Optional<Instituicao> instituicao = repository.findById(idInstituicao);

        if (instituicao.isEmpty()){
            throw new InstituicaoNotFound();
        }

        return instituicao.get();


    }
}

