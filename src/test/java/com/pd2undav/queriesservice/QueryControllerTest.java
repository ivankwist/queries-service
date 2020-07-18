package com.pd2undav.queriesservice;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource(properties = {"STATISTICS_ADDRESS=mock",
                                  "STATISTICS_PORT=mock",
                                  "MUSIC_ADDRESS=mock",
                                  "MUSIC_PORT=mock",
                                  "USERS_ADDRESS=mock",
                                  "USERS_PORT=mock"})
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
    public void testGetCancionEscuchasTest() throws Exception {
        when(this.restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(Integer.valueOf(3));

        MvcResult result = this.mockMvc.perform(get("/cancion/escuchas").param("cancionID", "cancionID")).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("3", content);
    }

    @Test
    void getAmbitoEscuchasTest() throws Exception {
        when(this.restTemplate.getForObject(anyString(), any(Class.class))).thenReturn(Integer.valueOf(17));

        MvcResult result = this.mockMvc.perform(get("/album/escuchas").param("ambitoID", "ambitoID")).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("17", content);
    }

    @Test
    void getMostListenedCancionTest() throws Exception {
        Cancion returnedCancion = new Cancion("idCancion", "nombre");
        when(this.restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("cancionID");
        when(this.restTemplate.getForObject(anyString(), eq(Cancion.class))).thenReturn(returnedCancion);

        this.mockMvc.perform(get("/escuchas/cancion")
                .param("fechaStart", "fechaStart")
                .param("fechaEnd", "fechaEnd"))
                .andExpect(jsonPath("$.id_cancion").value(returnedCancion.getId_cancion()));
    }

    @Test
    void getMostListenedAlbumTest() {
    }

    @Test
    void getMostListenedPlaylistTest() {
    }

    @Test
    void getArtistaAmbitosTest() {
    }

    @Test
    void getAmbitoCancionesTest() {
    }

    @Test
    void getUserTest() {
    }

    @Test
    void getNewAlbumForUserTest() {
    }
}