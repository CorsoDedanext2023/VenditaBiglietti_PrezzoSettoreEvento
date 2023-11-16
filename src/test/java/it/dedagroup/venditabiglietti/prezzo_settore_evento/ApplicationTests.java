package it.dedagroup.venditabiglietti.prezzo_settore_evento;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
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
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
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
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
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
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andReturn();
		
	}
	
	@Test
	void getListaByIdSettoreError() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-settore")
				.param("idSettore", "0")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void getListaByIdSettoreCorretto() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-settore")
				.param("idSettore", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	void getListaByIdEventoError() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento")
				.param("idEvento", "0")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	@Order(3)
	void getListaByIdEventoCorreto() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento")
				.param("idEvento", "2")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$[0].idSettore").value(1))
		.andExpect(jsonPath("$[0].idEvento").value(2))
		.andExpect(jsonPath("$[0].prezzo").value(25.00))
		.andExpect(jsonPath("$[0].available").value(true))
		.andExpect(jsonPath("$[1].idSettore").value(2))
		.andExpect(jsonPath("$[1].idEvento").value(2))
		.andExpect(jsonPath("$[1].prezzo").value(22.00))
		.andExpect(jsonPath("$[1].available").value(true))
		.andReturn();
	}
	
	@Test
	void modificaIdSettoreErrore() throws Exception{
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-settore")
				.param("idPse", "0")
				.param("idSettore", "-1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void modificaIdSettoreCorretto() throws Exception{
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-settore")
				.param("idPse", "1")
				.param("idSettore", "4")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	
	}
	
	@Test
	void modificaIdEventoErrore() throws Exception{
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-evento")
				.param("idPse", "0")
				.param("idEvento", "-1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void modificaIdEventoCorretto() throws Exception{
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-evento")
				.param("idPse", "1")
				.param("idEvento", "4")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	void modificaPrezzoErrorePrezzo() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdSettore(1);
		dto.setIdEvento(1);
		dto.setPrezzo(-1);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-prezzo")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void modificaPrezzoErroreIdSettore() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdSettore(-5);
		dto.setIdEvento(1);
		dto.setPrezzo(10.00);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-prezzo")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void modificaPrezzoErroreidEvento() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdSettore(1);
		dto.setIdEvento(-1);
		dto.setPrezzo(100.00);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-prezzo")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void modificaPrezzoCorretto() throws Exception{
		PrezzoSettoreEventoDtoRequest dto = new PrezzoSettoreEventoDtoRequest();
		dto.setIdSettore(1);
		dto.setIdEvento(1);
		dto.setPrezzo(100.00);
		String json = mapper.writeValueAsString(dto);
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/modifica-prezzo")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	@Order(1)
	void getListaByIdSettoreAndByIdEventoCorretto() throws Exception{
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento-settore")
				.param("idEvento", "1")
				.param("idSettore", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$[0].idSettore").value(1))
		.andExpect(jsonPath("$[0].idEvento").value(1))
		.andExpect(jsonPath("$[0].prezzo").value(20.00))
		.andExpect(jsonPath("$[0].available").value(true))
		.andReturn();
	}
	
	@Test
	void getListaByIdSettoreAndByIdEventoErroreidEvento() throws Exception{
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento-settore")
				.param("idEvento", "-1")
				.param("idSettore", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void getListaByIdSettoreAndByIdEventoErroreidSettore() throws Exception{
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento-settore")
				.param("idEvento", "1")
				.param("idSettore", "-1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	@Order(2)
	void getListaByIdSettoreAndByIdEventoAndIsAvailableTrueCorretto() throws Exception{
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento-settore-is-available")
				.param("idEvento", "1")
				.param("idSettore", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$[0].idSettore").value(1))
		.andExpect(jsonPath("$[0].idEvento").value(1))
		.andExpect(jsonPath("$[0].prezzo").value(20.00))
		.andExpect(jsonPath("$[0].available").value(true))
		.andReturn();
	}
	
	@Test
	void getListaByIdSettoreAndByIdEventoAndIsAvailableTrueErroreIdEvento() throws Exception{
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento-settore-is-available")
				.param("idEvento", "-1")
				.param("idSettore", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void getListaByIdSettoreAndByIdEventoAndIsAvailableTrueErroreIdSettore() throws Exception{
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento-settore-is-available")
				.param("idEvento", "1")
				.param("idSettore", "opp")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	@Order(4)
	void getListaByIdEventoAndIsAvailableTrueCorreto() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento")
				.param("idEvento", "2")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$[0].idSettore").value(1))
		.andExpect(jsonPath("$[0].idEvento").value(2))
		.andExpect(jsonPath("$[0].prezzo").value(25.00))
		.andExpect(jsonPath("$[0].available").value(true))
		.andExpect(jsonPath("$[1].idSettore").value(2))
		.andExpect(jsonPath("$[1].idEvento").value(2))
		.andExpect(jsonPath("$[1].prezzo").value(22.00))
		.andExpect(jsonPath("$[1].available").value(true))
		.andReturn();
	}
	
	@Test
	void getListaByIdEventoAndIsAvailableTrueErroreidEvento() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-evento")
				.param("idEvento", "-4143243243")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	@Order(5)
	void getListaByIdSettoreAndIsAvailableTrueCorretto() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-settore")
				.param("idSettore", "3")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$[0].idSettore").value(3))
		.andExpect(jsonPath("$[0].idEvento").value(1))
		.andExpect(jsonPath("$[0].prezzo").value(30.00))
		.andExpect(jsonPath("$[0].available").value(true))
		.andReturn();
	}
	
	@Test
	@Order(6)
	void getListaByIdSettoreAndIsAvailableTrueisEmpty() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-settore")
				.param("idSettore", "7")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$").isEmpty())
		.andReturn();
	}
	
	@Test
	void getListaByIdSettoreAndIsAvailableTrueErroreIdSettore() throws Exception {
		mock.perform(MockMvcRequestBuilders.get("/prezzi-settore-evento/lista-by-settore")
				.param("idSettore", "32b423u")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	
	@Test
	void eliminaByIdCorretto() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-id")
				.param("idPse", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	void eliminaByIdCorrettoErroridPse() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-id")
				.param("idPse", "ewgew532")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void eliminaByidEventoCorretto() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-evento")
				.param("idEvento", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	void eliminaByidEventoErroreIdEvento() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-evento")
				.param("idEvento", "svgr6gvte6")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void eliminaByidSettoreCorretto() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-settore")
				.param("idSettore", "1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	void eliminaByidSettoreErroreIdSettore() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-settore")
				.param("idSettore", "fb3rifgb43b")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	

	@Test
	void eliminaByidSettoreAndIdEventoCorretto() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-settore-evento")
				.param("idSettore", "2")
				.param("idEvento", "2")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn();
	}
	
	@Test
	void eliminaByidSettoreAndIdEventoErroreIdSettore() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-settore-evento")
				.param("idSettore", "wefg3f4")
				.param("idEvento", "2")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
	@Test
	void eliminaByidSettoreAndIdEventoErroreIdEvento() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/prezzi-settore-evento/elimina-by-settore-evento")
				.param("idSettore", "2")
				.param("idEvento", "rvervger")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(MockMvcResultMatchers.status().isBadRequest())
		.andReturn();
	}
	
}
