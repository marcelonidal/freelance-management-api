package br.com.freelance_management_api.freelance_management_api.service;

import br.com.freelance_management_api.freelance_management_api.entities.CadastroFreeLance;
import br.com.freelance_management_api.freelance_management_api.repository.CadastroFreeLanceRepository;
import br.com.freelance_management_api.freelance_management_api.controller.excetion.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class CadastroFreeLanceService {

    @Autowired
    private CadastroFreeLanceRepository cadastroFreeLanceRepository;

    //Rretornar todos os cadastros de FreeLance

    public Collection<CadastroFreeLance> findAll () {
        var freelance = cadastroFreeLanceRepository.findAll();
        return freelance;
    }

    //Retorna o FreeLance pelo Id de Cadstro
    public CadastroFreeLance findById(UUID id) {
        var freelance = cadastroFreeLanceRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("FreeLance não Cadastrado"));
        return freelance;
    }

   //Inclui novo Freelance
   public CadastroFreeLance save(CadastroFreeLance freelance){
        freelance = cadastroFreeLanceRepository.save(freelance);
        return freelance;
   }

   //Update dos Dados FreeLance
    public CadastroFreeLance update(UUID id, CadastroFreeLance freeLance) {
        try {

            CadastroFreeLance buscaFreelance = cadastroFreeLanceRepository.getOne(id);
            buscaFreelance.setNome(freeLance.getNome());
            buscaFreelance.seteMail(freeLance.geteMail());
            buscaFreelance.setTecnologia(freeLance.getTecnologia());
            buscaFreelance.setDataDisponivel(freeLance.getDataDisponivel());
            buscaFreelance.setEnderecoResidencia(freeLance.getEnderecoResidencia());
            buscaFreelance.setNumeroResidencia(freeLance.getNumeroResidencia());
            buscaFreelance.setComplementoEndereco(freeLance.getComplementoEndereco());
            buscaFreelance.setValorHora(freeLance.getValorHora());
            buscaFreelance.setQtdAnosExperiencia(freeLance.getQtdAnosExperiencia());
            buscaFreelance = cadastroFreeLanceRepository.save(buscaFreelance);

            return buscaFreelance;
        }

        catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("FreLance Não Localizado Para Atualização");

        }
    }

    //Exclui registro
    public void delete(UUID id){
        cadastroFreeLanceRepository.deleteById(id);
    }

}

