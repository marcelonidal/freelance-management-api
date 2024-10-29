package br.com.freelance_management_api.service;

import br.com.freelance_management_api.entities.Freelance;
import br.com.freelance_management_api.repository.FreelanceRepository;
import br.com.freelance_management_api.controller.excetion.ControllerNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FreeLanceService {

    @Autowired
    private FreelanceRepository freeLanceRepository;

    //Rretornar todos os cadastros de FreeLance

    public List<Freelance> findAll () {
        return freeLanceRepository.findAll();
    }

    //Retorna o FreeLance pelo Id de Cadstro
    public Freelance findById(UUID id) {
        return freeLanceRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("FreeLance não Cadastrado"));
    }

   //Inclui novo Freelance
   public Freelance save(Freelance freelance){
        freelance = freeLanceRepository.save(freelance);
        return freelance;
   }

   //Update dos Dados FreeLance
    public Freelance update(UUID id, Freelance freeLance) {
        try {

            Freelance buscaFreelance = freeLanceRepository.getReferenceById(id);
            buscaFreelance.setNome(freeLance.getNome());
            buscaFreelance.seteMail(freeLance.geteMail());
            buscaFreelance.setTecnologias(freeLance.getTecnologias());
            buscaFreelance.setEnderecoResidencia(freeLance.getEnderecoResidencia());
            buscaFreelance.setNumeroResidencia(freeLance.getNumeroResidencia());
            buscaFreelance.setComplementoEndereco(freeLance.getComplementoEndereco());
            buscaFreelance.setValorHora(freeLance.getValorHora());
            buscaFreelance.setQtdAnosExperiencia(freeLance.getQtdAnosExperiencia());
            buscaFreelance = freeLanceRepository.save(buscaFreelance);

            return buscaFreelance;
        }

        catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("FreLance Não Localizado Para Atualização");

        }
    }

    //Exclui registro
    public void delete(UUID id){
        freeLanceRepository.deleteById(id);
    }

}

