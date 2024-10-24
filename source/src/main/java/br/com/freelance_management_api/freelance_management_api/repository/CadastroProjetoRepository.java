package br.com.freelance_management_api.freelance_management_api.repository;

import br.com.freelance_management_api.freelance_management_api.entities.CadastroProjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CadastroProjetoRepository extends JpaRepository<CadastroProjeto, UUID> {
}
