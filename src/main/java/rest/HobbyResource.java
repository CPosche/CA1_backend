package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDto;
import facades.HobbyFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("hobby")
public class HobbyResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final HobbyFacade FACADE =  HobbyFacade.getHobbyFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllHobby() throws EntityNotFoundException  {
        List<HobbyDto> hobbyList = FACADE.getAllHobby();
        return Response.ok().header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD").entity(GSON.toJson(hobbyList)).build();
    }

}


