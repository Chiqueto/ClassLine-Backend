package com.senai.classline.domain.instituicao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.senai.classline.domain.commom.AuthenticatedUser;
import com.senai.classline.domain.professor.Professor;
import com.senai.classline.domain.turma.Turma;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "instituicao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instituicao implements AuthenticatedUser {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id_instituicao")
	private String idInstituicao;
	private String nome;
	private String email;
	private String senha;
	private String logradouro;
	private String bairro;
	private String numero;
	private String cidade;
	private String telefone;
	@OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Professor> professores;

	@Override
	public String getLoginIdentifier() {
		return this.getEmail();
	}
}
