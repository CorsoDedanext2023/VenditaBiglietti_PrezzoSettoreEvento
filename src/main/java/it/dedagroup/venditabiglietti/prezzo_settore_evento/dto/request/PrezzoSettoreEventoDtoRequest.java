package it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Classe DTO (Data Transfer Object) utilizzata per la richiesta di creazione o modifica di un prezzo settore evento.
 * Include informazioni come l'ID del settore, l'ID dell'evento e il prezzo associato.
 */
@Data
public class PrezzoSettoreEventoDtoRequest {
    
    /**
     * L'ID del settore associato al prezzo settore evento.
     */
    @Min(value = 1, message = "Id settore non valido")
    private long idSettore;

    /**
     * L'ID dell'evento associato al prezzo settore evento.
     */
    @Min(value = 1, message = "Id evento non valido")
    private long idEvento;

    /**
     * Il prezzo associato al prezzo settore evento.
     */
    @Min(value = 0, message = "Prezzo non valido")
    private double prezzo;
}
