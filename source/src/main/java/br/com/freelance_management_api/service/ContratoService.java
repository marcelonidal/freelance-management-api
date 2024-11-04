package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.*;
import br.com.freelance_management_api.entities.*;
import br.com.freelance_management_api.repository.*;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final FreelanceRepository freelanceRepository;
    private final ProjetoRepository projetoRepository;
    private final EmailService emailService;
    private final DisponibilidadeService disponibilidadeService;
    private final AgendaFreelanceService agendaFreelanceService;
    private final AgendaProjetoService agendaProjetoService;
    private final AgendaFreelanceRepository agendaFreelanceRepository;
    private final AgendaProjetoRepository agendaProjetoRepository;
    private final FaturaService faturaService;

    public ContratoService(ContratoRepository contratoRepository,
                           FreelanceRepository freelanceRepository,
                           ProjetoRepository projetoRepository,
                           EmailService emailService,
                           DisponibilidadeService disponibilidadeService,
                           AgendaFreelanceService agendaFreelanceService,
                           AgendaProjetoService agendaProjetoService,
                           AgendaFreelanceRepository agendaFreelanceRepository,
                           AgendaProjetoRepository agendaProjetoRepository,
                           FaturaService faturaService) {
        this.contratoRepository = contratoRepository;
        this.freelanceRepository = freelanceRepository;
        this.projetoRepository = projetoRepository;
        this.emailService = emailService;
        this.disponibilidadeService = disponibilidadeService;
        this.agendaFreelanceService = agendaFreelanceService;
        this.agendaProjetoService = agendaProjetoService;
        this.agendaFreelanceRepository = agendaFreelanceRepository;
        this.agendaProjetoRepository = agendaProjetoRepository;
        this.faturaService = faturaService;
    }

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
        Freelance freelance = freelanceRepository.findById(contratoDTO.getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(contratoDTO.getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        if (!isTecnologiasValidas(contratoDTO.getIdFreelance(), contratoDTO.getIdProjeto())) {
            throw new IllegalArgumentException("O freelance não possui todas as tecnologias exigidas pelo projeto.");
        }

        if (!isAnosExperienciaValido(contratoDTO.getIdFreelance(), contratoDTO.getIdProjeto())) {
            throw new IllegalArgumentException("O freelance não possui anos de experiência exigida pelo projeto.");
        }

        boolean disponivel = disponibilidadeService.isFreelanceDisponivel(
                freelance.getIdFreelance(), contratoDTO.getDataInicioContrato(), contratoDTO.getDataFimContrato());
        if (!disponivel) {
            throw new IllegalStateException("O freelancer não está disponível durante o período do contrato.");
        }

        boolean projetoDisponivel = disponibilidadeService.isProjetoDisponivel(
                projeto.getIdProjeto(), contratoDTO.getDataInicioContrato(), contratoDTO.getDataFimContrato());
        if (!projetoDisponivel) {
            throw new IllegalStateException("O projeto não está disponível durante o período do contrato.");
        }

        Contrato contrato = new Contrato();
        contrato.setFreelance(freelance);
        contrato.setProjeto(projeto);
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDateFimContrato(contratoDTO.getDataFimContrato());

        contrato = contratoRepository.save(contrato);

        AgendaFreelanceDTO agendaFreelanceDTO = new AgendaFreelanceDTO();
        agendaFreelanceDTO.setIdFreelance(freelance.getIdFreelance());
        agendaFreelanceDTO.setDataInicio(contratoDTO.getDataInicioContrato());
        agendaFreelanceDTO.setDataFim(contratoDTO.getDataFimContrato());
        agendaFreelanceDTO.setIdProjeto(projeto.getIdProjeto());
        agendaFreelanceService.criar(agendaFreelanceDTO);

        AgendaProjetoDTO agendaProjetoDTO = new AgendaProjetoDTO();
        agendaProjetoDTO.setIdProjeto(projeto.getIdProjeto());
        agendaProjetoDTO.setDataInicio(contratoDTO.getDataInicioContrato());
        agendaProjetoDTO.setDataFim(contratoDTO.getDataFimContrato());
        agendaProjetoService.criar(agendaProjetoDTO);

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

        FaturaDTO faturaDTO = new FaturaDTO();
        faturaDTO.setDataEmissao(contratoDTO.getDataInicioContrato());
        faturaDTO.setDataVencimento(contratoDTO.getDataInicioContrato().plusDays(projeto.getDiasVctoFatura()));
        faturaDTO.setIdFreelance(freelance.getIdFreelance());
        faturaDTO.setIdProjeto(projeto.getIdProjeto());

        faturaService.criar(faturaDTO);

        return toDTO(contrato);
    }

    public ContratoDTO atualizar(UUID id, ContratoDTO contratoDTO) {
        Contrato contrato = contratoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contrato não encontrado para atualização"));

        Freelance freelance = freelanceRepository.findById(contratoDTO.getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(contratoDTO.getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        if (!isTecnologiasValidas(contratoDTO.getIdFreelance(), contratoDTO.getIdProjeto())) {
            throw new IllegalArgumentException("O freelance não possui todas as tecnologias exigidas pelo projeto.");
        }

        if (!isAnosExperienciaValido(contratoDTO.getIdFreelance(), contratoDTO.getIdProjeto())) {
            throw new IllegalArgumentException("O freelance não possui anos de experiência exigida pelo projeto.");
        }

        contrato.setFreelance(freelance);
        contrato.setProjeto(projeto);
        contrato.setDataInicioContrato(contratoDTO.getDataInicioContrato());
        contrato.setDateFimContrato(contratoDTO.getDataFimContrato());

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

        FaturaDTO faturaDTOExistente = faturaService.buscarFaturaPorFreelanceEProjeto(freelance, projeto);
        if (faturaDTOExistente != null && !StatusFatura.PAGO.equals(faturaDTOExistente.getStatus())) {
            faturaDTOExistente.setDataEmissao(contratoDTO.getDataInicioContrato());
            faturaDTOExistente.setDataVencimento(contratoDTO.getDataInicioContrato().plusDays(projeto.getDiasVctoFatura()));
            faturaService.atualizar(faturaDTOExistente.getId(), faturaDTOExistente);
        }

        return toDTO(contrato);
    }

    public void deletar(UUID contratoId) {
        if (!contratoRepository.existsById(contratoId)) {
            throw new EntityNotFoundException("Contrato não encontrado para exclusão");
        }

        contratoRepository.deleteById(contratoId);
    }

    public boolean isTecnologiasValidas(UUID idFreelance, UUID idProjeto) {
        Freelance freelance = freelanceRepository.findById(idFreelance)
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        Set<String> freelanceTecnologias = freelance.getTecnologias() != null ? new HashSet<>(freelance.getTecnologias()) : new HashSet<>();
        Set<String> projetoTecnologias = projeto.getTecnologias() != null ? new HashSet<>(projeto.getTecnologias()) : new HashSet<>();

        return freelanceTecnologias.containsAll(projetoTecnologias);
    }

    public boolean isAnosExperienciaValido(UUID idFreelance, UUID idProjeto) {
        Freelance freelance = freelanceRepository.findById(idFreelance)
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(idProjeto)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        return freelance.getQtdAnosExperiencia() >= projeto.getAnosExperiencia();
    }

    private ContratoDTO toDTO(Contrato contrato) {
        ContratoDTO dto = new ContratoDTO();
        dto.setIdContrato(contrato.getIdContrato());
        dto.setIdFreelance(contrato.getFreelance() != null ? contrato.getFreelance().getIdFreelance() : null);
        dto.setIdProjeto(contrato.getProjeto() != null ? contrato.getProjeto().getIdProjeto() : null);
        dto.setDataInicioContrato(contrato.getDataInicioContrato());
        dto.setDataFimContrato(contrato.getDateFimContrato());
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
        dto.setAnosExperiencia(projeto.getAnosExperiencia());
        dto.setDiasVctoFatura(projeto.getDiasVctoFatura());
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