package br.com.freelance_management_api.dto;

import br.com.freelance_management_api.entities.StatusFatura;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public class FaturaDTO {

    private UUID id;

    @Positive(message = "{fatura.valor.positivo}")
    private Double valor;

    @NotNull(message = "{fatura.status.obrigatorio}")
    private StatusFatura status;

    @NotNull(message = "{fatura.dataEmissao.obrigatoria}")
    private LocalDate dataEmissao;

    @NotNull(message = "{fatura.dataVencimento.obrigatoria}")
    private LocalDate dataVencimento;

    @NotNull(message = "{fatura.projeto.obrigatorio}")
    private UUID idProjeto;

    @NotNull(message = "{fatura.freelance.obrigatorio}")
    private UUID idFreelance;

    private String emailStatus;

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

    public StatusFatura getStatus() {
        return status;
    }

    public void setStatus(StatusFatura status) {
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

    public UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public UUID getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(UUID idFreelance) {
        this.idFreelance = idFreelance;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

}
