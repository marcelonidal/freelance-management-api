package br.com.freelance_management_api.service;

import br.com.freelance_management_api.entities.Projeto;
import br.com.freelance_management_api.repository.ProjetoRepository;
import br.com.freelance_management_api.controller.excetion.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    //Retorna Todos os Projetos Cadstrados
    public Collection<Projeto> findAll(){
        return projetoRepository.findAll();
    }

    //Retorno o Projeto Pelo Id do cadastro
    public Projeto findById(UUID id){
        return projetoRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Projeto não Cadastrado"));
    }

    //Cadastra novo Projeto
    public Projeto save(Projeto projeto){
        projeto = projetoRepository.save(projeto);
        return projeto;
    }

    //Atualiza Cadastro do Projeto
    public Projeto update(UUID id, Projeto projeto){
        try {
            Projeto buscaCadastrProjeto = projetoRepository.getOne(id);
            buscaCadastrProjeto.setNomeProjeto(projeto.getNomeProjeto());
            buscaCadastrProjeto.setTempoEmHoras(projeto.getTempoEmHoras());
            buscaCadastrProjeto.setEmpresaContratanteProjeto(projeto.getEmpresaContratanteProjeto());
            buscaCadastrProjeto.setPaisProjeto(projeto.getPaisProjeto());
            buscaCadastrProjeto.setTecnologias(projeto.getTecnologias());
            buscaCadastrProjeto.setCobreCustoFreelance(projeto.getCobreCustoFreelance());
            buscaCadastrProjeto.setValorCustoPago(projeto.getValorCustoPago());
            buscaCadastrProjeto.setValorHoraPago(projeto.getValorHoraPago());
            buscaCadastrProjeto = projetoRepository.save(buscaCadastrProjeto);

            return buscaCadastrProjeto;

        }
        catch (EntityNotFoundException e){
            throw new ControllerNotFoundException("Projeto Não Localizado para Atualização");
        }
    }

    //Exclui Projeto Cadastrado
    public void delete(UUID id){
        projetoRepository.deleteById(id);

    }
}
