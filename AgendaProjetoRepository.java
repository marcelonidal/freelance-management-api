package br.com.freelance_management_api.repository;

import br.com.freelance_management_api.entities.AgendaProjeto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AgendaProjetoRepository extends JpaRepository<AgendaProjeto, UUID> {

    List<AgendaProjeto> findByProjetoIdAndFreelanceIdAndDataInicioBeforeAndDataFimAfter(
            UUID projetoId, UUID freelanceId, LocalDate dataFim, LocalDate dataInicio);

    List<AgendaProjeto> findByFreelanceIdAndDataInicioBeforeAndDataFimAfter(UUID freelanceId, LocalDate dataFim, LocalDate dataInicio);

    List<AgendaProjeto> findByFreelanceId(UUID freelanceId);

    List<AgendaProjeto> findByProjetoId(UUID projetoId);

    List<AgendaProjeto> findByFreelanceIdAndDataFimAfter(UUID freelanceId, LocalDate now);

    List<AgendaProjeto> findByProjetoIdAndDataFimAfter(UUID projetoId, LocalDate now);
}
