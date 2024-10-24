package br.com.freelance_management_api.freelance_management_api.service;

import br.com.freelance_management_api.freelance_management_api.entities.CadastroProjeto;
import br.com.freelance_management_api.freelance_management_api.repository.CadastroProjetoRepository;
import br.com.freelance_management_api.freelance_management_api.controller.excetion.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class CadastroProjetoService {

    @Autowired
    private CadastroProjetoRepository cadastroProjetoRepository;

    //Retorna Todos os Projetos Cadstrados
    public Collection<CadastroProjeto> findAll(){
        var cadastroProjeto = cadastroProjetoRepository.findAll();
        return cadastroProjeto;
    }

    //Retorno o Projeto Pelo Id do cadastro
    public CadastroProjeto findById(UUID id){
        var cadastroProjeto = cadastroProjetoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Projeto não Cadastrado"));
        return cadastroProjeto;
    }

    //Cadastra novo Projeto
    public CadastroProjeto save(CadastroProjeto cadastroProjeto){
        cadastroProjeto = cadastroProjetoRepository.save(cadastroProjeto);
        return cadastroProjeto;
    }

    //Atualiza Cadastro do Projeto
    public CadastroProjeto update(UUID id, CadastroProjeto cadastroProjeto){
        try {
            CadastroProjeto buscaCadastrProjeto = cadastroProjetoRepository.getOne(id);
            buscaCadastrProjeto.setNomeProjeto(cadastroProjeto.getNomeProjeto());
            buscaCadastrProjeto.setTempoEmHoras(cadastroProjeto.getTempoEmHoras());
            buscaCadastrProjeto.setEmpresaContratanteProjeto(cadastroProjeto.getEmpresaContratanteProjeto());
            buscaCadastrProjeto.setPaisProjeto(cadastroProjeto.getPaisProjeto());
            buscaCadastrProjeto.setTecnologiaProjeto(cadastroProjeto.getTecnologiaProjeto());
            buscaCadastrProjeto.setCobreCustoFreelance(cadastroProjeto.getCobreCustoFreelance());
            buscaCadastrProjeto.setValorCustoPago(cadastroProjeto.getValorCustoPago());
            buscaCadastrProjeto.setValorHoraPago(cadastroProjeto.getValorHoraPago());
            buscaCadastrProjeto = cadastroProjetoRepository.save(buscaCadastrProjeto);

            return buscaCadastrProjeto;

        }
        catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Projeto Não Localizado para Atualização");
        }
    }

    //Exclui Projeto Cadastrado
    public void detelete(UUID id){
        cadastroProjetoRepository.deleteById(id);

    }
}
