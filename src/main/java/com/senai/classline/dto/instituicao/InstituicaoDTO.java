package com.senai.classline.dto.instituicao;


import jakarta.validation.constraints.*;

public record InstituicaoDTO(
		@NotNull(message = "Nome não pode ser nulo")
		@NotEmpty(message = "Nome não pode estar vazio")
		String nome,
		@NotEmpty(message = "Email não pode estar vazio")
		@NotNull(message = "Email não pode ser nulo")
		@Email(message = "Email deve estar em um formato válido")
		String email,
		@NotEmpty(message = "Senha não pode estar vazio")
		@Min(value = 3, message = "Mínimo de 3 caracteres para senha!")
		@NotNull(message = "Senha não pode ser nula")
		String senha,
		String logradouro,
		String bairro,
		String numero,
		String cidade,
		@Pattern(regexp = "^\\(?(\\d{2})\\)?[- ]?(\\d{4,5})[- ]?(\\d{4})$",
				message = "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
		String telefone) {

}
