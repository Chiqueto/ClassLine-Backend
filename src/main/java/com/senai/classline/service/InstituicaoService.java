package com.senai.classline.service;

import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.dto.InstituicaoDTO;

public interface InstituicaoService {
    Instituicao salvar(InstituicaoDTO instituicaoDTO);

    Instituicao editar();

}
