package it.dedagroup.venditabiglietti.prezzo_settore_evento.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model.NotFoundExceptionCustom;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model.NotValidDataException;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.repository.PrezzoSettoreEventoRepository;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.servicedef.PrezzoSettoreEventoService;
import jakarta.transaction.Transactional;

/**
 * Implementazione del servizio {@link PrezzoSettoreEventoService} che gestisce
 * le operazioni relative ai prezzi settore evento, interagendo con il repository {@link PrezzoSettoreEventoRepository}.
 * Le operazioni includono l'aggiunta, la modifica, l'eliminazione e la ricerca di prezzi settore evento
 * basate su diversi criteri, come id, id settore, id evento.
 * 
 * Le operazioni di aggiunta e modifica sono transazionali e gestiscono il rollback in caso di eccezioni di accesso ai dati.
 * L'eliminazione di un prezzo settore evento viene effettuata impostando il flag 'available' su false,
 * mentre la ricerca per id, id settore e id evento restituisce le liste corrispondenti o lancia un'eccezione di
 * tipo {@link NotValidDataException} se i dati richiesti non sono presenti nel repository.
 * La modifica del prezzo per id settore e id evento è eseguita in due fasi, prima viene effettuata una ricerca
 * e poi viene eseguita l'effettiva modifica attraverso il repository.
 * 
 * @see PrezzoSettoreEventoService
 * @see PrezzoSettoreEventoRepository
 * @see NotValidDataException
 * @see NotFoundExceptionCustom
 */
@Service
public class PrezzoSettoreEventoServiceImpl implements PrezzoSettoreEventoService {
	
	@Autowired
	private PrezzoSettoreEventoRepository repo;

	/**
	 * Aggiunge un nuovo prezzo settore evento al repository.
	 * 
	 * @param pse Il prezzo settore evento da aggiungere.
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void aggiungiPrezzoSettoreEvento(PrezzoSettoreEvento pse) {
		repo.save(pse);
	}

	/**
	 * Modifica un prezzo settore evento esistente nel repository.
	 * 
	 * @param pse Il prezzo settore evento da modificare.
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void modificaPrezzoSettoreEvento(PrezzoSettoreEvento pse) {
		repo.save(pse);
	}

	/**
	 * Elimina un prezzo settore evento impostando il flag 'available' su false.
	 * 
	 * @param pse Il prezzo settore evento da eliminare.
	 * @see PrezzoSettoreEvento#setAvailable(boolean)
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	public void eliminaPrezzoSettoreEvento(PrezzoSettoreEvento pse) {
		pse.setCancellato(true);
		repo.save(pse);
	}

	/**
	 * Trova un prezzo settore evento per l'id specificato.
	 * 
	 * @param id L'id del prezzo settore evento da cercare.
	 * @return Il prezzo settore evento corrispondente all'id.
	 * @throws NotValidDataException Se il prezzo settore evento con l'id specificato non è presente nel repository.
	 */
	@Override
	public PrezzoSettoreEvento findById(long id) {
		return repo.findById(id).orElseThrow(() -> new NotValidDataException("Id 'PrezzoSettoreEvento' non trovato"));
	}

	/**
	 * Trova tutti i prezzi settore evento per l'id del settore specificato.
	 * 
	 * @param id L'id del settore per cui cercare i prezzi settore evento.
	 * @return Una lista di prezzi settore evento corrispondente all'id del settore.
	 * @throws NotValidDataException Se non sono presenti prezzi settore evento per l'id del settore specificato.
	 */
	@Override
	public List<PrezzoSettoreEvento> findAllByIdSettore(long id) {
		return repo.findAllByIdSettore(id).orElseThrow(() -> new NotFoundExceptionCustom("PrezzoSettoreEvento con id settore " + id + " non trovato"));
	}

	/**
	 * Trova tutti i prezzi settore evento per l'id dell'evento specificato.
	 * 
	 * @param id L'id dell'evento per cui cercare i prezzi settore evento.
	 * @return Una lista di prezzi settore evento corrispondente all'id dell'evento.
	 * @throws NotValidDataException Se non sono presenti prezzi settore evento per l'id dell'evento specificato.
	 */
	@Override
	public List<PrezzoSettoreEvento> findAllByIdEvento(long id) {
		return repo.findAllByIdEvento(id).orElseThrow(() -> new NotFoundExceptionCustom("PrezzoSettoreEvento con id evento " + id + " non trovato"));
	}

