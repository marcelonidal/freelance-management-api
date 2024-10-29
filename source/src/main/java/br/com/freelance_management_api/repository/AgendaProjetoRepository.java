package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.AgendaProjeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AgendaProjetoRepository extends JpaRepository<AgendaProjeto, Long> {

    List<AgendaProjeto> findByProject_IdProjetoAndStartDateBeforeAndEndDateAfter(UUID idProjeto, LocalDate endDate, LocalDate startDate);
}