package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.*;
import br.com.freelance_management_api.entities.*;
import br.com.freelance_management_api.repository.*;
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

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @Autowired
    private AgendaFreelanceService agendaFreelanceService;

    @Autowired
    private AgendaProjetoService agendaProjetoService;

    @Autowired
    private AgendaFreelanceRepository agendaFreelanceRepository;

    @Autowired
    private AgendaProjetoRepository agendaProjetoRepository;

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

        boolean disponivel = disponibilidadeService.isFreelanceDisponivel(
                freelance.getIdFreelance(), contratoDTO.getDataInicioContrato(), contratoDTO.getDateFimContrato());
        if (!disponivel) {
            throw new IllegalStateException("O freelancer não está disponível durante o período do contrato.");
        }

        boolean projetoDisponivel = disponibilidadeService.isProjetoDisponivel(
                projeto.getIdProjeto(), contratoDTO.getDataInicioContrato(), contratoDTO.getDateFimContrato());
        if (!projetoDisponivel) {
            throw new IllegalStateException("O projeto não está disponível durante o período do contrato.");
        }

        Contrato contrato = new Contrato();
        contrato.setFreelance(freelance);
        contrato.setProjeto(projeto);
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDateFimContrato(contratoDTO.getDateFimContrato());

        contrato = contratoRepository.save(contrato);

        AgendaFreelanceDTO agendaFreelanceDTO = new AgendaFreelanceDTO();
        agendaFreelanceDTO.setIdFreelance(freelance.getIdFreelance());
        agendaFreelanceDTO.setDataInicio(contratoDTO.getDataInicioContrato());
        agendaFreelanceDTO.setDataFim(contratoDTO.getDateFimContrato());
        agendaFreelanceDTO.setIdProjeto(projeto.getIdProjeto());
        agendaFreelanceService.criar(agendaFreelanceDTO);

        AgendaProjetoDTO agendaProjetoDTO = new AgendaProjetoDTO();
        agendaProjetoDTO.setIdProjeto(projeto.getIdProjeto());
        agendaProjetoDTO.setDataInicio(contratoDTO.getDataInicioContrato());
        agendaProjetoDTO.setDataFim(contratoDTO.getDateFimContrato());
        agendaProjetoService.criar(agendaProjetoDTO);

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

        Freelance freelance = freelanceRepository.findById(contratoDTO.getFreelance().getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(contratoDTO.getProjeto().getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        if (!contratoDTO.isTecnologiasValidas()) {
            throw new IllegalArgumentException("O freelance não possui todas as tecnologias exigidas pelo projeto.");
        }

        contrato.setFreelance(freelance);
        contrato.setProjeto(projeto);
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDateFimContrato(contratoDTO.getDateFimContrato());

        contrato = contratoRepository.save(contrato);

        List<AgendaFreelance> lstAgendaFreelance = agendaFreelanceRepository.findByFreelance_IdFreelanceAndDataFimGreaterThanEqualAndDataInicioLessThanEqual(
                        freelance.getIdFreelance(), contrato.getDataInicioContrato(), contrato.getDateFimContrato());
        if (lstAgendaFreelance.isEmpty()) {
            throw new EntityNotFoundException("Agenda de Freelance não encontrada");
        }

        AgendaFreelance agendaFreelance = lstAgendaFreelance.get(0);

        agendaFreelance.setDataInicio(contrato.getDataInicioContrato());
        agendaFreelance.setDataFim(contrato.getDateFimContrato());
        agendaFreelanceService.atualizar(agendaFreelance.getId(), toAgendaFreelanceDTO(agendaFreelance));

        List<AgendaProjeto> lstAgendaProjeto = agendaProjetoRepository.findByProjeto_IdProjetoAndDataFimGreaterThanEqualAndDataInicioLessThanEqual(
                        projeto.getIdProjeto(), contrato.getDataInicioContrato(), contrato.getDateFimContrato());
        if (lstAgendaProjeto.isEmpty()) {
            throw new EntityNotFoundException("Agenda de Freelance não encontrada");
        }
        AgendaProjeto agendaProjeto = lstAgendaProjeto.get(0);

        agendaProjeto.setDataInicio(contrato.getDataInicioContrato());
        agendaProjeto.setDataFim(contrato.getDateFimContrato());
        agendaProjetoService.atualizar(agendaProjeto.getId(), toAgendaProjetoDTO(agendaProjeto));

        return toDTO(contrato);
    }

    public void deletar(UUID contratoId) {
        if (!contratoRepository.existsById(contratoId)) {
            throw new EntityNotFoundException("Contrato não encontrado para exclusão");
        }

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

    private AgendaFreelanceDTO toAgendaFreelanceDTO(AgendaFreelance agendaFreelance) {
        AgendaFreelanceDTO dto = new AgendaFreelanceDTO();
        dto.setId(agendaFreelance.getId());
        dto.setIdFreelance(agendaFreelance.getFreelance().getIdFreelance());
        dto.setIdProjeto(agendaFreelance.getProjeto().getIdProjeto());
        dto.setDataInicio(agendaFreelance.getDataInicio());
        dto.setDataFim(agendaFreelance.getDataFim());
        return dto;
    }

    private AgendaProjetoDTO toAgendaProjetoDTO(AgendaProjeto agendaProjeto) {
        AgendaProjetoDTO dto = new AgendaProjetoDTO();
        dto.setId(agendaProjeto.getId());
        dto.setIdProjeto(agendaProjeto.getProjeto().getIdProjeto());
        dto.setDataInicio(agendaProjeto.getDataInicio());
        dto.setDataFim(agendaProjeto.getDataFim());
        return dto;
    }

}