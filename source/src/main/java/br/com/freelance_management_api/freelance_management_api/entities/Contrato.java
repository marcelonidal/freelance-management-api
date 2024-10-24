package br.com.freelance_management_api.freelance_management_api.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idContrato;

    private String idFreelance;
    private String idVaga;
    private Date dataInicioContrato;
    private Date dateFimContrato;

    public Contrato() {}

    public Contrato(UUID idContrato, String idFreelance, String idVaga, Date dataInicioContrato, Date dateFimContrato) {
        this.idContrato = idContrato;
        this.idFreelance = idFreelance;
        this.idVaga = idVaga;
        this.dataInicioContrato = dataInicioContrato;
        this.dateFimContrato = dateFimContrato;
    }

    public UUID getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(UUID idContrato) {
        this.idContrato = idContrato;
    }

    public String getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(String idFreelance) {
        this.idFreelance = idFreelance;
    }

    public String getIdVaga() {
        return idVaga;
    }

    public void setIdVaga(String idVaga) {
        this.idVaga = idVaga;
    }

    public Date getDataInicioContrato() {
        return dataInicioContrato;
    }

    public void setDataInicioContrato(Date dataInicioContrato) {
        this.dataInicioContrato = dataInicioContrato;
    }

    public Date getDateFimContrato() {
        return dateFimContrato;
    }

    public void setDateFimContrato(Date dateFimContrato) {
        this.dateFimContrato = dateFimContrato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return Objects.equals(idContrato, contrato.idContrato);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idContrato);
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "idContrato=" + idContrato +
                ", idFreelance='" + idFreelance + '\'' +
                ", idVaga='" + idVaga + '\'' +
                ", dataInicioContrato=" + dataInicioContrato +
                ", dateFimContrato=" + dateFimContrato +
                '}';
    }

}
