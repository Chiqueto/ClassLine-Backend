package com.senai.classline.domain.pessoa;


import com.senai.classline.domain.commom.AuthenticatedUser;
import com.senai.classline.enums.StatusPessoa;
import com.senai.classline.enums.Turno;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Pessoa implements AuthenticatedUser {
    @Column (name = "id_instituicao")
    private String idInstituicao;
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
