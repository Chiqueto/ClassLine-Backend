package com.senai.classline.dto.instituicao;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record InstituicaoEditarDTO(
        String nome,
        @Email(message = "Email deve estar em um formato válido")
        String email,
        @Min(value = 3, message = "Mínimo de 3 caracteres para senha!")
        String senha,
        String logradouro,
        String bairro,
        String numero,
        String cidade,
        @Pattern(regexp = "^\\(?(\\d{2})\\)?[- ]?(\\d{4,5})[- ]?(\\d{4})$",
                message = "Telefone deve estar no formato (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
        String telefone
) {}
