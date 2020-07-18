package com.pd2undav.queriesservice;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCancionEscuchas() throws Exception {
        when(this.restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(Integer.valueOf(3));

        MvcResult result = this.mockMvc.perform(get("/cancion/escuchas").param("cancionID", "cancionID")).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("3", content);
    }

    @Test
    void getAmbitoEscuchas() throws Exception {
        when(this.restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(Integer.valueOf(17));

        MvcResult result = this.mockMvc.perform(get("/album/escuchas").param("ambitoID", "ambitoID")).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("17", content);
    }

    @Test
    void getMostListenedCancion() throws Exception {
        Cancion returnedCancion = new Cancion("idCancion", "nombre");
        when(this.restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("cancionID");
        when(this.restTemplate.getForObject(anyString(), eq(Cancion.class))).thenReturn(returnedCancion);

        this.mockMvc.perform(get("/escuchas/cancion")
                .param("fechaStart", "fechaStart")
                .param("fechaEnd", "fechaEnd"))
                .andExpect(jsonPath("$.id_cancion").value(returnedCancion.getId_cancion()));
    }

    @Test
    void getMostListenedAlbum() {
    }

    @Test
    void getMostListenedPlaylist() {
    }

    @Test
    void getArtistaAmbitos() {
    }

    @Test
    void getAmbitoCanciones() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getNewAlbumForUser() {
    }
}