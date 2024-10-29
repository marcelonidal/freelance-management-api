package br.com.freelance_management_api.service;

import br.com.freelance_management_api.repository.AgendaFreelanceRepository;
import br.com.freelance_management_api.repository.AgendaProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class DisponibilidadeService {

    @Autowired
    private AgendaFreelanceRepository agendaFreelanceRepository;

    @Autowired
    private AgendaProjetoRepository agendaProjetoRepository;

    public boolean isFreelanceDisponivel(UUID freelanceId, LocalDate dataInicio, LocalDate dataFim) {
        return agendaFreelanceRepository
                .findByFreelancer_IdFreelanceAndStartDateBeforeAndEndDateAfter(freelanceId, dataFim, dataInicio)
                .isEmpty();
    }

    public boolean isProjetoDisponivel(UUID projetoId, LocalDate dataInicio, LocalDate dataFim) {
        return agendaProjetoRepository
                .findByProject_IdProjetoAndStartDateBeforeAndEndDateAfter(projetoId, dataFim, dataInicio)
                .isEmpty();
    }

}
