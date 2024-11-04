package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.FaturaDTO;
import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.*;
import br.com.freelance_management_api.repository.FaturaRepository;
import br.com.freelance_management_api.repository.FreelanceRepository;
import br.com.freelance_management_api.repository.ProjetoRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FaturaService {

    private final FaturaRepository faturaRepository;
    private final EmailService emailService;
    private final FreelanceRepository freelanceRepository;
    private final ProjetoRepository projetoRepository;

    @Autowired
    public FaturaService(FaturaRepository faturaRepository, EmailService emailService, FreelanceRepository freelanceRepository,ProjetoRepository projetoRepository) {
        this.faturaRepository = faturaRepository;
        this.emailService = emailService;
        this.freelanceRepository = freelanceRepository;
        this.projetoRepository = projetoRepository;
    }

    public List<FaturaDTO> listar() {
        return faturaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<FaturaDTO> buscar(UUID id) {
        return faturaRepository.findById(id).map(this::toDTO);
    }

    public FaturaDTO buscarFaturaPorFreelanceEProjeto(Freelance freelance, Projeto projeto) {
        return faturaRepository.findByFreelanceAndProjeto(freelance, projeto)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Fatura não encontrada para o Freelance e Projeto"));
    }

    public FaturaDTO criar(FaturaDTO faturaDTO) {

        Freelance freelance = freelanceRepository.findById(faturaDTO.getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(faturaDTO.getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        Fatura fatura = toEntity(faturaDTO);

        fatura.setFreelance(freelance);
        fatura.setProjeto(projeto);

        if (freelance != null && fatura.getDataEmissao() != null && fatura.getDataVencimento() != null) {
            double custoTotal = calcularValor(freelance, projeto, fatura.getDataEmissao(), fatura.getDataVencimento());
            fatura.setValor(custoTotal);
            fatura.setStatus(StatusFatura.PENDENTE);
        }

        fatura = faturaRepository.save(fatura);
        FaturaDTO faturaCriada = toDTO(fatura);

        faturaCriada.setIdFreelance(fatura.getFreelance().getIdFreelance());
        faturaCriada.setIdProjeto(fatura.getProjeto().getIdProjeto());

        try {
            emailService.enviarPedidoPagamento(
                fatura.getFreelance().geteMail(),
                fatura.getProjeto().getEmailContato(),
                fatura.getValor()
            );
            faturaCriada.setEmailStatus("sucesso");
        } catch (MessagingException e) {
            faturaCriada.setEmailStatus("falha: " + e.getMessage());
        }

        return faturaCriada;
    }

    public FaturaDTO atualizar(UUID id, FaturaDTO faturaDTO) {
        Fatura faturaExistente = faturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fatura não encontrada para atualização"));

        Freelance freelance = freelanceRepository.findById(faturaDTO.getIdFreelance())
                .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));

        Projeto projeto = projetoRepository.findById(faturaDTO.getIdProjeto())
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));

        if (!StatusFatura.PAGO.equals(faturaExistente.getStatus())) {
            faturaExistente.setFreelance(freelance);
            faturaExistente.setProjeto(projeto);

            if (freelance != null && projeto != null) {
                double custoTotal = calcularValor(freelance, projeto, faturaExistente.getDataEmissao(), faturaExistente.getDataVencimento());
                faturaExistente.setValor(custoTotal);

                try {
                    emailService.enviarPedidoPagamento(
                            freelance.geteMail(),
                            projeto.getEmailContato(),
                            faturaExistente.getValor()
                    );
                    faturaDTO.setEmailStatus("sucesso");
                } catch (MessagingException e) {
                    faturaDTO.setEmailStatus("falha: " + e.getMessage());
                }
            }
        }

        faturaExistente.setStatus(faturaDTO.getStatus());

        faturaExistente = faturaRepository.save(faturaExistente);

        FaturaDTO updatedFaturaDTO = toDTO(faturaExistente);
        updatedFaturaDTO.setEmailStatus(faturaDTO.getEmailStatus());

        return updatedFaturaDTO;
    }

    public void deletar(UUID id) {
        if (!faturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrato não encontrado para exclusão");
        }

        faturaRepository.deleteById(id);
    }

    private double calcularValor(Freelance freelance, Projeto projeto, LocalDate dataEmissao, LocalDate dataVencimento) {
        long duracaoDias = dataVencimento.toEpochDay() - dataEmissao.toEpochDay();
        double taxaHora = freelance.getValorHora();
        int horasPorDia = projeto.getHorasPorDia();
        return taxaHora * horasPorDia * duracaoDias;
    }

    private FaturaDTO toDTO(Fatura fatura) {
        FaturaDTO dto = new FaturaDTO();
        dto.setId(fatura.getId());
        dto.setValor(fatura.getValor());
        dto.setStatus(fatura.getStatus());
        dto.setDataEmissao(fatura.getDataEmissao());
        dto.setDataVencimento(fatura.getDataVencimento());

        dto.setIdFreelance(fatura.getFreelance() != null ? fatura.getFreelance().getIdFreelance() : null);
        dto.setIdProjeto(fatura.getProjeto() != null ? fatura.getProjeto().getIdProjeto() : null);

        return dto;
    }

    private Fatura toEntity(FaturaDTO dto) {
        Fatura fatura = new Fatura();
        fatura.setId(dto.getId());
        fatura.setValor(dto.getValor());
        fatura.setStatus(dto.getStatus());
        fatura.setDataEmissao(dto.getDataEmissao());
        fatura.setDataVencimento(dto.getDataVencimento());

        if (dto.getIdFreelance() != null) {
            Freelance freelance = freelanceRepository.findById(dto.getIdFreelance())
                    .orElseThrow(() -> new EntityNotFoundException("Freelance não encontrado"));
            fatura.setFreelance(freelance);
        }

        if (dto.getIdProjeto() != null) {
            Projeto projeto = projetoRepository.findById(dto.getIdProjeto())
                    .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
            fatura.setProjeto(projeto);
        }

        return fatura;
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

    private Freelance toFreelanceEntity(FreelanceDTO dto) {
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

    private Projeto toProjetoEntity(ProjetoDTO dto) {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(dto.getIdProjeto());
        projeto.setNomeProjeto(dto.getNomeProjeto());
        projeto.setTempoEmHoras(dto.getTempoEmHoras());
        projeto.setEmpresaContratanteProjeto(dto.getEmpresaContratanteProjeto());
        projeto.setPaisProjeto(dto.getPaisProjeto());
        projeto.setTecnologias(dto.getTecnologias());
        projeto.setEmailContato(dto.getEmailContato());
        projeto.setCobreCustoFreelance(dto.getCobreCustoFreelance());
        projeto.setValorCustoPago(dto.getValorCustoPago());
        projeto.setValorHoraPago(dto.getValorHoraPago());
        projeto.setHorasPorDia(dto.getHorasPorDia());
        projeto.setAnosExperiencia(dto.getAnosExperiencia());
        projeto.setDiasVctoFatura(dto.getDiasVctoFatura());
        return projeto;
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
        dto.setHorasPorDia(projeto.getHorasPorDia());
        return dto;
    }

}
