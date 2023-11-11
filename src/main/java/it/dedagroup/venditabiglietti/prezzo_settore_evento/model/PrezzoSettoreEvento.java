package it.dedagroup.venditabiglietti.prezzo_settore_evento.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PrezzoSettoreEvento {

	/**
	 * Identificatore univoco del prezzo del settore per un evento.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * Identificatore del settore associato al prezzo.
	 */
	@Column(nullable = false)
	private long idSettore;

	/**
	 * Identificatore dell'evento associato al prezzo.
	 */
	@Column(nullable = false)
	private long idEvento;

	/**
	 * Importo del prezzo del settore per l'evento.
	 */
	@Column(nullable = false)
	private double prezzo;

	/**
	 * Flag che indica se il prezzo del settore per l'evento è disponibile o meno.
	 */
	@Column(nullable = false)
	private boolean isAvailable = true;

}


