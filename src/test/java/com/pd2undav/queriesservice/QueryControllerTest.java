package com.pd2undav.queriesservice;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    void getMostListenedAlbumTest() throws Exception {
        Ambito returnedAmbito = new Ambito("idAmbito", "nombre", "tipo");
        when(this.restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("ambitoID");
        when(this.restTemplate.getForObject(anyString(), eq(Ambito.class))).thenReturn(returnedAmbito);

        this.mockMvc.perform(get("/escuchas/album")
                .param("fechaStart", "fechaStart")
                .param("fechaEnd", "fechaEnd"))
                .andExpect(jsonPath("$.id_ambito").value(returnedAmbito.getId_ambito()));

    }

    @Test
    void getMostListenedRadioTest() throws Exception {
        Ambito returnedAmbito = new Ambito("idAmbito", "nombre", "tipo");
        when(this.restTemplate.getForObject(anyString(), eq(String.class))).thenReturn("ambitoID");
        when(this.restTemplate.getForObject(anyString(), eq(Ambito.class))).thenReturn(returnedAmbito);

        this.mockMvc.perform(get("/escuchas/radio")
                .param("fechaStart", "fechaStart")
                .param("fechaEnd", "fechaEnd"))
                .andExpect(jsonPath("$.id_ambito").value(returnedAmbito.getId_ambito()));

    }

    @Test
    void getArtistaAmbitosTest() throws Exception {
        Ambito[] returnedAmbitos = {new Ambito("idAmbito1", "nombre1", "tipo1"),
                                    new Ambito("idAmbito2", "nombre2", "tipo2")};
        ResponseEntity<Ambito[]> mockResponse = new ResponseEntity<Ambito[]>(returnedAmbitos, HttpStatus.OK);

        when(this.restTemplate.getForEntity(anyString(), eq(Ambito[].class))).thenReturn(mockResponse);


        this.mockMvc.perform(get("/artista/ambitos")
                .param("artistaID", "artistaID"))
                .andExpect(jsonPath("$[0].id_ambito").value(returnedAmbitos[0].getId_ambito()))
                .andExpect(jsonPath("$[1].id_ambito").value(returnedAmbitos[1].getId_ambito()));

    }

    @Test
    void getAmbitoCancionesTest() throws Exception {
        Cancion[] returnedCanciones = {new Cancion("idCancion1", "nombre1"),
                                     new Cancion("idCancion2", "nombre2")};
        ResponseEntity<Cancion[]> mockResponse = new ResponseEntity<Cancion[]>(returnedCanciones, HttpStatus.OK);

        when(this.restTemplate.getForEntity(anyString(), eq(Cancion[].class))).thenReturn(mockResponse);


        this.mockMvc.perform(get("/album/canciones")
                .param("ambitoID", "ambitoID"))
                .andExpect(jsonPath("$[0].id_cancion").value(returnedCanciones[0].getId_cancion()))
                .andExpect(jsonPath("$[1].id_cancion").value(returnedCanciones[1].getId_cancion()));
    }

    @Test
    void getUserTest() throws Exception {
        User returnedUser = new User("idUser", "username", "nombre");
        when(this.restTemplate.getForObject(anyString(), eq(User.class))).thenReturn(returnedUser);

        this.mockMvc.perform(get("/user")
                .param("userID", "userID"))
                .andExpect(jsonPath("$.id_user").value(returnedUser.getId_user()));
    }

    @Test
    void getNewAlbumForUserExistingAlbumTest() throws Exception {
        String[] listenedArtists = {"artista1", "artista2"};
        String[] listenedAlbums = {"album1", "album2"};
        String[] allAlbums = {"album1", "album2", "album3"};

        when(this.restTemplate.getForEntity(anyString(), eq(String[].class)))
                .thenReturn(new ResponseEntity<String[]>(listenedArtists, HttpStatus.OK))
                .thenReturn(new ResponseEntity<String[]>(listenedAlbums, HttpStatus.OK))
                .thenReturn(new ResponseEntity<String[]>(allAlbums, HttpStatus.OK));

        Ambito returnedAmbito = new Ambito("album3", "nombre", "tipo");
        when(this.restTemplate.getForObject(anyString(), eq(Ambito.class))).thenReturn(returnedAmbito);

        this.mockMvc.perform(get("/user/discovery")
                .param("userID", "userID"))
                .andExpect(jsonPath("$.id_ambito").value(returnedAmbito.getId_ambito()));
    }

    void getNewAlbumForUserNonExistingAlbumTest() throws Exception {
        String[] listenedArtists = {"artista1", "artista2"};
        String[] listenedAlbums = {"album1", "album2"};
        String[] allAlbums = {"album1", "album2"};

        when(this.restTemplate.getForEntity(anyString(), eq(String[].class)))
                .thenReturn(new ResponseEntity<String[]>(listenedArtists, HttpStatus.OK))
                .thenReturn(new ResponseEntity<String[]>(listenedAlbums, HttpStatus.OK))
                .thenReturn(new ResponseEntity<String[]>(allAlbums, HttpStatus.OK));

        Ambito returnedAmbito = new Ambito();
        when(this.restTemplate.getForObject(anyString(), eq(Ambito.class))).thenReturn(returnedAmbito);

        this.mockMvc.perform(get("/user/discovery")
                .param("userID", "userID"))
                .andExpect(jsonPath("$.id_ambito").value((String) null));
    }
}