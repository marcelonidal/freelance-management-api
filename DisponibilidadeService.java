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

    /**
     * Verifica se o freelancer está disponível para o período especificado.
     * @param freelanceId ID do freelancer
     * @param dataInicio Data de início do período
     * @param dataFim Data de término do período
     * @return true se o freelancer estiver disponível, false caso contrário
     */
    public boolean isFreelancerAvailable(UUID freelanceId, LocalDate dataInicio, LocalDate dataFim) {
        // Querying to see if any bookings conflict with the desired period
        return agendaFreelanceRepository
                .findByFreelanceIdAndDataInicioBeforeAndDataFimAfter(freelanceId, dataFim, dataInicio)
                .isEmpty();
    }

    /**
     * Verifica se o projeto pode acomodar um freelancer para o período especificado.
     * @param projetoId ID do projeto
     * @param dataInicio Data de início do período
     * @param dataFim Data de término do período
     * @return true se o projeto puder acomodar, false caso contrário
     */
    public boolean isProjectAvailable(UUID projetoId, LocalDate dataInicio, LocalDate dataFim) {
        // Check if there is any project allocation conflict
        return agendaProjetoRepository
                .findByProjetoIdAndDataInicioBeforeAndDataFimAfter(projetoId, dataFim, dataInicio)
                .isEmpty();
    }

}