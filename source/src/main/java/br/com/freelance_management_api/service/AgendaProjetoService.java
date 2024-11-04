package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.AgendaProjetoDTO;
import br.com.freelance_management_api.entities.AgendaProjeto;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.repository.AgendaProjetoRepository;
import br.com.freelance_management_api.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AgendaProjetoService {

    private final AgendaProjetoRepository agendaProjetoRepository;
    private final ProjetoRepository projetoRepository;

    @Autowired
    public AgendaProjetoService(AgendaProjetoRepository agendaProjetoRepository,
                                ProjetoRepository projetoRepository) {
        this.agendaProjetoRepository = agendaProjetoRepository;
        this.projetoRepository = projetoRepository;
    }

    public List<AgendaProjetoDTO> listar() {
        return agendaProjetoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public AgendaProjetoDTO buscar(UUID id) {
        AgendaProjeto agendaProjeto = agendaProjetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento de projeto não encontrado"));
        return toDTO(agendaProjeto);
    }

    public AgendaProjetoDTO criar(AgendaProjetoDTO agendaProjetoDTO) {
        AgendaProjeto agendaProjeto = toEntity(agendaProjetoDTO);
        agendaProjeto = agendaProjetoRepository.save(agendaProjeto);
        return toDTO(agendaProjeto);
    }

    public AgendaProjetoDTO atualizar(UUID id, AgendaProjetoDTO agendaProjetoDTO) {
        AgendaProjeto agendaExistente = agendaProjetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento de projeto não encontrado para atualização"));

        agendaExistente.setDataInicio(agendaProjetoDTO.getDataInicio());
        agendaExistente.setDataFim(agendaProjetoDTO.getDataFim());
        agendaExistente = agendaProjetoRepository.save(agendaExistente);
        return toDTO(agendaExistente);
    }

    public void deletar(UUID id) {
        if (!agendaProjetoRepository.existsById(id)) {
            throw new EntityNotFoundException("Agendamento de projeto não encontrado para exclusão");
        }
        agendaProjetoRepository.deleteById(id);
    }

    private AgendaProjetoDTO toDTO(AgendaProjeto agendaProjeto) {
        AgendaProjetoDTO dto = new AgendaProjetoDTO();
        dto.setId(agendaProjeto.getId());
        dto.setIdProjeto(agendaProjeto.getProjeto().getIdProjeto());
        dto.setDataInicio(agendaProjeto.getDataInicio());
        dto.setDataFim(agendaProjeto.getDataFim());
        return dto;
    }

    private AgendaProjeto toEntity(AgendaProjetoDTO dto) {
        AgendaProjeto agendaProjeto = new AgendaProjeto();
        agendaProjeto.setId(dto.getId());

        Projeto projeto = projetoRepository.findById(dto.getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        agendaProjeto.setProjeto(projeto);

        agendaProjeto.setDataInicio(dto.getDataInicio());
        agendaProjeto.setDataFim(dto.getDataFim());
        return agendaProjeto;
    }

}