package br.com.freelance_management_api.freelance_management_api.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_cadastrofreelance")
public class CadastroFreeLance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFreelance;

    private String nome;
    private String eMail;
    private String tecnologia;
    private Date   dataDisponivel;
    private String enderecoResidencia;
    private int    numeroResidencia;
    private String complementoEndereco;
    private int    qtdAnosExperiencia;
    private Double valorHora;

    public CadastroFreeLance() {}

    public CadastroFreeLance(UUID idFreelance, String nome, String eMail, String tecnologia, Date dataDisponivel, String enderecoResidencia, int numeroResidencia, String complementoEndereco, int qtdAnosExperiencia, Double valorHora) {
        this.idFreelance = idFreelance;
        this.nome = nome;
        this.eMail = eMail;
        this.tecnologia = tecnologia;
        this.dataDisponivel = dataDisponivel;
        this.enderecoResidencia = enderecoResidencia;
        this.numeroResidencia = numeroResidencia;
        this.complementoEndereco = complementoEndereco;
        this.qtdAnosExperiencia = qtdAnosExperiencia;
        this.valorHora = valorHora;
    }

    public UUID getIdFreelance() {
        return idFreelance;
    }

    public void setIdFreelance(UUID idFreelance) {
        this.idFreelance = idFreelance;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public Date getDataDisponivel() {
        return dataDisponivel;
    }

    public void setDataDisponivel(Date dataDisponivel) {
        this.dataDisponivel = dataDisponivel;
    }

    public String getEnderecoResidencia() {
        return enderecoResidencia;
    }

    public void setEnderecoResidencia(String enderecoResidencia) {
        this.enderecoResidencia = enderecoResidencia;
    }

    public int getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(int numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getComplementoEndereco() {
        return complementoEndereco;
    }

    public void setComplementoEndereco(String complementoEndereco) {
        this.complementoEndereco = complementoEndereco;
    }

    public int getQtdAnosExperiencia() {
        return qtdAnosExperiencia;
    }

    public void setQtdAnosExperiencia(int qtdAnosExperiencia) {
        this.qtdAnosExperiencia = qtdAnosExperiencia;
    }

    public Double getValorHora() {
        return valorHora;
    }

    public void setValorHora(Double valorHora) {
        this.valorHora = valorHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CadastroFreeLance that = (CadastroFreeLance) o;
        return Objects.equals(idFreelance, that.idFreelance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idFreelance);
    }

    @Override
    public String toString() {
        return "CadastroFreeLance{" +
                "idFreelance=" + idFreelance +
                ", nome='" + nome + '\'' +
                ", eMail='" + eMail + '\'' +
                ", tecnologia='" + tecnologia + '\'' +
                ", dataDisponivel=" + dataDisponivel +
                ", enderecoResidencia='" + enderecoResidencia + '\'' +
                ", numeroResidencia=" + numeroResidencia +
                ", complementoEndereco='" + complementoEndereco + '\'' +
                ", qtdAnosExperiencia=" + qtdAnosExperiencia +
                ", valorHora=" + valorHora +
                '}';
    }
}
