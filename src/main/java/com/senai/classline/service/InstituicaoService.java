package com.senai.classline.service;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.instituicao.InstituicaoDTO;
import com.senai.classline.dto.instituicao.InstituicaoEditarDTO;
import com.senai.classline.dto.instituicao.InstituicaoLoginRequestDTO;
import com.senai.classline.dto.ResponseDTO;

public interface InstituicaoService {
    Instituicao salvar(InstituicaoDTO instituicaoDTO);

    Instituicao editar(String idInstituicao, InstituicaoEditarDTO body);

    ResponseDTO login(InstituicaoLoginRequestDTO loginRequest);
}
