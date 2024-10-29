package br.com.freelance_management_api.entities;


import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_cadastroprojeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idProjeto;

    private String nomeProjeto;
    private String tempoEmHoras;
    private String empresaContratanteProjeto;
    private String paisProjeto;
    @ElementCollection
    @CollectionTable(name = "projeto_tecnologias", joinColumns = @JoinColumn(name = "projeto_id"))
    @Column(name = "tecnologia")
    private Set<String> tecnologias = new HashSet<>();
    private String emailContato;
    private Boolean cobreCustoFreelance;
    private Double valorCustoPago;
    private Double valorHoraPago;

    public Projeto() {
    }

    public Projeto(UUID idProjeto, String nomeProjeto, String tempoEmHoras, String empresaContratanteProjeto, String paisProjeto, Set<String> tecnologias,
                   String emailContato, Boolean cobreCustoFreelance, Double valorCustoPago, Double valorHoraPago) {
        this.idProjeto = idProjeto;
        this.nomeProjeto = nomeProjeto;
        this.tempoEmHoras = tempoEmHoras;
        this.empresaContratanteProjeto = empresaContratanteProjeto;
        this.paisProjeto = paisProjeto;
        this.tecnologias = tecnologias;
        this.emailContato = emailContato;
        this.cobreCustoFreelance = cobreCustoFreelance;
        this.valorCustoPago = valorCustoPago;
        this.valorHoraPago = valorHoraPago;
    }

    public UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public String getTempoEmHoras() {
        return tempoEmHoras;
    }

    public void setTempoEmHoras(String tempoEmHoras) {
        this.tempoEmHoras = tempoEmHoras;
    }

    public String getEmpresaContratanteProjeto() {
        return empresaContratanteProjeto;
    }

    public void setEmpresaContratanteProjeto(String empresaContratanteProjeto) {
        this.empresaContratanteProjeto = empresaContratanteProjeto;
    }

    public String getPaisProjeto() {
        return paisProjeto;
    }

    public void setPaisProjeto(String paisProjeto) {
        this.paisProjeto = paisProjeto;
    }

    public Set<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(Set<String> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(String emailContato) {
        this.emailContato = emailContato;
    }

    public Boolean getCobreCustoFreelance() {
        return cobreCustoFreelance;
    }

    public void setCobreCustoFreelance(Boolean cobreCustoFreelance) {
        this.cobreCustoFreelance = cobreCustoFreelance;
    }

    public Double getValorCustoPago() {
        return valorCustoPago;
    }

    public void setValorCustoPago(Double valorCustoPago) {
        this.valorCustoPago = valorCustoPago;
    }

    public Double getValorHoraPago() {
        return valorHoraPago;
    }

    public void setValorHoraPago(Double valorHoraPago) {
        this.valorHoraPago = valorHoraPago;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projeto projeto = (Projeto) o;
        return Objects.equals(idProjeto, projeto.idProjeto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idProjeto);
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "idProjeto=" + idProjeto +
                ", nomeProjeto='" + nomeProjeto + '\'' +
                ", tempoEmHoras='" + tempoEmHoras + '\'' +
                ", empresaContratanteProjeto='" + empresaContratanteProjeto + '\'' +
                ", paisProjeto='" + paisProjeto + '\'' +
                ", tecnologias=" + tecnologias +
                ", emailContato='" + emailContato + '\'' +
                ", cobreCustoFreelance=" + cobreCustoFreelance +
                ", valorCustoPago=" + valorCustoPago +
                ", valorHoraPago=" + valorHoraPago +
                '}';
    }

}
