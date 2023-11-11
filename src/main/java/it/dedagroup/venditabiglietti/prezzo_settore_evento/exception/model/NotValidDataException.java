package it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model;

import lombok.Getter;

/**
 * Eccezione personalizzata che rappresenta un caso in cui i dati forniti non sono validi.
 * Estende RuntimeException e include un messaggio di errore che può essere recuperato tramite il metodo getMessage().
 */
@Getter
public class NotValidDataException extends RuntimeException {
    
    /**
     * Costruttore della classe NotValidDataException.
     * 
     * @param message Il messaggio di errore associato all'eccezione.
     */
    public NotValidDataException(String message) {
        super(message);
    }
}






