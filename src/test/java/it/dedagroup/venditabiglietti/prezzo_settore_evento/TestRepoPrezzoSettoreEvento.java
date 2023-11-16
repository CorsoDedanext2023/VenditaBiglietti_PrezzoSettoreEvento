package it.dedagroup.venditabiglietti.prezzo_settore_evento;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.repository.PrezzoSettoreEventoRepository;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRepoPrezzoSettoreEvento {
	
	@Autowired
	PrezzoSettoreEventoRepository repo;

}
