package br.com.freelance_management_api.service;

import br.com.freelance_management_api.controller.excetion.ControllerNotFoundException;
import br.com.freelance_management_api.dto.ContratoDTO;
import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.Contrato;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.repository.ContratoRepository;
import br.com.freelance_management_api.repository.FreelanceRepository;
import br.com.freelance_management_api.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private FreelanceRepository freelanceRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public ContratoDTO criarContrato(UUID freelanceId, UUID projetoId, LocalDate dataInicio, LocalDate dataFim) {

        Freelance freelance = freelanceRepository.findById(freelanceId)
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        Contrato contrato = new Contrato();
        contrato.setFreelance(freelance);
        contrato.setProjeto(projeto);
        contrato.setDataInicioContrato(dataInicio);
        contrato.setDateFimContrato(dataFim);

        contrato = contratoRepository.save(contrato);
        return toDTO(contrato);
    }

    public ContratoDTO buscarContrato(UUID contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado"));
        return toDTO(contrato);
    }

    private ContratoDTO toDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setIdContrato(contrato.getIdContrato());
        dto.setFreelance(toFreelanceDTO(contrato.getFreelance()));
        dto.setProjeto(toProjetoDTO(contrato.getProjeto()));
        dto.setDataInicioContrato(contrato.getDataInicioContrato());
        dto.setDateFimContrato(contrato.getDateFimContrato());
        return dto;
    }

    private FreelanceDTO toFreelanceDTO(Freelance freelance) {
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

    private ProjetoDTO toProjetoDTO(Projeto projeto) {
        ProjetoDTO dto = new ProjetoDTO();
        dto.setIdProjeto(projeto.getIdProjeto());
        dto.setNomeProjeto(projeto.getNomeProjeto());
        dto.setEmpresaContratanteProjeto(projeto.getEmpresaContratanteProjeto());
        dto.setPaisProjeto(projeto.getPaisProjeto());
        dto.setTecnologias(projeto.getTecnologias());
        dto.setEmailContato(projeto.getEmailContato());
        dto.setCobreCustoFreelance(projeto.getCobreCustoFreelance());
        dto.setValorCustoPago(projeto.getValorCustoPago());
        dto.setValorHoraPago(projeto.getValorHoraPago());
        return dto;
    }

}