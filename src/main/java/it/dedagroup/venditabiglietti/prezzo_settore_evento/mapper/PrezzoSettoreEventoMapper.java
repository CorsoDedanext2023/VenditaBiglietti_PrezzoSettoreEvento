package it.dedagroup.venditabiglietti.prezzo_settore_evento.mapper;

import org.springframework.stereotype.Component;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.request.PrezzoSettoreEventoDtoRequest;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;

/**
 * Componente che fornisce la funzionalità di mappatura tra oggetti DTO (Data Transfer Object)
 * e entità {@link PrezzoSettoreEvento}.
 * 
 * Questo componente contiene un metodo per convertire un oggetto di tipo {@link PrezzoSettoreEventoDtoRequest}
 * in un'istanza di {@link PrezzoSettoreEvento}.
 * 
 * @see PrezzoSettoreEvento
 * @see PrezzoSettoreEventoDtoRequest
 */
@Component
public class PrezzoSettoreEventoMapper {
	
	/**
	 * Converte un oggetto di tipo {@link PrezzoSettoreEventoDtoRequest} in un'istanza di {@link PrezzoSettoreEvento}.
	 * 
	 * @param dto L'oggetto DTO da convertire.
	 * @return Un'istanza di {@link PrezzoSettoreEvento} risultante dalla conversione dell'oggetto DTO.
	 */
	public PrezzoSettoreEvento toPrezzoSettoreEvento(PrezzoSettoreEventoDtoRequest dto) {
		PrezzoSettoreEvento pse = new PrezzoSettoreEvento();
		pse.setIdEvento(dto.getIdEvento());
		pse.setIdSettore(dto.getIdSettore());
		pse.setPrezzo(dto.getPrezzo());
		return pse;
	}
}
