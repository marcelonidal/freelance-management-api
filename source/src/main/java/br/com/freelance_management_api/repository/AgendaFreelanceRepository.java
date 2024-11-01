package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.AgendaFreelance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendaFreelanceRepository extends JpaRepository<AgendaFreelance, Long> {

    List<AgendaFreelance> findByFreelance_IdFreelanceAndDataFimGreaterThanEqualAndDataInicioLessThanEqual(UUID freelanceId, LocalDate dataInicio, LocalDate dataFim);
}