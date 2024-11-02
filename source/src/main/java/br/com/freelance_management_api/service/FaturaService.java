package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.FaturaDTO;
import br.com.freelance_management_api.dto.FreelanceDTO;
import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.Fatura;
import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.entities.StatusFatura;
import br.com.freelance_management_api.repository.FaturaRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FaturaService {

    @Autowired
    private FaturaRepository faturaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ContratoService contratoService;

    public List<FaturaDTO> listar() {
        return faturaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<FaturaDTO> buscar(Long id) {
        return faturaRepository.findById(id).map(this::toDTO);
    }

    public FaturaDTO criar(FaturaDTO faturaDTO) {
        Fatura fatura = toEntity(faturaDTO);

        Freelance freelance = fatura.getFreelance();
        Projeto projeto = fatura.getProjeto();
        if (freelance != null && fatura.getDataEmissao() != null && fatura.getDataVencimento() != null) {

            long duracaoDias = fatura.getDataVencimento().toEpochDay() - fatura.getDataEmissao().toEpochDay();
            double taxaHora = freelance.getValorHora();
            int horasPorDia = projeto.getHorasPorDia();
            double custoTotal = taxaHora * horasPorDia * duracaoDias;
            fatura.setValor(custoTotal);
            fatura.setStatus(StatusFatura.PENDENTE);
        }

        fatura = faturaRepository.save(fatura);
        FaturaDTO faturaCriada = toDTO(fatura);

        faturaCriada.setFreelance(toFreelanceDTO(fatura.getFreelance()));
        faturaCriada.setProjeto(toProjetoDTO(fatura.getProjeto()));

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

    public FaturaDTO atualizar(Long id, FaturaDTO faturaDTO) {
        Fatura faturaExistente = faturaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fatura não encontrada para atualização"));

        faturaExistente.setValor(faturaDTO.getValor());
        faturaExistente.setStatus(StatusFatura.valueOf(faturaDTO.getStatus()));
        faturaExistente.setDataEmissao(faturaDTO.getDataEmissao());
        faturaExistente.setDataVencimento(faturaDTO.getDataVencimento());

        faturaExistente = faturaRepository.save(faturaExistente);
        return toDTO(faturaExistente);
    }

    public void deletar(Long id) {
        if (!faturaRepository.existsById(id)) {
            throw new EntityNotFoundException("Contrato não encontrado para exclusão");
        }

        faturaRepository.deleteById(id);
    }

    private FaturaDTO toDTO(Fatura fatura) {
        FaturaDTO dto = new FaturaDTO();
        dto.setId(fatura.getId());
        dto.setValor(fatura.getValor());
        dto.setStatus(fatura.getStatus() != null ? fatura.getStatus().name() : "");
        dto.setDataEmissao(fatura.getDataEmissao());
        dto.setDataVencimento(fatura.getDataVencimento());

        dto.setFreelance(toFreelanceDTO(fatura.getFreelance()));
        dto.setProjeto(toProjetoDTO(fatura.getProjeto()));

        return dto;
    }

    private Fatura toEntity(FaturaDTO dto) {
        Fatura fatura = new Fatura();
        fatura.setId(dto.getId());
        fatura.setValor(dto.getValor());
        fatura.setStatus(StatusFatura.valueOf(dto.getStatus()));
        fatura.setDataEmissao(dto.getDataEmissao());
        fatura.setDataVencimento(dto.getDataVencimento());

        // Convert and set the Freelance and Projeto objects
        if (dto.getFreelance() != null) {
            fatura.setFreelance(toFreelanceEntity(dto.getFreelance()));
        }
        if (dto.getProjeto() != null) {
            fatura.setProjeto(toProjetoEntity(dto.getProjeto()));
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
