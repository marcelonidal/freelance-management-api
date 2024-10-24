package br.com.freelance_management_api.freelance_management_api.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_vaga_x_freelance")
public class CadastroVagaXFreeLance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idVagaFreelace;

    private String idFreelance;
    private String IdVaga;
    private Date   dataInicioContrato;
    private Date   dateFimContrato;

    public CadastroVagaXFreeLance () {}

    public CadastroVagaXFreeLance(UUID idVagaFreelace, String idFreelance, String idVaga, Date dataInicioContrato, Date dateFimContrato) {
        this.idVagaFreelace = idVagaFreelace;
        this.idFreelance = idFreelance;
        IdVaga = idVaga;
        this.dataInicioContrato = dataInicioContrato;
        this.dateFimContrato = dateFimContrato;
    }

    public UUID getIdVagaFreelace() {
        return idVagaFreelace;
    }

    public void setIdVagaFreelace(UUID idVagaFreelace) {
        this.idVagaFreelace = idVagaFreelace;
    }

    public String getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(String idFreelance) {
        this.idFreelance = idFreelance;
    }

    public String getIdVaga() {
        return IdVaga;
    }

    public void setIdVaga(String idVaga) {
        IdVaga = idVaga;
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
    public String toString() {
        return "CadastroVagaXFreeLance{" +
                "idVagaFreelace=" + idVagaFreelace +
                ", idFreelance='" + idFreelance + '\'' +
                ", IdVaga='" + IdVaga + '\'' +
                ", dataInicioContrato=" + dataInicioContrato +
                ", dateFimContrato=" + dateFimContrato +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CadastroVagaXFreeLance that = (CadastroVagaXFreeLance) o;
        return Objects.equals(idVagaFreelace, that.idVagaFreelace);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idVagaFreelace);
    }
}
