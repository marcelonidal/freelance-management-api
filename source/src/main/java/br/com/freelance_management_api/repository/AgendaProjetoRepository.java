package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.AgendaProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendaProjetoRepository extends JpaRepository<AgendaProjeto, UUID> {

    List<AgendaProjeto> findByProjeto_IdProjetoAndDataFimGreaterThanEqualAndDataInicioLessThanEqual(UUID idProjeto, LocalDate dataInicio, LocalDate dataFim);
}