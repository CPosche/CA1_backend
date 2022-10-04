package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityinfoDto;
import dtos.PersonDto;
import entities.Cityinfo;
import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;

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


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPerson(String jsonInput) {
        PersonDto personDto = GSON.fromJson(jsonInput, PersonDto.class);
        PersonDto newPerson = FACADE.createPerson(personDto);
        return Response.ok().entity(GSON.toJson(newPerson)).build();
    }

    @GET
    @Path("city")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByZip(@QueryParam("zipcode") int zipcode, @QueryParam("cityname") String cityname){
        Cityinfo cityinfo = new Cityinfo(zipcode, cityname);
        CityinfoDto cityinfoDto = new CityinfoDto(cityinfo);
        List<PersonDto> personList = FACADE.getPersonsByZip(cityinfoDto);
        return Response.ok().entity(GSON.toJson(personList)).build();
    }
    @GET
    @Path("count/hobby")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCountByHobbyName(@QueryParam("hobby") String hobby){
        int hobbyCount = FACADE.getCountByHobby(hobby);
        return Response.ok().entity(GSON.toJson(hobbyCount)).build();
    }

    @GET
    @Path("count/zip")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCountByZip(@QueryParam("zip") int zip){
        int zipcount = FACADE.getCountByZip(zip);
        return Response.ok().entity(GSON.toJson(zipcount)).build();
    }

    @GET
    @Path("phone")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@QueryParam("phone") int phone){
        return Response.ok().entity(GSON.toJson(FACADE.getPersonByPhoneNumber(phone))).build();
    }

    @PUT
    @Path("edit")
    @Produces({MediaType.APPLICATION_JSON}) 
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPerson(String content){
        Person newPerson = GSON.fromJson(content, Person.class);
        PersonDto newPersonDto = new PersonDto(newPerson);
        return Response.ok().entity(GSON.toJson(FACADE.editPerson(newPersonDto))).build();
    }
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons () {
            return Response.ok().entity(GSON.toJson(FACADE.getAllPersons())).build();

    }
    @GET
    @Path("hobby")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonsByHobby(@QueryParam("hobby") String hobby){
        return Response.ok().entity(GSON.toJson(FACADE.getPersonsByHobby(hobby))).build();
    }
}
