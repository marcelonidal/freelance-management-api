package br.com.freelance_management_api.repository;


import br.com.freelance_management_api.entities.Contrato;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, UUID> {

    @Query("SELECT c FROM Contrato c WHERE c.freelance = :freelance AND c.projeto = :projeto")
    Optional<Contrato> findByFreelanceAndProjeto(@Param("freelance") Freelance freelance, @Param("projeto") Projeto projeto);
}
