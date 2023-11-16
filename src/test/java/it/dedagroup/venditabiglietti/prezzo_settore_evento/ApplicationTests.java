package it.dedagroup.venditabiglietti.prezzo_settore_evento;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.dedagroup.venditabiglietti.prezzo_settore_evento.dto.request.PrezzoSettoreEventoDtoRequest;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc

class ApplicationTests {
	
	@Autowired
	MockMvc mock;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	
	@Test
	void aggiungiPseSenzaIdEvento() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdSettore(1);
		dto.setPrezzo(20.0);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void aggiungiPseSenzaIdSettore() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdEvento(1);
		dto.setPrezzo(20.0);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void aggiungiPsePrezzoNegativo() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdEvento(1);
		dto.setIdSettore(1);
		dto.setPrezzo(-20.00);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void aggiungiPse() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdEvento(1);
		dto.setIdSettore(1);
		dto.setPrezzo(20.00);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/add")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isCreated());
		
		
	}


}
