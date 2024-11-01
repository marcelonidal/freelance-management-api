package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.ContratoDTO;
import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.Contrato;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.repository.ContratoRepository;
import br.com.freelance_management_api.repository.FreelanceRepository;
import br.com.freelance_management_api.repository.ProjetoRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private FreelanceRepository freelanceRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EmailService emailService;

    public List<ContratoDTO> listar() {
        return contratoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContratoDTO buscar(UUID contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado"));
        return toDTO(contrato);
    }

    public ContratoDTO criar(ContratoDTO contratoDTO) {
        Freelance freelance = freelanceRepository.findById(contratoDTO.getFreelance().getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));
        Projeto projeto = projetoRepository.findById(contratoDTO.getProjeto().getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        if (!contratoDTO.isTecnologiasValidas()) {
            throw new IllegalArgumentException("O freelance não possui todas as tecnologias exigidas pelo projeto.");
        }

        Contrato contrato = new Contrato();
        contrato.setFreelance(freelance);
        contrato.setProjeto(projeto);
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDateFimContrato(contratoDTO.getDateFimContrato());

        contrato = contratoRepository.save(contrato);

        ContratoDTO contratoMsgDTO = toDTO(contrato);


        try {
            emailService.enviarNotificacaoContrato(
                    freelance.geteMail(),
                    projeto.getNomeProjeto(),
                    projeto.getEmailContato()
            );
            contratoDTO.setEmailStatus("sucesso");
        } catch (MessagingException e) {
            contratoDTO.setEmailStatus("falha: " + e.getMessage());
        }

        return contratoMsgDTO;
    }

    public ContratoDTO atualizar(UUID id, ContratoDTO contratoDTO) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado para atualização"));

        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDateFimContrato(contratoDTO.getDateFimContrato());
        contrato = contratoRepository.save(contrato);
        return toDTO(contrato);
    }

    public void deletar(UUID contratoId) {
        contratoRepository.deleteById(contratoId);
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
        dto.setTempoEmHoras(projeto.getTempoEmHoras());
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