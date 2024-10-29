package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class FreelanceDTO {

    private UUID idFreelance;

    @NotEmpty(message = "O nome é obrigatório.")
    private String nome;
    @Email(message = "E-mail inválido.")
    @NotEmpty(message = "O e-mail é obrigatório.")
    private String eMail;
    @NotEmpty(message = "As tecnologias são obrigatórias.")
    private Set<String> tecnologias;
    private String enderecoResidencia;
    private int numeroResidencia;
    private String complementoEndereco;
    @Positive(message = "Anos de experiência deve ser um número positivo.")
    private int qtdAnosExperiencia;
    @Positive(message = "Valor por hora deve ser um valor positivo.")
    private Double valorHora;

    // Getters e Setters
    public UUID getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(UUID idFreelance) {
        this.idFreelance = idFreelance;
    }

    public @NotEmpty(message = "O nome é obrigatório.") String getNome() {
        return nome;
    }

    public void setNome(@NotEmpty(message = "O nome é obrigatório.") String nome) {
        this.nome = nome;
    }

    public @Email(message = "E-mail inválido.") @NotEmpty(message = "O e-mail é obrigatório.") String geteMail() {
        return eMail;
    }

    public void seteMail(@Email(message = "E-mail inválido.") @NotEmpty(message = "O e-mail é obrigatório.") String eMail) {
        this.eMail = eMail;
    }

    public @NotEmpty(message = "As tecnologias são obrigatórias.") Set<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(@NotEmpty(message = "As tecnologias são obrigatórias.") Set<String> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public String getEnderecoResidencia() {
        return enderecoResidencia;
    }

    public void setEnderecoResidencia(String enderecoResidencia) {
        this.enderecoResidencia = enderecoResidencia;
    }

    public int getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(int numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getComplementoEndereco() {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    @Positive(message = "Anos de experiência deve ser um número positivo.")
    public int getQtdAnosExperiencia() {
        return qtdAnosExperiencia;
    }

    public void setQtdAnosExperiencia(@Positive(message = "Anos de experiência deve ser um número positivo.") int qtdAnosExperiencia) {
        this.qtdAnosExperiencia = qtdAnosExperiencia;
    }

    public @Positive(message = "Valor por hora deve ser um valor positivo.") Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(@Positive(message = "Valor por hora deve ser um valor positivo.") Double valorHora) {
        this.valorHora = valorHora;
    }

}