package br.com.freelance_management_api;

import br.com.freelance_management_api.dto.*;
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

    private final FreelanceService freelanceService;
    private final ProjetoService projetoService;
    private final ContratoService contratoService;
    private final DisponibilidadeService disponibilidadeService;

    @Autowired
    public FreelanceManagementTest(FreelanceService freelanceService, ProjetoService projetoService, ContratoService contratoService,
                                   DisponibilidadeService disponibilidadeService) {
        this.freelanceService = freelanceService;
        this.projetoService = projetoService;
        this.contratoService = contratoService;
        this.disponibilidadeService = disponibilidadeService;
    }

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
        projetoDTO.setDiasVctoFatura(30);
        projetoDTO = projetoService.criar(projetoDTO);

        assertNotNull(projetoDTO.getIdProjeto(), "O ID do projeto não deve ser nulo");

        // 3. Criar um Contrato
        LocalDate dataInicio = LocalDate.now();
        LocalDate dataFim = dataInicio.plusDays(30);

        ContratoDTO contratoDTO = new ContratoDTO();
        contratoDTO.setIdFreelance(freelanceDTO.getIdFreelance());
        contratoDTO.setIdProjeto(projetoDTO.getIdProjeto());
        contratoDTO.setDataInicioContrato(dataInicio);
        contratoDTO.setDataFimContrato(dataFim);

        ContratoDTO contratoCriado = contratoService.criar(contratoDTO);
        assertNotNull(contratoCriado.getIdContrato(), "O ID do contrato não deve ser nulo");

        // 4. Adicionar Agendamentos de Freelancer e Projeto - criado dentro de contrato

        // 5. Gerar uma Fatura - criado dentro de contrato

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
