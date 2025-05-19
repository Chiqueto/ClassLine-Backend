package com.senai.classline.domain.professor;

import com.senai.classline.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

import java.util.Date;

@Entity
@Table(name = "professor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id_professor;
    private String formacao;
    private String area_atuacao;
    private String diploma;
    private Date dt_admissao;
}
