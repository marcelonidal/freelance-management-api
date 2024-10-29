package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.AgendaFreelance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AgendaFreelanceRepository extends JpaRepository<AgendaFreelance, Long> {

    @Query("SELECT a FROM AgendaFreelance a WHERE a.freelancer.id = :freelanceId " +
            "AND (a.startDate <= :endDate AND a.endDate >= :startDate)")
    List<AgendaFreelance> findByFreelanceIdAndDateRange(@Param("freelanceId") Long freelanceId,
                                                         @Param("startDate") LocalDate startDate,
                                                         @Param("endDate") LocalDate endDate);

    List<AgendaFreelance> findByFreelancer_IdFreelanceAndStartDateBeforeAndEndDateAfter(UUID freelanceId, LocalDate dataFim, LocalDate dataInicio);
}