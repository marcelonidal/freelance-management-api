package br.com.freelance_management_api.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class AgendaFreelance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "freelancer_id")
    private Freelance freelancer;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Projeto project;

    private LocalDate startDate;
    private LocalDate endDate;

    public AgendaFreelance() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Freelance getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelance freelancer) {
        this.freelancer = freelancer;
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
