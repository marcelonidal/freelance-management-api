package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public class FaturaDTO {

    private UUID id;

    @Positive(message = "{fatura.valor.positivo}")
    private Double valor;

    @NotNull(message = "{fatura.status.obrigatorio}")
    private String status;

    @NotNull(message = "{fatura.dataEmissao.obrigatoria}")
    private LocalDate dataEmissao;

    @NotNull(message = "{fatura.dataVencimento.obrigatoria}")
    private LocalDate dataVencimento;

    @NotNull(message = "{fatura.projeto.obrigatorio}")
    private ProjetoDTO projeto;

    @NotNull(message = "{fatura.freelance.obrigatorio}")
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

    public ProjetoDTO getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoDTO projeto) {
        this.projeto = projeto;
    }

    public FreelanceDTO getFreelance() {
        return freelance;
    }

    public void setFreelance(FreelanceDTO freelance) {
        this.freelance = freelance;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

}
