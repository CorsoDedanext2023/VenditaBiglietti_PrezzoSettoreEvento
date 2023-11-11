package it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model;

import lombok.Getter;

/**
 * Eccezione personalizzata che rappresenta un errore di tipo "non trovato".
 * Estende la classe {@link RuntimeException} per indicare un'eccezione non verificata
 * che può essere lanciata quando un'entità specifica non è presente come attesa.
 * 
 * La presenza dell'annotazione {@code @Getter} indica la generazione automatica dei metodi getter
 * per i campi della classe.
 */
@Getter
public class NotFoundExceptionCustom extends RuntimeException {

	/**
	 * Costruttore che accetta un messaggio personalizzato sull'errore.
	 * 
	 * @param message Il messaggio di errore personalizzato.
	 */
	public NotFoundExceptionCustom(String message) {
		super(message);
	}
}
