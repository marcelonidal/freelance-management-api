package br.com.freelance_management_api;

import br.com.freelance_management_api.dto.*;
import br.com.freelance_management_api.entities.StatusFatura;
import br.com.freelance_management_api.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FreelanceManagementTest {

    @Autowired
    private FreelanceService freelanceService;

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private FaturaService faturaService;

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @Autowired
    private AgendaFreelanceService agendaFreelanceService;

    @Autowired
    private AgendaProjetoService agendaProjetoService;

    @Test
    void contextLoads() {
    }

    @Test
    public void testarFluxoCompletoDeGerenciamento() {

        Set<String> setTec = new HashSet<>();
        setTec.add("java");
        setTec.add("spring");

        // 1. Criar um Freelancer
        FreelanceDTO freelanceDTO = new FreelanceDTO();
        freelanceDTO.setNome("João Silva");
        freelanceDTO.seteMail("freelancegrupo19fiap@gmail.com");
        freelanceDTO.setQtdAnosExperiencia(5);
        freelanceDTO.setValorHora(50.0);
        freelanceDTO.setTecnologias(setTec);
        freelanceDTO = freelanceService.criar(freelanceDTO);

        assertNotNull(freelanceDTO.getIdFreelance(), "O ID do freelancer não deve ser nulo");

        // 2. Criar um Projeto
        ProjetoDTO projetoDTO = new ProjetoDTO();
        projetoDTO.setNomeProjeto("Desenvolvimento de Software");
        projetoDTO.setTempoEmHoras("80");
        projetoDTO.setEmpresaContratanteProjeto("Empresa XPTO");
        projetoDTO.setEmailContato("freelancegrupo19fiap@gmail.com");
        projetoDTO.setCobreCustoFreelance(true);
        projetoDTO.setValorHoraPago(60.0);
        projetoDTO.setTecnologias(setTec);
        projetoDTO.setHorasPorDia(8);
        projetoDTO = projetoService.criar(projetoDTO);

        assertNotNull(projetoDTO.getIdProjeto(), "O ID do projeto não deve ser nulo");

        // 3. Criar um Contrato
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(30);

        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO.setFreelance(freelanceDTO);
        contratoDTO.setProjeto(projetoDTO);
        contratoDTO.setDataInicioContrato(dataInicio);
        contratoDTO.setDateFimContrato(dataFim);

        ContratoDTO contratoCriado = contratoService.criar(contratoDTO);
        assertNotNull(contratoCriado.getIdContrato(), "O ID do contrato não deve ser nulo");

        // 4. Adicionar Agendamentos de Freelancer e Projeto
        AgendaFreelanceDTO agendaFreelanceDTO = new AgendaFreelanceDTO();
        agendaFreelanceDTO.setIdFreelance(freelanceDTO.getIdFreelance());
        agendaFreelanceDTO.setDataInicio(dataInicio);
        agendaFreelanceDTO.setDataFim(dataFim);
        agendaFreelanceDTO.setIdProjeto(projetoDTO.getIdProjeto());
        agendaFreelanceDTO = agendaFreelanceService.criar(agendaFreelanceDTO);

        AgendaProjetoDTO agendaProjetoDTO = new AgendaProjetoDTO();
        agendaProjetoDTO.setIdProjeto(projetoDTO.getIdProjeto());
        agendaProjetoDTO.setDataInicio(dataInicio);
        agendaProjetoDTO.setDataFim(dataFim);
        agendaProjetoDTO = agendaProjetoService.criar(agendaProjetoDTO);

        // Verificar se os IDs foram gerados corretamente
        assertNotNull(agendaFreelanceDTO.getId(), "O ID da agenda do freelancer não deve ser nulo");
        assertNotNull(agendaProjetoDTO.getId(), "O ID da agenda do projeto não deve ser nulo");

        // 5. Gerar uma Fatura
        FaturaDTO faturaDTO = new FaturaDTO();
        faturaDTO.setValor(5000.0);
        faturaDTO.setStatus(StatusFatura.PENDENTE.name());
        faturaDTO.setDataEmissao(LocalDate.now());
        faturaDTO.setDataVencimento(dataFim);
        faturaDTO.setFreelance(freelanceDTO);
        faturaDTO.setProjeto(projetoDTO);
        faturaDTO.setDataVencimento(LocalDate.now().plusDays(30));
        faturaDTO = faturaService.criar(faturaDTO);

        assertNotNull(faturaDTO.getId(), "O ID da fatura não deve ser nulo");
        assertEquals(StatusFatura.PENDENTE.name(), faturaDTO.getStatus(), "O status da fatura deve ser PENDENTE");

        // 6. Verificar Disponibilidade do Freelancer
        boolean disponivel = disponibilidadeService.isFreelanceDisponivel(
                freelanceDTO.getIdFreelance(), dataInicio, dataFim);
        assertFalse(disponivel, "O freelancer não deve estar disponível durante o período do contrato");

        // 7. Verificar Disponibilidade do Projeto
        boolean projetoDisponivel = disponibilidadeService.isProjetoDisponivel(
                projetoDTO.getIdProjeto(), dataInicio, dataFim);
        assertFalse(projetoDisponivel, "O projeto não deve estar disponível durante o período do contrato");

        System.out.println("Teste de fluxo completo executado com sucesso!");
    }

}
