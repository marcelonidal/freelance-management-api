package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class AgendaFreelanceDTO {

    private UUID id;

    @NotNull(message = "{agendaFreelance.idFreelance.notNull}")
    private UUID idFreelance;

    @NotNull(message = "{agendaFreelance.idProjeto.notNull}")
    private UUID idProjeto;

    @NotNull(message = "{agendaFreelance.dataInicio.notNull}")
    private LocalDate dataInicio;

    @NotNull(message = "{agendaFreelance.dataFim.notNull}")
    private LocalDate dataFim;

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(UUID idFreelance) {
        this.idFreelance = idFreelance;
    }

    public UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

}
