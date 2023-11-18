package it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe utilizzata per rappresentare un messaggio di errore da restituire nelle risposte
 * degli handler delle eccezioni personalizzate. Contiene informazioni come il messaggio di errore,
 * il codice di stato HTTP e il timestamp di generazione del messaggio.
 */
@Getter @Setter
public class ErrorMessage {
    
    /**
     * Il messaggio di errore.
     */
    private String message;
    
    /**
     * Il codice di stato HTTP.
     */
    private int statusCode;
    
    /**
     * Il timestamp di generazione del messaggio.
     */
    private LocalDateTime timeStamp;
    
    /**
     * Costruttore della classe ErrorMessage.
     * 
     * @param message     Il messaggio di errore.
     * @param statusCode  Il codice di stato HTTP.
     */
    public ErrorMessage(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = LocalDateTime.now();
    }
    
   
    
    
    
   
    
    
    
    
    
    
}
