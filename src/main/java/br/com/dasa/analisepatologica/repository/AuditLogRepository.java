package br.com.dasa.analisepatologica.repository;

import br.com.dasa.analisepatologica.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for AuditLog entity.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * Finds audit logs by table name.
     */
    List<AuditLog> findByTabelaAfetada(String tabelaAfetada);

    /**
     * Finds audit logs by record ID and table.
     */
    List<AuditLog> findByTabelaAfetadaAndRegistroId(String tabelaAfetada, Long registroId);

    /**
     * Finds audit logs by action (INSERT, UPDATE, DELETE).
     */
    List<AuditLog> findByAcao(String acao);

    /**
     * Finds audit logs by user.
     */
    List<AuditLog> findByUsuario(String usuario);

    /**
     * Finds audit logs by IP origin.
     */
    List<AuditLog> findByIpOrigem(String ipOrigem);

    /**
     * Finds audit logs by application.
     */
    List<AuditLog> findByAplicacao(String aplicacao);

    /**
     * Finds audit logs between two dates.
     */
    List<AuditLog> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    /**
     * Finds audit logs by session ID.
     */
    List<AuditLog> findBySessaoId(String sessaoId);

    /**
     * Finds recent audit logs (last N records).
     */
    @Query("SELECT a FROM AuditLog a ORDER BY a.dataHora DESC")
    List<AuditLog> findRecentAudits();

    /**
     * Counts audit logs by action.
     */
    long countByAcao(String acao);

    /**
     * Counts audit logs by user.
     */
    long countByUsuario(String usuario);

    /**
     * Counts audit logs by table.
     */
    long countByTabelaAfetada(String tabelaAfetada);

    /**
     * Finds audit logs for a specific record history.
     */
    @Query("SELECT a FROM AuditLog a WHERE a.tabelaAfetada = :tabela AND a.registroId = :registroId ORDER BY a.dataHora ASC")
    List<AuditLog> findHistoricoRegistro(@Param("tabela") String tabela, @Param("registroId") Long registroId);

    /**
     * Finds audit logs by user and date range.
     */
    @Query("SELECT a FROM AuditLog a WHERE a.usuario = :usuario AND a.dataHora BETWEEN :inicio AND :fim ORDER BY a.dataHora DESC")
    List<AuditLog> findByUsuarioAndDateRange(@Param("usuario") String usuario,
                                               @Param("inicio") LocalDateTime inicio,
                                               @Param("fim") LocalDateTime fim);
}
