package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaturaRepository extends JpaRepository<Fatura, Long> {
}