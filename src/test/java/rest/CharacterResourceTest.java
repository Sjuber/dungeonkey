package rest;

import com.google.gson.Gson;
import entities.AbillityScores;
import entities.Character;
import entities.Player;
import entities.Role;
import facades.CharacterFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test

@Disabled

public class CharacterResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static CharacterFacade facade;
    private static Character ch;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CharacterFacade.getCharacterFacade(emf);
        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager emDelete = emf.createEntityManager();
        try {
            emDelete.getTransaction().begin();
            emDelete.createQuery("DELETE FROM Character").executeUpdate();
            emDelete.createQuery("DELETE FROM Role").executeUpdate();
            emDelete.createQuery("DELETE FROM Player").executeUpdate();
            emDelete.getTransaction().commit();
        } finally {
            emDelete.close();
        }
        EntityManager em = emf.createEntityManager();
        Player player1 = new Player("Nikolaj", "Hamster16");
        Player DM = new Player("Cathrine", "Portraet11");
        Player player2 = new Player("Jens", "Skeletor69");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        AbillityScores abiSco2 = new AbillityScores(18, 10, 14, 12, 14, 10);
        ch = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1);
        try {
            em.getTransaction().begin();
            Role playerRole = new Role("player");
            Role DMRole = new Role("dungeonmaster");
            player1.addRole(playerRole);
            DM.addRole(DMRole);
            player2.addRole(playerRole);
            //both.addRole(DMRole); // MAN KAN GODT HAVE BEGGE ROLLER CATHRINE !!!
            em.persist(playerRole);
            em.persist(DMRole);
            em.persist(player1);
            player1.addCharacter(ch);
            em.merge(player1);
            em.persist(DM);
            em.persist(player2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        given().when().get("/xxx").then().statusCode(200);
    }

    //This test assumes the database contains two rows
    @Test
    public void testDummyMsg() throws Exception {
        given()
                .contentType("application/json")
                .get("/xxx/").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello World"));
    }



    @Test
    public void testAbiScores() throws Exception {
        given()
                .contentType("application/json")
                .get("/characters/"+ch.getId()+"/abillityscores").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("strength", equalTo(18));
    }
//        @Test
//    public void testAbiScoresUpdate() throws Exception {
//        given()
//                .contentType("application/json")
//                .accept(MediaType.APPLICATION_JSON)
//                .accept(ContentType.ANY)
//                .content(Gson.toJson(abiSco2))
//                .put("/characters/2/abillityscores").then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("strength", equalTo(18));
//    }
    
    @Test
    public void testSearchRace() throws Exception{
        given()
                .contentType("application/json")
                .get("/characters/searchbyrace/Orc").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("strength", equalTo(18));
    }
    
        @Test
    public void testSearchPlayer() throws Exception{
        given()
                .contentType("application/json")
                .get("/characters/searchbyplayer/Nikolaj").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dexterity", equalTo(8));
    }
    
        @Test
    public void testSearchName() throws Exception{
        given()
                .contentType("application/json")
                .get("/characters/searchbyname/"+ch.getName()).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("constitution", equalTo(14));
    }
}
