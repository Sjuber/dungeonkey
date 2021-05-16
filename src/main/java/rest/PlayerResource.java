package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.PlayerDTO;
import entities.AbillityScores;
import entities.Character;
import facades.CharacterFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

@Path("players")
public class PlayerResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final CharacterFacade facade = CharacterFacade.getCharacterFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String greetings() {
        return "Hello \n Welcome to DungeonKey API \n - for players :D ";
    }

    @POST
    @Path("createplayer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPlayer(String json) {
        PlayerDTO pDTO = GSON.fromJson(json, PlayerDTO.class);
        PlayerDTO pDTOPersisted = facade.createPlayer(pDTO.getUsername(), pDTO.getPassword());
        return GSON.toJson(pDTOPersisted);
    }

    @PUT
    @Path("updatepassword/{playerid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePassword(@PathParam("playerid") String playerId, String json) throws Exception {
        PlayerDTO pDTO = GSON.fromJson(json, PlayerDTO.class);
        PlayerDTO pDTOToBeUpdated= facade.updatePlayer(pDTO, pDTO.getUsername());
        return GSON.toJson(pDTOToBeUpdated);
    }
    
    @GET
    @Path("{playerid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayer(@PathParam("playerid") String playerName) throws Exception {
        PlayerDTO playerDTO = facade.getPlayerByName(playerName);
        return GSON.toJson(playerDTO);
    }

}
