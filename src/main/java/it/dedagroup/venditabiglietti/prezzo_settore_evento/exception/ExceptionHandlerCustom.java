package it.dedagroup.venditabiglietti.prezzo_settore_evento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.response.ErrorMessage;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model.NotFoundExceptionCustom;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.exception.model.NotValidDataException;

/**
 * Gestore delle eccezioni globale per l'applicazione, responsabile di gestire
 * specifiche eccezioni personalizzate e restituire risposte HTTP appropriate con dettagli sull'errore.
 * 
 * Le annotazioni {@code @RestControllerAdvice} e {@code @ExceptionHandler} consentono di definire
 * la gestione delle eccezioni in un unico luogo per l'intera applicazione.
 * 
 * @see NotValidDataException
 * @see NotFoundExceptionCustom
 * @see ErrorMessage
 */
@RestControllerAdvice
public class ExceptionHandlerCustom {
	
	/**
	 * Gestisce le eccezioni di tipo {@link NotValidDataException} restituendo una risposta HTTP BAD_REQUEST
	 * con i dettagli dell'errore incapsulati in un oggetto {@link ErrorMessage}.
	 * 
	 * @param e L'eccezione di tipo {@link NotValidDataException} catturata.
	 * @return Una risposta HTTP BAD_REQUEST con i dettagli dell'errore.
	 */
	@ExceptionHandler(NotValidDataException.class)
	ResponseEntity<ErrorMessage> getNotValidDataException(NotValidDataException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
	}
	
	/**
	 * Gestisce le eccezioni di tipo {@link NotFoundExceptionCustom} restituendo una risposta HTTP NOT_FOUND
	 * con i dettagli dell'errore incapsulati in un oggetto {@link ErrorMessage}.
	 * 
	 * @param e L'eccezione di tipo {@link NotFoundExceptionCustom} catturata.
	 * @return Una risposta HTTP NOT_FOUND con i dettagli dell'errore.
	 */
	@ExceptionHandler(NotFoundExceptionCustom.class)
	ResponseEntity<ErrorMessage> getNotFoundException(NotFoundExceptionCustom e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage(), HttpStatus.NOT_FOUND.value()));
	}
}
