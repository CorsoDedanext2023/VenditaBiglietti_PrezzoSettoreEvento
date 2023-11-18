package it.dedagroup.venditabiglietti.prezzo_settore_evento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;

/**
 * Repository per l'entità {@code PrezzoSettoreEvento}.
 * Estende {@code JpaRepository} per fornire operazioni di base di accesso ai dati.
 */
public interface PrezzoSettoreEventoRepository extends JpaRepository<PrezzoSettoreEvento, Long> {

    /**
     * Trova tutti i prezzi del settore per un determinato identificatore di settore.
     *
     * @param id L'identificatore del settore.
     * @return Un'opzione contenente una lista di prezzi del settore, se presenti.
     */
    Optional<List<PrezzoSettoreEvento>> findAllByIdSettore(long id);

    /**
     * Trova tutti i prezzi del settore per un determinato identificatore di evento.
     *
     * @param id L'identificatore dell'evento.
     * @return Un'opzione contenente una lista di prezzi del settore, se presenti.
     */
    Optional<List<PrezzoSettoreEvento>> findAllByIdEvento(long id);

    /**
     * Modifica il prezzo del settore per un evento specifico.
     *
     * @param nuovoPrezzo Il nuovo prezzo da impostare.
     * @param idSettore   L'identificatore del settore.
     * @param idEvento    L'identificatore dell'evento.
     */
    @Modifying
    @Query("UPDATE PrezzoSettoreEvento pse SET pse.prezzo = :nuovoPrezzo WHERE pse.idSettore = :idSettore AND pse.idEvento = :idEvento")
    void modificaPrezzoByIdSettoreAndIdEvento(
            @Param("nuovoPrezzo") double nuovoPrezzo,
            @Param("idSettore") long idSettore,
            @Param("idEvento") long idEvento
    );

    /**
     * Trova tutti i prezzi del settore per un determinato identificatore di evento e settore.
     *
     * @param idEvento L'identificatore dell'evento.
     * @param idSettore L'identificatore del settore.
     * @return Un'opzione contenente una lista di prezzi del settore, se presenti.
     */
    Optional<List<PrezzoSettoreEvento>> findAllByIdEventoAndIdSettore(long idEvento, long idSettore);
    
    /**
     * Imposta il flag di disponibilità su falso per tutti i prezzi del settore associati a un dato identificatore di settore.
     *
     * @param idSettore L'identificatore del settore.
     */
    @Modifying
    @Query("UPDATE PrezzoSettoreEvento pse SET pse.isCancellato = true WHERE pse.idSettore = :idSettore")
    void eliminaByIdSettore(@Param("idSettore") long idSettore);
    
    /**
     * Imposta il flag di disponibilità su falso per tutti i prezzi del settore associati a un dato identificatore di evento.
     *
     * @param idEvento L'identificatore dell'evento.
     */
    @Modifying
    @Query("UPDATE PrezzoSettoreEvento pse SET pse.isCancellato = true WHERE pse.idEvento = :idEvento")
    void eliminaByIdEvento(@Param("idEvento") long idEvento);
    
    /**
     * Imposta il flag di disponibilità su falso per un dato prezzo del settore associato a un identificatore di settore ed evento.
     *
     * @param idSettore L'identificatore del settore.
     * @param idEvento  L'identificatore dell'evento.
     */
    @Modifying
    @Query("UPDATE PrezzoSettoreEvento pse SET pse.isCancellato = true WHERE pse.idSettore = :idSettore AND pse.idEvento = :idEvento")
    void eliminaByIdSettoreAndIdEvento(@Param("idSettore") long idSettore, @Param("idEvento") long idEvento);
    
    /**
     * Trova tutti i prezzi del settore per un determinato identificatore di evento con flag di disponibilità true.
     *
     * @param id L'identificatore dell'evento.
     * @return Un'opzione contenente una lista di prezzi del settore disponibili, se presenti.
     */
    Optional<List<PrezzoSettoreEvento>> findAllByIdEventoAndIsCancellatoFalse(long id);
    
    /**
     * Trova tutti i prezzi del settore per un determinato identificatore di settore con flag di disponibilità true.
     *
     * @param id L'identificatore del settore.
     * @return Un'opzione contenente una lista di prezzi del settore disponibili, se presenti.
     */
    Optional<List<PrezzoSettoreEvento>> findAllByIdSettoreAndIsCancellatoFalse(long id);
    
    /**
     * Trova tutti i prezzi del settore per un determinato identificatore di evento e settore con flag di disponibilità true.
     *
     * @param idEvento L'identificatore dell'evento.
     * @param idSettore L'identificatore del settore.
     * @return Un'opzione contenente una lista di prezzi del settore disponibili, se presenti.
     */
    Optional<List<PrezzoSettoreEvento>> findAllByIdEventoAndIdSettoreAndIsCancellatoFalse(long idEvento, long idSettore);
    
    Optional<List<PrezzoSettoreEvento>> findAllByIsCancellatoFalse();
}
