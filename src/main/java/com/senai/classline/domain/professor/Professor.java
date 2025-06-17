package com.senai.classline.domain.professor;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.senai.classline.domain.instituicao.Instituicao;
import com.senai.classline.domain.pessoa.Pessoa;
import com.senai.classline.enums.Formacao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

import java.util.Date;

@Entity
@Table(name = "professor")
@Getter
@Setter
@SQLRestriction("status = 'ATIVO'")
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_professor")
    private String idProfessor;
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id_instituicao")
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Instituicao instituicao;
    private Formacao formacao;
    private String area_atuacao;
    private String diploma;
    private Date dt_admissao;
    private Date dt_desligamento;


    @Override
    public String getLoginIdentifier() {
        return this.getCpf();
    }
}
