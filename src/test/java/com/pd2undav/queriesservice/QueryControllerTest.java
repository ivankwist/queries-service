package com.pd2undav.queriesservice;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class QueryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetCancionEscuchasWithExistingId() throws Exception {
        doReturn(3).when(this.restTemplate).getForObject(any(), any());
        MvcResult result = this.mockMvc.perform(get("/cancion/escuchas?cancionID=cancionID")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();

        assertEquals("3", content);
    }

    @Test
    void getAmbitoEscuchas() {
    }

    @Test
    void getMostListenedCancion() {
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