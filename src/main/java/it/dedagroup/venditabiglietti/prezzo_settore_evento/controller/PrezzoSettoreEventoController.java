package it.dedagroup.venditabiglietti.prezzo_settore_evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.request.PrezzoSettoreEventoDtoRequest;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.response.ErrorMessage;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.mapper.PrezzoSettoreEventoMapper;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.servicedef.PrezzoSettoreEventoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

/**
 * Controller che gestisce le operazioni relative ai prezzi settore evento.
 */
@RestController
@Validated
@Tag(name = "Gestione dei Prezzi Settore Evento",
description = "Questo modulo gestiscele API di operazioni di lettura, cancellazione, modifica e aggiunta relative ai prezzi dei settori per gli eventi nell'applicativo Prezzo_Settore_Evento. "
	     + "Fornisce funzionalità per visualizzare, modificare e eliminare prezzi settore evento in base a vari criteri come settore, evento, e stato di cancellazione."
	)
public class PrezzoSettoreEventoController {
	
	@Autowired
	private PrezzoSettoreEventoService pseService;
	@Autowired
	private PrezzoSettoreEventoMapper pseMapper;
	
	/**
	 * Aggiunge un nuovo prezzo settore evento.
	 * 
	 * @param request La richiesta per aggiungere un prezzo settore evento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(
		    summary = "Aggiungi un nuovo prezzo settore evento",
		    description = "Questo endpoint consente di inserire un nuovo prezzo associato a un settore e un evento specifici. "
		                + "I dettagli del prezzo sono forniti attraverso una richiesta di tipo PrezzoSettoreEventoDtoRequest. "
		                + "Se l'operazione è completata con successo, viene restituito un codice di stato 200. "
		                + "In caso di richiesta non valida, viene restituita una risposta con codice 400 e un oggetto di tipo ErrorMessage. "
		                + "In caso di errore interno del server, viene restituito un codice 500."
		)
		@ApiResponses(value = {
		    @ApiResponse(description = "Operazione riuscita", responseCode = "200"),
		    @ApiResponse(description = "Richiesta non valida", responseCode = "400", content = @Content(
		        mediaType = MediaType.APPLICATION_JSON_VALUE,
		        schema = @Schema(implementation = ErrorMessage.class)
		    ))
		})
	@PostMapping("/prezzi-settore-evento/add")
	public ResponseEntity<Void> aggiungiPrezzoSettoreEvento(@Valid @RequestBody PrezzoSettoreEventoDtoRequest request){
		pseService.aggiungiPrezzoSettoreEvento(pseMapper.toPrezzoSettoreEvento(request));
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	       
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato settore.
	 * 
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento.
	 */
	@Operation(summary = "Visualizza la lista dei prezzi settore evento per un determinato settore",
			description = "Questo endpoint restituisce la lista dei prezzi dei settori per un determinato settore.")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@GetMapping("/prezzi-settore-evento/lista-by-settore/id-settore/{idSettore}")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdSettore(
			@PathVariable
			@Min(value = 1, message = "L'id del settore non è valido") 
			long id){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdSettore(id));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return ResponseEntity con la lista dei prezzi settore evento.
	 */
	@Operation(summary = "Visualizza la lista dei prezzi settore evento per un determinato evento",
			   description = "Questo endpoint restituisce la lista dei prezzi dei settori per un determinato evento")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@GetMapping("/prezzi-settore-evento/lista-by-evento/id-evento/{idEvento}")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEvento(@PathVariable @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEvento(idEvento));
	}
	
	/**
	 * Modifica l'id del settore di un prezzo settore evento.
	 * 
	 * @param idPse L'id del PrezzoSettoreEvento.
	 * @param idSettore Il nuovo id del settore.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(summary = "Modifica l'id del settore",
			   description = "Questo endpoint restituisce un'id del settore modificato")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@PostMapping("/prezzi-settore-evento/modifica-settore/id-pse/{idPse}/idSettore/{idSettore}")
	public ResponseEntity<Void> modificaIdSettore(
			@PathVariable("idPse") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long idPse,
			@PathVariable("idSettore") @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		PrezzoSettoreEvento pse = pseService.findById(idPse);
		pse.setIdSettore(idSettore);
		pseService.modificaPrezzoSettoreEvento(pse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	/**
	 * Modifica l'id dell'evento di un prezzo settore evento.
	 * 
	 * @param idPse L'id del PrezzoSettoreEvento.
	 * @param idEvento Il nuovo id dell'evento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */

	@Operation(summary = "Modifica l'id dell'evento",
			description = "Questo endpoint permette di modificare l'id dell'evento")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})

	@PostMapping("/prezzi-settore-evento/modifica-evento/id-pse/{idPse}/id-evento/{idEvento}")
	public ResponseEntity<Void> modificaIdEvento(
			@PathVariable("idPse") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long idPse,
			@PathVariable("idEvento") @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento){
		PrezzoSettoreEvento pse = pseService.findById(idPse);
		pse.setIdEvento(idEvento);
		pseService.modificaPrezzoSettoreEvento(pse);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	/**
	 * Modifica il prezzo di un prezzo settore evento per un determinato settore ed evento.
	 * 
	 * @param request La richiesta con i nuovi dati.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(summary = "Modifica il prezzo per un determinato settore ed evento",
			   description = "Questo endpoint permette di modificare il prezzo di un determinato settore ed evento")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@PostMapping("/prezzi-settore-evento/modifica-prezzo")
	public ResponseEntity<Void> modificaPrezzoByIdSettoreAndIdEvento(@Valid @RequestBody PrezzoSettoreEventoDtoRequest request){
		pseService.modificaPrezzoByIdSettoreAndIdEvento(request.getPrezzo(), request.getIdSettore(), request.getIdEvento());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento e settore.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento.
	 */
	@Operation(summary = "Visualizza la lista dei prezzi per determinato evento e settore",
			   description = "Questo endpoint resitutisce la lista dei prezzi per un determinato evento e settore")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@GetMapping("/prezzi-settore-evento/lista-by-evento-settore/id-evento/{idEvento}/id-settore/{idSettore}")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEventoAndIdSettore(
			@PathVariable("idEvento") @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento,
			@PathVariable("idSettore") @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEventoAndIdSettore(idEvento, idSettore));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento e settore che sono disponibili.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento disponibili.
	 */
	@Operation(summary = "Visualizza la lista dei prezzi per un determinato evento e settore che sono disponibili",
			   description = "Questo endpoint restituisce la lista dei prezzi per un determinato evento e settore,nel caso in cui siano disponibili")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@GetMapping("/prezzi-settore-evento/lista-by-evento-settore-is-cancellato-false/id-evento/{idEvento}/id-settore/{idSettore}")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEventoAndIdSettoreAndIsCancellatoFalse(
			@PathVariable("idEvento") @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento,
			@PathVariable("idSettore") @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEventoAndIdSettoreAndIsCancellatoFalse(idEvento, idSettore));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato evento che sono disponibili.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return ResponseEntity con la lista dei prezzi settore evento disponibili.
	 */
	@Operation(summary = "Visualizza la lista dei prezzi per un determinato evento che sono disponibili",
			   description = "Questo endpoint restituisce la lista dei prezzi per un determinato evento, nel caso in cui siano disponibili")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@GetMapping("/prezzi-settore-evento/lista-by-evento-is-cancellato-false/id-evento/{id}")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdEventoAndIsCancellatoFalse(
			@PathVariable("id") @Min(value = 1, message = "L'id dell'evento non è valido") long id){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdEventoAndIsCancellatoFalse(id));
	}
	
	/**
	 * Ottiene la lista dei prezzi settore evento per un determinato settore che sono disponibili.
	 * 
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con la lista dei prezzi settore evento disponibili.
	 */
	@Operation(summary = "Visualizza la lista dei prezzi per un determinato settore che sono disponibili",
			   description = "Questo endpoint restituisce la lista dei prezzi per un determinato settore, nel caso in cui siano disponibili")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@GetMapping("/prezzi-settore-evento/lista-by-settore-is-cancellato-false/id-settore/{id}")
	public ResponseEntity<List<PrezzoSettoreEvento>> getListaPrezzoSettoreEventoByIdSettoreAndIsCancellatoFalse(
			@PathVariable("id") @Min(value = 1, message = "L'id del settore non è valido") long id){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIdSettoreAndIsCancellatoFalse(id));
	}
	
	/**
	 * Elimina un prezzo settore evento per id.
	 * 
	 * @param idPse L'id del PrezzoSettoreEvento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(summary = "Elimina un oggetto prezzo settore evento per id",
			   description = "Questo endpoint elimina un oggetto prezzo settore evento attraverso l'id")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@PostMapping("/prezzi-settore-evento/elimina-by-id/{id}")
	public ResponseEntity<Void> eliminaPrezzoSettoreEventoById(
			@PathVariable("id") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long id){
		pseService.eliminaPrezzoSettoreEvento(pseService.findById(id));
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	
	/**
	 * Elimina i prezzi settore evento per un determinato settore.
	 * 
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(summary = "Elimina un oggetto prezzo settore evento per un determinato settore",
			description = "Questo endpoint elimina un oggetto prezzo settore evento attraverso l'id di un settore")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@PostMapping("/prezzi-settore-evento/elimina-by-settore/id-settore/{id}")
	public ResponseEntity<Void> eliminaPrezzoSettoreEventoByIdSettore(
			@PathVariable("id") @Min(value = 1, message = "L'id del settore non è valido") long id){
		pseService.eliminaByIdSettore(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Elimina i prezzi settore evento per un determinato evento.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(summary = "Elimina un oggetto prezzo settore evento per un determinato evento",
			   description = "Questo endpoint elimina un oggetto prezzo settore evento attraverso l'id di un evento")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@PostMapping("/prezzi-settore-evento/elimina-by-evento/id-evento/{id}")
	public ResponseEntity<Void> eliminaPrezzoSettoreEventoByIdEvento(
			@PathVariable("id") @Min(value = 1, message = "L'id del PrezzoSettoreEvento non è valido") long id){
		pseService.eliminaByIdEvento(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	/**
	 * Elimina i prezzi settore evento per un determinato settore ed evento.
	 * 
	 * @param idEvento L'id dell'evento.
	 * @param idSettore L'id del settore.
	 * @return ResponseEntity con lo stato della richiesta.
	 */
	@Operation(summary = "Elimina un oggetto prezzo settore evento per un determinato settore ed evento",
			   description = "Questo endpoint elimina un oggetto prezzo settore evento attraverso l'id di un settore e l'id di un evento")
	@ApiResponses(value = {
			@ApiResponse(description = "Operazione riuscita", responseCode = "200"),
			@ApiResponse(description = "Richiesta non valida",responseCode = "400", content = @Content(
			        mediaType = MediaType.APPLICATION_JSON_VALUE,
			        schema = @Schema(implementation = ErrorMessage.class)
			    ))
	})
	@PostMapping("/prezzi-settore-evento/elimina-by-settore-evento/id-evento/{idEvento}/id-settore/{idSettore}")
	public ResponseEntity<Void> eliminaByIdSettoreAndIdEvento(
			@PathVariable("idEvento") @Min(value = 1, message = "L'id dell'evento non è valido") long idEvento,
			@PathVariable("idSettore") @Min(value = 1, message = "L'id del settore non è valido") long idSettore){
		pseService.eliminaByIdSettoreAndIdEvento(idSettore, idEvento);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping("/prezzi-settore-evento/lista-is-cancellato-false")
	public ResponseEntity<List<PrezzoSettoreEvento>> getAllIsCancellatoFalse(){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findAllByIsCancellatoFalse());
	}
	
	@GetMapping("/prezzi-settore-evento/id/{id}")
	public ResponseEntity<PrezzoSettoreEvento> findById(@PathVariable @Min(value = 1,message = "l'Id del prezzo settore evento non è valido")long id){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.findById(id));
	}
	
	@PostMapping("/prezzi-settore-evento/ids-evento")
	public ResponseEntity<List<PrezzoSettoreEvento>> findAllByIdsEvento(@RequestBody List<Long> idEventi){
		return ResponseEntity.status(HttpStatus.OK).body(pseService.getListaPseByIdsEvento(idEventi));
	}
}
