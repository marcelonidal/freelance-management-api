package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.AgendaFreelanceDTO;
import br.com.freelance_management_api.entities.AgendaFreelance;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.repository.AgendaFreelanceRepository;
import br.com.freelance_management_api.repository.FreelanceRepository;
import br.com.freelance_management_api.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgendaFreelanceService {

    private final AgendaFreelanceRepository agendaFreelanceRepository;
    private final FreelanceRepository freelanceRepository;
    private final ProjetoRepository projetoRepository;

    @Autowired
    public AgendaFreelanceService(AgendaFreelanceRepository agendaFreelanceRepository,
                                  FreelanceRepository freelanceRepository,
                                  ProjetoRepository projetoRepository) {
        this.agendaFreelanceRepository = agendaFreelanceRepository;
        this.freelanceRepository = freelanceRepository;
        this.projetoRepository = projetoRepository;
    }

    public List<AgendaFreelanceDTO> listar() {
        return agendaFreelanceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AgendaFreelanceDTO buscar(UUID id) {
        AgendaFreelance agendaFreelance = agendaFreelanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));
        return toDTO(agendaFreelance);
    }

    public AgendaFreelanceDTO criar(AgendaFreelanceDTO agendaFreelanceDTO) {
        AgendaFreelance agendaFreelance = toEntity(agendaFreelanceDTO);
        agendaFreelance = agendaFreelanceRepository.save(agendaFreelance);
        return toDTO(agendaFreelance);
    }

    public AgendaFreelanceDTO atualizar(UUID id, AgendaFreelanceDTO agendaFreelanceDTO) {
        AgendaFreelance existingAgenda = agendaFreelanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado para atualização"));

        existingAgenda.setDataInicio(agendaFreelanceDTO.getDataInicio());
        existingAgenda.setDataFim(agendaFreelanceDTO.getDataFim());
        existingAgenda = agendaFreelanceRepository.save(existingAgenda);
        return toDTO(existingAgenda);
    }

    public void deletar(UUID id) {
        if (!agendaFreelanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento não encontrado para exclusão");
        }
        agendaFreelanceRepository.deleteById(id);
    }

    private AgendaFreelanceDTO toDTO(AgendaFreelance agendaFreelance) {
        AgendaFreelanceDTO dto = new AgendaFreelanceDTO();
        dto.setId(agendaFreelance.getId());
        dto.setIdFreelance(agendaFreelance.getFreelance().getIdFreelance());
        dto.setIdProjeto(agendaFreelance.getProjeto().getIdProjeto());
        dto.setDataInicio(agendaFreelance.getDataInicio());
        dto.setDataFim(agendaFreelance.getDataFim());
        return dto;
    }

    private AgendaFreelance toEntity(AgendaFreelanceDTO dto) {
        AgendaFreelance agendaFreelance = new AgendaFreelance();
        agendaFreelance.setId(dto.getId());

        Freelance freelance = freelanceRepository.findById(dto.getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));
        agendaFreelance.setFreelance(freelance);

        Projeto projeto = projetoRepository.findById(dto.getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        agendaFreelance.setProjeto(projeto);

        agendaFreelance.setDataInicio(dto.getDataInicio());
        agendaFreelance.setDataFim(dto.getDataFim());
        return agendaFreelance;
    }

}
