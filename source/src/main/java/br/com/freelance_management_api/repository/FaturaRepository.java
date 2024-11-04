package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.Fatura;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, UUID> {

    Optional<Fatura> findByFreelanceAndProjeto(Freelance freelance, Projeto projeto);

}