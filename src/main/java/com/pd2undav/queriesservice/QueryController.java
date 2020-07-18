package com.pd2undav.queriesservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
public class QueryController {

    private static final Logger log = LoggerFactory.getLogger(QueryController.class);

    @Value("${STATISTICS_ADDRESS}")
    private String STATISTICS_ADDRESS;
    @Value("${STATISTICS_PORT}")
    private String STATISTICS_PORT;
    @Value("${MUSIC_ADDRESS}")
    private String MUSIC_ADDRESS;
    @Value("${MUSIC_PORT}")
    private String MUSIC_PORT;
    @Value("${USERS_ADDRESS}")
    private String USERS_ADDRESS;
    @Value("${USERS_PORT}")
    private String USERS_PORT;

    private Random rand = new Random();

    private final RestTemplate restTemplate;

    public QueryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Se debe poder pedir la cantidad de escuchas de cada cancion
    @RequestMapping("/cancion/escuchas")
    public ResponseEntity<Integer> getCancionEscuchas(@RequestParam(value = "cancionID") String cancionID) {
        String url = String.format("http://%s:%s/cancion/escuchas?cancionID=%s", STATISTICS_ADDRESS, STATISTICS_PORT, cancionID);
        Integer escuchas = restTemplate.getForObject(url, Integer.class);

        log.info("Got {} escuchas for Cancion {}", escuchas.toString(), cancionID);

        return new ResponseEntity<Integer>(escuchas, HttpStatus.OK);
    }

    // Se debe poder pedir la cantidad de escuchas de cada album
    @RequestMapping("/album/escuchas")
    public ResponseEntity<Integer> getAmbitoEscuchas(@RequestParam(value = "ambitoID") String ambitoID) {
        String url = String.format("http://%s:%s/ambito/escuchas?ambitoID=%s", STATISTICS_ADDRESS, STATISTICS_PORT, ambitoID);
        Integer escuchas = restTemplate.getForObject(url, Integer.class);

        log.info("Got {} escuchas for Ambito {}", escuchas.toString(), ambitoID);

        return new ResponseEntity<Integer>(escuchas, HttpStatus.OK);
    }

    // La canción mas escuchada en un rango de tiempo
    @RequestMapping("/escuchas/cancion")
    public ResponseEntity<Cancion> getMostListenedCancion(@RequestParam(value="fechaStart") String fechaStart,
                                                          @RequestParam(value="fechaEnd") String fechaEnd) {

        String url1 = String.format("http://%s:%s/escuchas/cancion?fechaStart=%s&fechaEnd=%s", STATISTICS_ADDRESS, STATISTICS_PORT, fechaStart, fechaEnd);
        String cancion_id = restTemplate.getForObject(url1, String.class);

        String url2 = String.format("http://%s:%s/cancion?cancionID=%s", MUSIC_ADDRESS, MUSIC_PORT, cancion_id);
        Cancion cancion = restTemplate.getForObject(url2, Cancion.class);

        log.info("Most listened Cancion from {} to {}: {}", fechaStart, fechaEnd, cancion.getNombre());

        return new ResponseEntity<Cancion>(cancion, HttpStatus.OK);
    }

    // El album mas escuchado en un rango de tiempo
    @RequestMapping("/escuchas/album")
    public ResponseEntity<Ambito> getMostListenedAlbum(@RequestParam(value="fechaStart") String fechaStart,
                                                       @RequestParam(value="fechaEnd") String fechaEnd) {

        String url1 = String.format("http://%s:%s/escuchas/album?fechaStart=%s&fechaEnd=%s", STATISTICS_ADDRESS, STATISTICS_PORT, fechaStart, fechaEnd);
        String album_id = restTemplate.getForObject(url1, String.class);

        String url2 = String.format("http://%s:%s/ambito?ambitoID=%s", MUSIC_ADDRESS, MUSIC_PORT, album_id);
        Ambito album = restTemplate.getForObject(url2, Ambito.class);

        log.info("Most listened Album from {} to {}: {}", fechaStart, fechaEnd, album.getNombre());

        return new ResponseEntity<Ambito>(album, HttpStatus.OK);
    }

    // La radio mas escuchado en un rango de tiempo
    @RequestMapping("/escuchas/radio")
    public ResponseEntity<Ambito> getMostListenedRadio(@RequestParam(value="fechaStart") String fechaStart,
                                                       @RequestParam(value="fechaEnd") String fechaEnd) {

        String url1 = String.format("http://%s:%s/escuchas/radio?fechaStart=%s&fechaEnd=%s", STATISTICS_ADDRESS, STATISTICS_PORT, fechaStart, fechaEnd);
        String radio_id = restTemplate.getForObject(url1, String.class);

        String url2 = String.format("http://%s:%s/ambito?ambitoID=%s", MUSIC_ADDRESS, MUSIC_PORT, radio_id);
        Ambito radio = restTemplate.getForObject(url2, Ambito.class);

        log.info("Most listened Radio from {} to {}: {}", fechaStart, fechaEnd, radio.getNombre());

        return new ResponseEntity<Ambito>(radio, HttpStatus.OK);
    }

