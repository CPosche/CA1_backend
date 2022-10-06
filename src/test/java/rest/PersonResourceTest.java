package rest;

import dtos.HobbyDto;
import dtos.PersonDto;
import dtos.PhoneDto;
import entities.*;
import facades.SuperFacadeTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class PersonResourceTest extends SuperResourceTest{

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/CA1/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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
        super.setup(emf);
        }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons/all").then().statusCode(200);
    }

    @Test
    public void getAllPersons() throws Exception {

        List<PersonDto> personDTOs;

        personDTOs = given()
                .contentType("application/json")
                .when()
                .get("/persons/all")
                .then()
                .extract().body().jsonPath().getList("", PersonDto.class);

        PersonDto p1DTO = new PersonDto(super.testPerson);
        PersonDto p2DTO = new PersonDto(super.testPerson2);
        assertThat(personDTOs, containsInAnyOrder(p1DTO, p2DTO));

    }

    @Test
    public void getPersonByPhoneNumber() throws Exception {
        PersonDto personDTO;

        personDTO = given()
                .contentType(ContentType.JSON)
                .queryParam("phone","71241337")
                .when()
                .get("/persons/phone")
                .then()
                .extract().body().jsonPath().getObject("", PersonDto.class);

        PersonDto p1DTO = new PersonDto(super.testPerson);
        assertThat(personDTO, equalTo(p1DTO));

    }
    @Test
    public void getPersonsByHobby() throws Exception {
        List<PersonDto> personDTOs;

        personDTOs = given()
                .contentType(ContentType.JSON)
                .queryParam("hobby","badminton")
                .when()
                .get("/persons/hobby")
                .then()
                .extract().body().jsonPath().getList("", PersonDto.class);

        PersonDto p1DTO = new PersonDto(super.testPerson);
        PersonDto p2DTO = new PersonDto(super.testPerson2);
        assertThat(personDTOs, containsInAnyOrder(p1DTO, p2DTO));
    }
    @Test
    public void getPersonsByZipAndCityname() throws Exception {
        List<PersonDto> personDTOs;
        personDTOs = given()
                .contentType(ContentType.JSON)
                .queryParam("zipcode","3450")
                .queryParam("cityname","alleroed")
                .when()
                .get("/persons/city")
                .then()
                .extract().body().jsonPath().getList("", PersonDto.class);

        PersonDto p1DTO = new PersonDto(super.testPerson);
        PersonDto p2DTO = new PersonDto(super.testPerson2);
        assertThat(personDTOs, containsInAnyOrder(p1DTO, p2DTO));
    }


    @Test
    public void getCountByHobby() throws Exception {
        given()
                .contentType("application/json")
                .queryParam("hobby", "badminton")
                .get("/persons/count/hobby").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("2"));
    }
    @Test
    public void getCountByZip() throws Exception {
        given()
                .contentType("application/json")
                .queryParam("zip", "3450")
                .get("/persons/count/zip").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body(equalTo("2"));
    }

    @Test
    public void createPerson() {
        Person p1;
        p1 = new Person("f1", "l1", "p1@p1.dk");
        p1.setId(3);
        PersonDto p1DTO = new PersonDto(p1);

        String requestBody = GSON.toJson(p1DTO);
        PersonDto personDTO;

        personDTO = given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .post("/persons")
                .then()
                .extract().body().jsonPath().getObject("", PersonDto.class);

        assertThat(personDTO, equalTo(p1DTO));

    }
    @Test
    public void editPerson() {
        Person p1 = super.testPerson;
        p1.setPersonEmail("NyTestEmail@NyTestEmail.com");
        p1.setPersonFirstname("NyTestFirstName");
        p1.setPersonLastname("NyTestLastName");
        Hobby tennis = super.tennis;
        Phone fastnet = super.phone2;
        p1.addHobby(tennis);
        p1.addPhones(fastnet);
        PersonDto p1Dto = new PersonDto(p1);
        String requestBody = GSON.toJson(p1Dto);
        PersonDto personDto;

        personDto = given()
                .header("Content-type", ContentType.JSON)
                .and()
                .body(requestBody)
                .when()
                .put("/persons/edit")
                .then()
                .extract().body().jsonPath().getObject("", PersonDto.class);

        assertThat(personDto, equalTo(p1Dto));

    }



}
