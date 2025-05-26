package com.senai.classline.service;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.InstituicaoDTO;
import com.senai.classline.dto.InstituicaoLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;

public interface InstituicaoService {
    Instituicao salvar(InstituicaoDTO instituicaoDTO);

    Instituicao editar();

    ResponseDTO login(InstituicaoLoginRequestDTO loginRequest);
}