	/**
	 * Modifica il prezzo di un prezzo settore evento per l'id del settore e l'id dell'evento specificati.
	 * 
	 * @param prezzo Il nuovo prezzo da impostare.
	 * @param idSettore L'id del settore per cui modificare il prezzo.
	 * @param idEvento L'id dell'evento per cui modificare il prezzo.
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void modificaPrezzoByIdSettoreAndIdEvento(double prezzo, long idSettore, long idEvento) {
		findAllByIdEventoAndIdSettore(idEvento, idSettore);
		repo.modificaPrezzoByIdSettoreAndIdEvento(prezzo, idSettore, idEvento);
	}

	/**
	 * Trova tutti i prezzi settore evento per l'id dell'evento e l'id del settore specificati.
	 * 
	 * @param idEvento L'id dell'evento per cui cercare i prezzi settore evento.
	 * @param idSettore L'id del settore per cui cercare i prezzi settore evento.
	 * @return Una lista di prezzi settore evento corrispondente all'id dell'evento e all'id del settore.
	 * @throws NotFoundExceptionCustom Se non sono presenti prezzi settore evento per gli id specificati.
	 */
	@Override
	public List<PrezzoSettoreEvento> findAllByIdEventoAndIdSettore(long idEvento, long idSettore) {
		return repo.findAllByIdEventoAndIdSettore(idEvento, idSettore)
				.orElseThrow(() -> new NotFoundExceptionCustom("PrezziSettoreEvento con id_evento "+idEvento+" e con id_settore "+idSettore+" non trovati"));
	}


	@Override
	public List<PrezzoSettoreEvento> findAllByIsCancellatoFalse() {
		return repo.findAllByIsCancellatoFalse().orElseThrow(() -> new NotFoundExceptionCustom("Nessun prezzo settore evento è disponibile"));
	}


	/**
	 * Elimina i prezzi settore evento per l'id del settore specificato impostando il flag 'available' su false.
	 * 
	 * @param idSettore L'id del settore.
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void eliminaByIdSettore(long idSettore) {
		findAllByIdSettoreAndIsCancellatoFalse(idSettore);
		repo.eliminaByIdSettore(idSettore);
	}

	/**
	 * Elimina i prezzi settore evento per l'id dell'evento specificato impostando il flag 'available' su false.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void eliminaByIdEvento(long idEvento) {
		findAllByIdEventoAndIsCancellatoFalse(idEvento);
		repo.eliminaByIdEvento(idEvento);
	}

	/**
	 * Elimina i prezzi settore evento per l'id del settore e l'id dell'evento specificati impostando il flag 'available' su false.
	 * 
	 * @param idSettore L'id del settore.
	 * @param idEvento L'id dell'evento.
	 * @throws DataAccessException Se si verificano eccezioni di accesso ai dati durante l'operazione.
	 */
	@Override
	@Transactional(rollbackOn = DataAccessException.class)
	public void eliminaByIdSettoreAndIdEvento(long idSettore, long idEvento) {
		findAllByIdEventoAndIdSettoreAndIsCancellatoFalse(idEvento, idSettore);
		repo.eliminaByIdSettoreAndIdEvento(idSettore, idEvento);
	}

	/**
	 * Trova tutti i prezzi settore evento per l'id dell'evento che sono disponibili.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return Una lista di prezzi settore evento disponibili, se presenti.
	 * @throws NotFoundExceptionCustom Se non sono presenti prezzi settore evento disponibili per l'id specificato.
	 */
	@Override
	public List<PrezzoSettoreEvento> findAllByIdEventoAndIsCancellatoFalse(long idEvento) {
		return repo.findAllByIdEventoAndIsCancellatoFalse(idEvento)
				.orElseThrow(() -> new NotFoundExceptionCustom("PrezziSettoreEvento con id evento "+idEvento+" non trovati"));
	}

	/**
	 * Trova tutti i prezzi settore evento per l'id del settore che sono disponibili.
	 * 
	 * @param idSettore L'id del settore.
	 * @return Una lista di prezzi settore evento disponibili, se presenti.
	 * @throws NotFoundExceptionCustom Se non sono presenti prezzi settore evento disponibili per l'id specificato.
	 */
	@Override
	public List<PrezzoSettoreEvento> findAllByIdSettoreAndIsCancellatoFalse(long idSettore) {
		return repo.findAllByIdSettoreAndIsCancellatoFalse(idSettore)
				.orElseThrow(() -> new NotFoundExceptionCustom("PrezziSettoreEvento con id settore "+idSettore+" non trovati"));
	}

	/**
	 * Trova tutti i prezzi settore evento per l'id dell'evento e l'id del settore che sono disponibili.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return Una lista di prezzi settore evento disponibili, se presenti.
	 * @throws NotFoundExceptionCustom Se non sono presenti prezzi settore evento disponibili per gli id specificati.
	 */
	@Override
	public List<PrezzoSettoreEvento> findAllByIdEventoAndIdSettoreAndIsCancellatoFalse(long idEvento, long idSettore) {
		return repo.findAllByIdEventoAndIdSettoreAndIsCancellatoFalse(idEvento, idSettore)
				.orElseThrow(() -> new NotFoundExceptionCustom("PrezziSettoreEvento con id_evento "+idEvento+" e con id_settore "+idSettore+" non trovati"));
	}

	@Override
	public PrezzoSettoreEvento findPrezzoSettoreEventoById(long id) {
		return repo.findById(id).orElseThrow(() -> new NotFoundExceptionCustom("Prezzo Settore Evento con id : " + id + " non trovato."));
	}

}
