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

    @NotEmpty(message = "{freelance.nome.obrigatorio}")
    private String nome;

    @Email(message = "{freelance.email.invalido}")
    @NotEmpty(message = "{freelance.email.obrigatorio}")
    private String eMail;

    @NotEmpty(message = "{freelance.tecnologias.obrigatorias}")
    private Set<String> tecnologias;

    private String enderecoResidencia;
    private int numeroResidencia;
    private String complementoEndereco;

    @Positive(message = "{freelance.anosExperiencia.positivo}")
    private int qtdAnosExperiencia;

    @Positive(message = "{freelance.valorHora.positivo}")
    private Double valorHora;

    // Getters e Setters
    public UUID getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(UUID idFreelance) {
        this.idFreelance = idFreelance;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Set<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(Set<String> tecnologias) {
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

    public int getQtdAnosExperiencia() {
        return qtdAnosExperiencia;
    }

    public void setQtdAnosExperiencia(int qtdAnosExperiencia) {
        this.qtdAnosExperiencia = qtdAnosExperiencia;
    }

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorHora) {
        this.valorHora = valorHora;
    }

}