    // Debe devolver los discos y radios de un artista
    @RequestMapping("/artista/ambitos")
    public ResponseEntity<Ambito[]> getArtistaAmbitos(@RequestParam(value = "artistaID") String artistaID) {

        String url = String.format("http://%s:%s/artista/ambitos?artistaID=%s", MUSIC_ADDRESS, MUSIC_PORT, artistaID);
        ResponseEntity<Ambito[]> responseEntity = restTemplate.getForEntity(url, Ambito[].class);
        Ambito[] ambitos = responseEntity.getBody();

        log.info("Got the following Ambitos for Artista {}: {}", artistaID, ambitos.toString());

        return new ResponseEntity<Ambito[]>(ambitos, HttpStatus.OK);
    }

    // Debe canciones con su nombre e id de un disco
    @RequestMapping("/album/canciones")
    public ResponseEntity<Cancion[]> getAmbitoCanciones(@RequestParam(value = "ambitoID") String ambitoID) {

        String url = String.format("http://%s:%s/ambito/canciones?ambitoID=%s", MUSIC_ADDRESS, MUSIC_PORT, ambitoID);
        ResponseEntity<Cancion[]> responseEntity = restTemplate.getForEntity(url, Cancion[].class);
        Cancion[] canciones = responseEntity.getBody();

        log.info("Got the following Canciones for Ambito {}: {}", ambitoID, canciones.toString());

        return new ResponseEntity<Cancion[]>(canciones, HttpStatus.OK);
    }

    // Poder pedir un perfil de usuario y que devuelva nombre, username e id
    @RequestMapping("/user")
    public ResponseEntity<User> getUser(@RequestParam(value = "userID") String userID) {

        String url = String.format("http://%s:%s/user?userID=%s", USERS_ADDRESS, USERS_PORT, userID);
        User user = restTemplate.getForObject(url, User.class);

        log.info("Got User: {}", user.toString());

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Agregar un disco que no haya escuchado de un artista que sí haya escuchado
    @RequestMapping("/user/discovery")
    public ResponseEntity<Ambito> getNewAlbumForUser(@RequestParam(value = "userID") String userID) {
        Ambito album = new Ambito();

        String url1 = String.format("http://%s:%s/escuchas/user/artistas?userID=%s", STATISTICS_ADDRESS, STATISTICS_PORT, userID);
        ResponseEntity<String[]> responseEntity1 = restTemplate.getForEntity(url1, String[].class);
        String[] artistas = responseEntity1.getBody();

        String artistaID = artistas[rand.nextInt(artistas.length)];
        log.info("Randomly selected Artista {}", artistaID);

        String url2= String.format("http://%s:%s/escuchas/user/artista/albums?userID=%s&artistaID=%s", STATISTICS_ADDRESS, STATISTICS_PORT, userID, artistaID);
        ResponseEntity<String[]> responseEntity2 = restTemplate.getForEntity(url2, String[].class);
        String[] artistaListenedAlbums = responseEntity2.getBody();

        String url3= String.format("http://%s:%s/artista/albums?artistaID=%s", MUSIC_ADDRESS, MUSIC_PORT, artistaID);
        ResponseEntity<String[]> responseEntity3 = restTemplate.getForEntity(url3, String[].class);
        String[] artistaAlbums = responseEntity3.getBody();

        Set<String> artistaListenedAlbumsSet = new HashSet<String>(Arrays.asList(artistaListenedAlbums));
        Set<String> artistaAlbumsSet = new HashSet<String>(Arrays.asList(artistaAlbums));
        artistaAlbumsSet.removeAll(artistaListenedAlbumsSet);
        String[] notListenedAlbums = artistaAlbumsSet.toArray(new String[artistaAlbumsSet.size()]);

        if (notListenedAlbums.length>0){
            String albumID = notListenedAlbums[rand.nextInt(notListenedAlbums.length)];
            log.info("Randomly selected Album {}", albumID);

            String url4= String.format("http://%s:%s/ambito?ambitoID=%s", MUSIC_ADDRESS, MUSIC_PORT, albumID);
            album = restTemplate.getForObject(url4, Ambito.class);
        }

        return new ResponseEntity<Ambito>(album, HttpStatus.OK);
    }


}
