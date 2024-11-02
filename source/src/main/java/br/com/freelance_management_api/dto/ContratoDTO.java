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

    @NotNull(message = "{contrato.freelance.obrigatorio}")
    private FreelanceDTO freelance;

    @NotNull(message = "{contrato.projeto.obrigatorio}")
    private ProjetoDTO projeto;

    @NotNull(message = "{contrato.dataInicioContrato.obrigatoria}")
    private LocalDate dataInicioContrato;

    @NotNull(message = "{contrato.dataFimContrato.obrigatoria}")
    private LocalDate dateFimContrato;
    private String emailStatus;

    // Getters e Setters
    public UUID getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(UUID idContrato) {
        this.idContrato = idContrato;
    }

    public FreelanceDTO getFreelance() {
        return freelance;
    }

    public void setFreelance(FreelanceDTO freelance) {
        this.freelance = freelance;
    }

    public ProjetoDTO getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoDTO projeto) {
        this.projeto = projeto;
    }

    public LocalDate getDataInicioContrato() {
        return dataInicioContrato;
    }

    public void setDataInicioContrato(LocalDate dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }

    public LocalDate getDateFimContrato() {
        return dateFimContrato;
    }

    public void setDateFimContrato(LocalDate dateFimContrato) {
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
