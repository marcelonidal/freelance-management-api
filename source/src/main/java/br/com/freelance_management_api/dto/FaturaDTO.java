package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public class FaturaDTO {

    private UUID id;

    @Positive(message = "O valor deve ser positivo.")
    private Double valor;

    @NotNull(message = "O status da fatura é obrigatório.")
    private String status;

    @NotNull(message = "A data de emissão é obrigatória.")
    private LocalDate dataEmissao;

    @NotNull(message = "A data de vencimento é obrigatória.")
    private LocalDate dataVencimento;

    @NotNull(message = "O projeto é obrigatório.")
    private ProjetoDTO projeto;

    @NotNull(message = "O freelancer é obrigatório.")
    private FreelanceDTO freelance;
    private String emailStatus;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public @NotNull(message = "O projeto é obrigatório.") ProjetoDTO getProjeto() {
        return projeto;
    }

    public void setProjeto(@NotNull(message = "O projeto é obrigatório.") ProjetoDTO projeto) {
        this.projeto = projeto;
    }

    public @NotNull(message = "O freelancer é obrigatório.") FreelanceDTO getFreelance() {
        return freelance;
    }

    public void setFreelance(@NotNull(message = "O freelancer é obrigatório.") FreelanceDTO freelance) {
        this.freelance = freelance;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

}
