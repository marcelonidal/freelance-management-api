package br.com.freelance_management_api.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idContrato;

    @ManyToOne
    @JoinColumn(name = "id_freelance", nullable = false)
    private Freelance freelance;
    @ManyToOne
    @JoinColumn(name = "id_vaga", nullable = false)
    private Projeto projeto;
    @Temporal(TemporalType.DATE)
    private LocalDate dataInicioContrato;
    @Temporal(TemporalType.DATE)
    private LocalDate dateFimContrato;

    public Contrato() {}

    public Contrato(UUID idContrato, Freelance freelance, Projeto projeto, LocalDate dataInicioContrato, LocalDate dateFimContrato) {
        this.idContrato = idContrato;
        this.freelance = freelance;
        this.projeto = projeto;
        this.dataInicioContrato = dataInicioContrato;
        this.dateFimContrato = dateFimContrato;
    }

    public UUID getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(UUID idContrato) {
        this.idContrato = idContrato;
    }

    public Freelance getFreelance() {
        return freelance;
    }

    public void setFreelance(Freelance freelance) {
        this.freelance = freelance;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
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

    @Override
    public int hashCode() {
        return Objects.hashCode(idContrato);
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato=" + idContrato +
                ", freelance=" + freelance +
                ", projeto=" + projeto +
                ", dataInicioContrato=" + dataInicioContrato +
                ", dateFimContrato=" + dateFimContrato +
                '}';
    }

}
