package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.repository.FreelanceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FreelanceService {

    @Autowired
    private FreelanceRepository freelanceRepository;

    public List<FreelanceDTO> listar() {
        return freelanceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FreelanceDTO buscar(UUID id) {
        Freelance freelance = freelanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));
        return toDTO(freelance);
    }

    public FreelanceDTO criar(FreelanceDTO freelanceDTO) {
        Freelance freelance = toEntity(freelanceDTO);
        freelance = freelanceRepository.save(freelance);
        return toDTO(freelance);
    }

    public FreelanceDTO atualizar(UUID id, FreelanceDTO freelanceDTO) {
        Freelance freelanceExistente = freelanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado para atualização"));

        freelanceExistente.setNome(freelanceDTO.getNome());
        freelanceExistente.seteMail(freelanceDTO.geteMail());
        freelanceExistente.setTecnologias(freelanceDTO.getTecnologias());
        freelanceExistente.setEnderecoResidencia(freelanceDTO.getEnderecoResidencia());
        freelanceExistente.setNumeroResidencia(freelanceDTO.getNumeroResidencia());
        freelanceExistente.setComplementoEndereco(freelanceDTO.getComplementoEndereco());
        freelanceExistente.setQtdAnosExperiencia(freelanceDTO.getQtdAnosExperiencia());
        freelanceExistente.setValorHora(freelanceDTO.getValorHora());

        freelanceExistente = freelanceRepository.save(freelanceExistente);
        return toDTO(freelanceExistente);
    }

    public void deletar(UUID id) {
        freelanceRepository.deleteById(id);
    }

    private FreelanceDTO toDTO(Freelance freelance) {
        FreelanceDTO dto = new FreelanceDTO();
        dto.setIdFreelance(freelance.getIdFreelance());
        dto.setNome(freelance.getNome());
        dto.seteMail(freelance.geteMail());
        dto.setTecnologias(freelance.getTecnologias());
        dto.setEnderecoResidencia(freelance.getEnderecoResidencia());
        dto.setNumeroResidencia(freelance.getNumeroResidencia());
        dto.setComplementoEndereco(freelance.getComplementoEndereco());
        dto.setQtdAnosExperiencia(freelance.getQtdAnosExperiencia());
        dto.setValorHora(freelance.getValorHora());
        return dto;
    }

    private Freelance toEntity(FreelanceDTO dto) {
        Freelance freelance = new Freelance();
        freelance.setIdFreelance(dto.getIdFreelance());
        freelance.setNome(dto.getNome());
        freelance.seteMail(dto.geteMail());
        freelance.setTecnologias(dto.getTecnologias());
        freelance.setEnderecoResidencia(dto.getEnderecoResidencia());
        freelance.setNumeroResidencia(dto.getNumeroResidencia());
        freelance.setComplementoEndereco(dto.getComplementoEndereco());
        freelance.setQtdAnosExperiencia(dto.getQtdAnosExperiencia());
        freelance.setValorHora(dto.getValorHora());
        return freelance;
    }

}

