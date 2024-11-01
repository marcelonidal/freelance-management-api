package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ContratoDTO {

    private UUID idContrato;

    @NotNull(message = "O freelancer é obrigatório.")
    private FreelanceDTO freelance;
    @NotNull(message = "O projeto é obrigatório.")
    private ProjetoDTO projeto;
    @NotNull(message = "A data de início do contrato é obrigatória.")
    private LocalDate dataInicioContrato;
    @NotNull(message = "A data de término do contrato é obrigatória.")
    private LocalDate dateFimContrato;
    private String emailStatus;

    // Getters e Setters
    public UUID getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(UUID idContrato) {
        this.idContrato = idContrato;
    }

    public @NotNull(message = "O freelancer é obrigatório.") FreelanceDTO getFreelance() {
        return freelance;
    }

    public void setFreelance(@NotNull(message = "O freelancer é obrigatório.") FreelanceDTO freelance) {
        this.freelance = freelance;
    }

    public @NotNull(message = "O projeto é obrigatório.") ProjetoDTO getProjeto() {
        return projeto;
    }

    public void setProjeto(@NotNull(message = "O projeto é obrigatório.") ProjetoDTO projeto) {
        this.projeto = projeto;
    }

    public @NotNull(message = "A data de início do contrato é obrigatória.") LocalDate getDataInicioContrato() {
        return dataInicioContrato;
    }

    public void setDataInicioContrato(@NotNull(message = "A data de início do contrato é obrigatória.") LocalDate dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }

    public @NotNull(message = "A data de término do contrato é obrigatória.") LocalDate getDateFimContrato() {
        return dateFimContrato;
    }

    public void setDateFimContrato(@NotNull(message = "A data de término do contrato é obrigatória.") LocalDate dateFimContrato) {
        this.dateFimContrato = dateFimContrato;
    }

    public String getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(String emailStatus) {
        this.emailStatus = emailStatus;
    }

    public boolean isTecnologiasValidas() {
        if (freelance == null || projeto == null) {
            return false;
        }

        Set<String> freelanceTecnologias = new HashSet<>(freelance.getTecnologias());
        Set<String> projetoTecnologias = new HashSet<>(projeto.getTecnologias());

        return freelanceTecnologias.containsAll(projetoTecnologias);
    }

}
