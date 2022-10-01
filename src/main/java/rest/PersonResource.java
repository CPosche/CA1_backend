package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDto;
import dtos.RenameMeDTO;
import facades.PersonFacade;
import utils.EMF_Creator;
import facades.FacadeExample;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("persons")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String demo() {
//        return "{\"msg\":\"Hello World\"}";
//    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String jsonInput) {
        PersonDto personDto = GSON.fromJson(jsonInput, PersonDto.class);
        PersonDto newPerson = FACADE.createPerson(personDto);
        return Response.ok().entity(GSON.toJson(newPerson)).build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPerson(@QueryParam("zipcode") int zipcode, @QueryParam("city") String city){
        List<PersonDto> personList = FACADE.getPersonsByZip2(zipcode, city);
        return Response.ok().entity(GSON.toJson(personList)).build();
    }

}
