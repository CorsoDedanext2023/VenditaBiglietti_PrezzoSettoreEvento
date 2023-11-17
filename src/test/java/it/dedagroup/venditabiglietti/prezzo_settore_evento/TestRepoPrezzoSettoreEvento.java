package it.dedagroup.venditabiglietti.prezzo_settore_evento;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.model.PrezzoSettoreEvento;
import it.dedagroup.venditabiglietti.prezzo_settore_evento.repository.PrezzoSettoreEventoRepository;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional(rollbackOn = DataAccessException.class)
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRepoPrezzoSettoreEvento {
	
	@Autowired
	PrezzoSettoreEventoRepository repo;
	
	
	@Test
	public void testFindAll() throws Exception {
		 List<PrezzoSettoreEvento> lista = repo.findAll();
		 assertEquals(5, lista.size());
		 
	}
	
	@Test
	public void testFindById() throws Exception {
		assertThat(repo.findById(1L).get())
		.isNotNull()
		.extracting("idSettore","idEvento","prezzo")
		.containsExactly(1L,1L,20.00);
		
	}
	
	@Test
	public void testSave() throws Exception {
		PrezzoSettoreEvento pse = new PrezzoSettoreEvento();
		pse.setIdEvento(5);
		pse.setIdSettore(7);
		pse.setPrezzo(25.00);
		PrezzoSettoreEvento pseAggiunto = repo.save(pse);
		assertThat(pseAggiunto)
		.isNotNull()
		.extracting("idSettore","idEvento","prezzo")
		.containsExactly(7L,5L,25.00);
	}
	
	@Test
	public void testFindAllBySettore() throws Exception {
		List<PrezzoSettoreEvento> lista = repo.findAllByIdSettore(1)
				.orElse(null);
		assertNotNull(lista);
		assertEquals(2, lista.size());
			
	}
	
	@Test
	public void testFindAllByIdEvento() throws Exception {
		List<PrezzoSettoreEvento> lista = repo.findAllByIdEvento(1)
				.orElse(null);
		assertNotNull(lista);
		assertEquals(3, lista.size());
		
	}
	
	@Test
	public void testFindAllByIdEventoAndIdSettore() throws Exception {
		List<PrezzoSettoreEvento> lista = repo.findAllByIdEventoAndIdSettore(1, 1)
				.orElse(null);
		assertNotNull(lista);
		assertEquals(1, lista.size());
	}
	
	@Test
	public void testFindAllByIdEventoAndIsAvailableTrue() throws Exception {
		List<PrezzoSettoreEvento> lista = repo.findAllByIdEventoAndIsCancellatoFalse(1)
				.orElse(null);
		assertNotNull(lista);
		assertEquals(3, lista.size());
	}
	
	@Test
	public void testFindAllByIdSettoreAndIsAvailableTrue() throws Exception {
		List<PrezzoSettoreEvento> lista = repo.findAllByIdSettoreAndIsCancellatoFalse(1)
				.orElse(null);
		assertNotNull(lista);
		assertEquals(2, lista.size());
	}
	
	@Test
	public void testFindAllByIdEventoAndIdSettoreAndIsAvailableTrue() throws Exception {
		List<PrezzoSettoreEvento> lista = repo.findAllByIdEventoAndIdSettoreAndIsCancellatoFalse(1, 1)
				.orElse(null);
		assertNotNull(lista);
		assertEquals(1, lista.size());
	}
	
	@Test
	public void modificaPrezzoByIdSettoreAndIdEvento() throws Exception{
		PrezzoSettoreEvento pse = new PrezzoSettoreEvento();
		pse.setIdEvento(1);
		pse.setIdSettore(1);
		pse.setPrezzo(100.00);
		repo.save(pse);
		repo.modificaPrezzoByIdSettoreAndIdEvento(50.00,1,1);
		PrezzoSettoreEvento pseModificato = repo.findById(1L).orElse(null);
		assertEquals(50.00, pseModificato.getPrezzo());
		
	}
	
	@Test
	public void testEliminaByIdSettore() throws Exception {
		PrezzoSettoreEvento pse = new PrezzoSettoreEvento();
		pse.setIdEvento(1);
		pse.setIdSettore(1);
		pse.setPrezzo(100.00);
		repo.save(pse);
		repo.eliminaByIdSettore(1L);
		Optional<PrezzoSettoreEvento> pseEliminato = repo.findById(1L);
		assertTrue(pseEliminato.isPresent());
		assertTrue(pseEliminato.get().isCancellato());
		
	}
	
	@Test
	public void testEliminaByIdEvento() throws Exception {
		PrezzoSettoreEvento pse = new PrezzoSettoreEvento();
		pse.setIdEvento(1);
		pse.setIdSettore(1);
		pse.setPrezzo(100.00);
		repo.save(pse);
		repo.eliminaByIdEvento(1L);
		Optional<PrezzoSettoreEvento> pseEliminato = repo.findById(1L);
		assertTrue(pseEliminato.isPresent());
		assertTrue(pseEliminato.get().isCancellato());
	}
	
	@Test
	public void testEliminaByidSettoreAndIdEvento() {
		PrezzoSettoreEvento pse = new PrezzoSettoreEvento();
		pse.setIdEvento(1);
		pse.setIdSettore(1);
		pse.setPrezzo(100.00);
		repo.save(pse);
		repo.eliminaByIdSettoreAndIdEvento(1L,1L);
		Optional<PrezzoSettoreEvento> pseEliminato = repo.findById(1L);
		assertTrue(pseEliminato.isPresent());
		assertTrue(pseEliminato.get().isCancellato());
	}
		
	

}
