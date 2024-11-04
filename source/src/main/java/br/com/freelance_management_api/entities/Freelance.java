package br.com.freelance_management_api.entities;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "tb_freelance")
public class Freelance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idFreelance;

    private String nome;
    private String eMail;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "freelance_tecnologias", joinColumns = @JoinColumn(name = "freelance_id"))
    @Column(name = "tecnologia")
    private Set<String> tecnologias = new HashSet<>();
    private String enderecoResidencia;
    private int numeroResidencia;
    private String complementoEndereco;
    private int qtdAnosExperiencia;
    private Double valorHora;

    public Freelance() {
    }

    public Freelance(UUID idFreelance, String nome, String eMail, Set<String> tecnologias, String enderecoResidencia,
                     int numeroResidencia, String complementoEndereco, int qtdAnosExperiencia, Double valorHora) {
        this.idFreelance = idFreelance;
        this.nome = nome;
        this.eMail = eMail;
        this.tecnologias = tecnologias;
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

    public Set<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(Set<String> tecnologias) {
        this.tecnologias = tecnologias;
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
        Freelance freeLance = (Freelance) o;
        return Objects.equals(idFreelance, freeLance.idFreelance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idFreelance);
    }

    @Override
    public String toString() {
        return "Freelance{" +
                "idFreelance=" + idFreelance +
                ", nome='" + nome + '\'' +
                ", eMail='" + eMail + '\'' +
                ", tecnologias=" + tecnologias +
                ", enderecoResidencia='" + enderecoResidencia + '\'' +
                ", numeroResidencia=" + numeroResidencia +
                ", complementoEndereco='" + complementoEndereco + '\'' +
                ", qtdAnosExperiencia=" + qtdAnosExperiencia +
                ", valorHora=" + valorHora +
                '}';
    }

}
