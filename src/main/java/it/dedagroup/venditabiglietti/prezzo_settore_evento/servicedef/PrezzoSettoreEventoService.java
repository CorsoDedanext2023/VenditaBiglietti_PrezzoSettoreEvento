package it.dedagroup.venditabiglietti.prezzo_settore_evento.servicedef;

import java.util.List;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model.NotFoundExceptionCustom;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model.NotValidDataException;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;

/**
* Servizio per la gestione dei prezzi dei settori per gli eventi.
*/
public interface PrezzoSettoreEventoService {

   /**
    * Aggiunge un nuovo prezzo del settore per un evento.
    *
    * @param pse Il prezzo del settore per l'evento da aggiungere.
    */
   void aggiungiPrezzoSettoreEvento(PrezzoSettoreEvento pse);

   /**
    * Modifica un prezzo del settore per un evento esistente.
    *
    * @param pse Il prezzo del settore per l'evento da modificare.
    */
   void modificaPrezzoSettoreEvento(PrezzoSettoreEvento pse);

   /**
    * Elimina un prezzo del settore per un evento.
    *
    * @param pse Il prezzo del settore per l'evento da eliminare.
    */
   void eliminaPrezzoSettoreEvento(PrezzoSettoreEvento pse);

   /**
    * Trova un prezzo del settore per un evento tramite il suo identificatore univoco.
    *
    * @param id L'identificatore univoco del prezzo del settore per l'evento.
    * @return Il prezzo del settore per l'evento trovato.
    * @throws NotValidDataException se il prezzo del settore per l'evento non viene trovato.
    */
   PrezzoSettoreEvento findById(long id) throws NotFoundExceptionCustom;

   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di settore.
    *
    * @param id L'identificatore del settore.
    * @return Una lista di prezzi dei settori per l'evento, se presenti.
    * @throws NotValidDataException se non vengono trovati prezzi del settore per l'evento.
    */
   List<PrezzoSettoreEvento> findAllByIdSettore(long id) throws NotFoundExceptionCustom;

   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di evento.
    *
    * @param id L'identificatore dell'evento.
    * @return Una lista di prezzi dei settori per l'evento, se presenti.
    * @throws NotValidDataException se non vengono trovati prezzi del settore per l'evento.
    */
   List<PrezzoSettoreEvento> findAllByIdEvento(long id) throws NotFoundExceptionCustom;

   /**
    * Modifica il prezzo del settore per un evento specifico.
    *
    * @param prezzo   Il nuovo prezzo da impostare.
    * @param idSettore L'identificatore del settore.
    * @param idEvento  L'identificatore dell'evento.
    */
   void modificaPrezzoByIdSettoreAndIdEvento(double prezzo, long idSettore, long idEvento);

   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di evento e settore.
    *
    * @param idEvento L'identificatore dell'evento.
    * @param idSettore L'identificatore del settore.
    * @return Una lista di prezzi dei settori per l'evento, se presenti.
    * @throws NotFoundExceptionCustom se non vengono trovati prezzi del settore per l'evento e settore specificati.
    */
   List<PrezzoSettoreEvento> findAllByIdEventoAndIdSettore(long idEvento, long idSettore) throws NotFoundExceptionCustom;

   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di evento e settore che sono disponibili.
    *
    * @param idEvento L'identificatore dell'evento.
    * @param idSettore L'identificatore del settore.
    * @return Una lista di prezzi dei settori per l'evento che sono disponibili, se presenti.
    * @throws NotFoundExceptionCustom se non vengono trovati prezzi del settore disponibili per l'evento e settore specificati.
    */
   List<PrezzoSettoreEvento> findAllByIsAvailableTrue(long idEvento, long idSettore) throws NotFoundExceptionCustom;
   
   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di evento che sono disponibili.
    *
    * @param id L'identificatore dell'evento.
    * @return Una lista di prezzi dei settori per l'evento che sono disponibili, se presenti.
    * @throws NotFoundExceptionCustom se non vengono trovati prezzi del settore disponibili per l'evento specificato.
    */
   List<PrezzoSettoreEvento> findAllByIdEventoAndIsAvailableTrue(long id) throws NotFoundExceptionCustom;

   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di settore che sono disponibili.
    *
    * @param id L'identificatore del settore.
    * @return Una lista di prezzi dei settori per l'evento che sono disponibili, se presenti.
    * @throws NotFoundExceptionCustom se non vengono trovati prezzi del settore disponibili per l'evento specificato.
    */
   List<PrezzoSettoreEvento> findAllByIdSettoreAndIsAvailableTrue(long id) throws NotFoundExceptionCustom;

   /**
    * Trova tutti i prezzi dei settori per un determinato identificatore di evento e settore che sono disponibili.
    *
    * @param idEvento L'identificatore dell'evento.
    * @param idSettore L'identificatore del settore.
    * @return Una lista di prezzi dei settori per l'evento che sono disponibili, se presenti.
    * @throws NotFoundExceptionCustom se non vengono trovati prezzi del settore disponibili per l'evento e settore specificati.
    */
   List<PrezzoSettoreEvento> findAllByIdEventoAndIdSettoreAndIsAvailableTrue(long idEvento, long idSettore) throws NotFoundExceptionCustom;

   /**
    * Elimina i prezzi del settore associati a un determinato identificatore di settore.
    *
    * @param idSettore L'identificatore del settore.
    */
   void eliminaByIdSettore(long idSettore);

   /**
    * Elimina i prezzi del settore associati a un determinato identificatore di evento.
    *
    * @param idEvento L'identificatore dell'evento.
    */
   void eliminaByIdEvento(long idEvento);

   /**
    * Elimina i prezzi del settore associati a un determinato identificatore di settore e evento.
    *
    * @param idSettore L'identificatore del settore.
    * @param idEvento  L'identificatore dell'evento.
    */
   void eliminaByIdSettoreAndIdEvento(long idSettore, long idEvento);

   
   
   
}
