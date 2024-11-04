package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class ContratoDTO {

    private UUID idContrato;

    @NotNull(message = "{contrato.freelance.obrigatorio}")
    private UUID idFreelance;

    @NotNull(message = "{contrato.projeto.obrigatorio}")
    private UUID idProjeto;

    @NotNull(message = "{contrato.dataInicioContrato.obrigatoria}")
    private LocalDate dataInicioContrato;

    @NotNull(message = "{contrato.dataFimContrato.obrigatoria}")
    private LocalDate dataFimContrato;
    private String emailStatus;

    public UUID getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(UUID idContrato) {
        this.idContrato = idContrato;
    }

    public @NotNull(message = "{contrato.freelance.obrigatorio}") UUID getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(@NotNull(message = "{contrato.freelance.obrigatorio}") UUID idFreelance) {
        this.idFreelance = idFreelance;
    }

    public @NotNull(message = "{contrato.projeto.obrigatorio}") UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(@NotNull(message = "{contrato.projeto.obrigatorio}") UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public LocalDate getDataInicioContrato() {
        return dataInicioContrato;
    }

    public void setDataInicioContrato(LocalDate dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }

    public LocalDate getDataFimContrato() {
        return dataFimContrato;
    }

    public void setDataFimContrato(LocalDate dataFimContrato) {
        this.dataFimContrato = dataFimContrato;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

}
