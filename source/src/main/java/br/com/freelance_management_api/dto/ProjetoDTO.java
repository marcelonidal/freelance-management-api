package br.com.freelance_management_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.Set;
import java.util.UUID;

public class ProjetoDTO {

    private UUID idProjeto;

    @NotEmpty(message = "O nome do projeto é obrigatório.")
    private String nomeProjeto;
    @NotEmpty(message = "O tempo em horas é obrigatório.")
    private String tempoEmHoras;
    @NotEmpty(message = "A empresa contratante é obrigatória.")
    private String empresaContratanteProjeto;
    private String paisProjeto;
    @NotEmpty(message = "As tecnologias do projeto são obrigatórias.")
    private Set<String> tecnologias;
    @Email(message = "E-mail de contato inválido.")
    @NotEmpty(message = "O e-mail de contato é obrigatório.")
    private String emailContato;
    private Boolean cobreCustoFreelance;
    @Positive(message = "O valor do custo pago deve ser positivo.")
    private Double valorCustoPago;
    @Positive(message = "O valor por hora pago deve ser positivo.")
    private Double valorHoraPago;
    @NotEmpty(message = "O valor em horas por dia é obrigatório.")
    @Positive(message = "O valor de horas por dia deve ser um número positivo.")
    private int horasPorDia;

    // Getters e Setters
    public UUID getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(UUID idProjeto) {
        this.idProjeto = idProjeto;
    }

    public @NotEmpty(message = "O nome do projeto é obrigatório.") String getNomeProjeto() {
        return nomeProjeto;
    }

    public void setNomeProjeto(@NotEmpty(message = "O nome do projeto é obrigatório.") String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }

    public @NotEmpty(message = "O tempo em horas é obrigatório.") String getTempoEmHoras() {
        return tempoEmHoras;
    }

    public void setTempoEmHoras(@NotEmpty(message = "O tempo em horas é obrigatório.") String tempoEmHoras) {
        this.tempoEmHoras = tempoEmHoras;
    }

    public @NotEmpty(message = "A empresa contratante é obrigatória.") String getEmpresaContratanteProjeto() {
        return empresaContratanteProjeto;
    }

    public void setEmpresaContratanteProjeto(@NotEmpty(message = "A empresa contratante é obrigatória.") String empresaContratanteProjeto) {
        this.empresaContratanteProjeto = empresaContratanteProjeto;
    }

    public String getPaisProjeto() {
        return paisProjeto;
    }

    public void setPaisProjeto(String paisProjeto) {
        this.paisProjeto = paisProjeto;
    }

    public @NotEmpty(message = "As tecnologias do projeto são obrigatórias.") Set<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(@NotEmpty(message = "As tecnologias do projeto são obrigatórias.") Set<String> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public @Email(message = "E-mail de contato inválido.") @NotEmpty(message = "O e-mail de contato é obrigatório.") String getEmailContato() {
        return emailContato;
    }

    public void setEmailContato(@Email(message = "E-mail de contato inválido.") @NotEmpty(message = "O e-mail de contato é obrigatório.") String emailContato) {
        this.emailContato = emailContato;
    }

    public Boolean getCobreCustoFreelance() {
        return cobreCustoFreelance;
    }

    public void setCobreCustoFreelance(Boolean cobreCustoFreelance) {
        this.cobreCustoFreelance = cobreCustoFreelance;
    }

    public @Positive(message = "O valor do custo pago deve ser positivo.") Double getValorCustoPago() {
        return valorCustoPago;
    }

    public void setValorCustoPago(@Positive(message = "O valor do custo pago deve ser positivo.") Double valorCustoPago) {
        this.valorCustoPago = valorCustoPago;
    }

    public @Positive(message = "O valor por hora pago deve ser positivo.") Double getValorHoraPago() {
        return valorHoraPago;
    }

    public void setValorHoraPago(@Positive(message = "O valor por hora pago deve ser positivo.") Double valorHoraPago) {
        this.valorHoraPago = valorHoraPago;
    }

    @NotEmpty(message = "O valor em horas é obrigatório.")
    public int getHorasPorDia() {
        return horasPorDia;
    }

    public void setHorasPorDia(@NotEmpty(message = "O valor em horas é obrigatório.") int horasPorDia) {
        this.horasPorDia = horasPorDia;
    }

}