package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Set;
import java.util.UUID;

public class ProjetoDTO {

    private UUID idProjeto;

    @NotEmpty(message = "{projeto.nome.obrigatorio}")
    private String nomeProjeto;

    @NotEmpty(message = "{projeto.tempoHoras.obrigatorio}")
    private String tempoEmHoras;

    @NotEmpty(message = "{projeto.empresaContratante.obrigatorio}")
    private String empresaContratanteProjeto;

    private String paisProjeto;

    @NotEmpty(message = "{projeto.tecnologias.obrigatorias}")
    private Set<String> tecnologias;

    @Email(message = "{projeto.emailContato.invalido}")
    @NotEmpty(message = "{projeto.emailContato.obrigatorio}")
    private String emailContato;

    private Boolean cobreCustoFreelance;

    @Positive(message = "{projeto.valorCustoPago.positivo}")
    private Double valorCustoPago;

    @Positive(message = "{projeto.valorHoraPago.positivo}")
    private Double valorHoraPago;

    @NotNull(message = "{projeto.horasPorDia.obrigatorio}")
    @Positive(message = "{projeto.horasPorDia.positivo}")
    private int horasPorDia;

    // Getters e Setters
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

    public int getHorasPorDia() {
        return horasPorDia;
    }

    public void setHorasPorDia(int horasPorDia) {
        this.horasPorDia = horasPorDia;
    }

}