package com.senai.classline.domain.instituicao;

import com.senai.classline.domain.commom.AuthenticatedUser;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "instituicao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Instituicao implements AuthenticatedUser {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id_instituicao;
	private String nome;
	private String email;
	private String senha;
	private String logradouro;
	private String bairro;
	private String numero;
	private String cidade;
	private String telefone;

	@Override
	public String getLoginIdentifier() {
		return this.getEmail();
	}
}
