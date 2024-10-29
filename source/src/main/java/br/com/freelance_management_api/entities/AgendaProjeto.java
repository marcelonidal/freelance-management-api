package br.com.freelance_management_api.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AgendaProjeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projeto project;

    private LocalDate startDate;
    private LocalDate endDate;

    public AgendaProjeto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Projeto getProject() {
        return project;
    }

    public void setProject(Projeto project) {
        this.project = project;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
