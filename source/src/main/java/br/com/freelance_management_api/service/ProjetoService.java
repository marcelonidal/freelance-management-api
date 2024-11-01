package br.com.freelance_management_api.service;

import br.com.freelance_management_api.dto.ProjetoDTO;
import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.repository.ProjetoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<ProjetoDTO> listar() {
        return projetoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProjetoDTO buscar(UUID id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado"));
        return toDTO(projeto);
    }

    public ProjetoDTO criar(ProjetoDTO projetoDTO) {
        Projeto projeto = toEntity(projetoDTO);
        projeto = projetoRepository.save(projeto);
        return toDTO(projeto);
    }

    public ProjetoDTO atualizar(UUID id, ProjetoDTO projetoDTO) {
        Projeto projetoExistente = projetoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Projeto não encontrado para atualização"));

        projetoExistente.setNomeProjeto(projetoDTO.getNomeProjeto());
        projetoExistente.setTempoEmHoras(projetoDTO.getTempoEmHoras());
        projetoExistente.setEmpresaContratanteProjeto(projetoDTO.getEmpresaContratanteProjeto());
        projetoExistente.setPaisProjeto(projetoDTO.getPaisProjeto());
        projetoExistente.setTecnologias(projetoDTO.getTecnologias());
        projetoExistente.setCobreCustoFreelance(projetoDTO.getCobreCustoFreelance());
        projetoExistente.setValorCustoPago(projetoDTO.getValorCustoPago());
        projetoExistente.setValorHoraPago(projetoDTO.getValorHoraPago());

        projetoExistente = projetoRepository.save(projetoExistente);
        return toDTO(projetoExistente);
    }

    public void deletar(UUID id) {
        projetoRepository.deleteById(id);
    }

    private ProjetoDTO toDTO(Projeto projeto) {
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

    private Projeto toEntity(ProjetoDTO dto) {
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

}
