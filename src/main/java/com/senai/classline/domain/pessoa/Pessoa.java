package com.senai.classline.domain.pessoa;


import com.senai.classline.domain.commom.AuthenticatedUser;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Pessoa implements AuthenticatedUser {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id_instituicao")
    private Instituicao instituicao;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Date dt_nascimento;
    private String genero;
    private String telefone;
    private String logradouro;
    private String bairro;
    private String numero;
    private String cidade;
    @Enumerated(value= EnumType.STRING)
    private Turno turno;
    @Enumerated(value=EnumType.STRING)
    private StatusPessoa status;

}
