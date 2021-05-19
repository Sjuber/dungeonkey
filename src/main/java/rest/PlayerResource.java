package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.PlayerDTO;
import entities.AbillityScores;
import entities.Character;
import errorhandling.ExceptionDTO;
import facades.CharacterFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String greetings() {
//        return "Hello \n Welcome to DungeonKey API \n - for players :D ";
//    }
    @POST
    @Path("createplayer/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createPlayer(@PathParam("username")String username, String password) {
        //PlayerDTO pDTO = GSON.fromJson(json, PlayerDTO.class);
        //String newpassword = GSON.fromJson(password, String.class);
        PlayerDTO pDTOPersisted;
        try {
            pDTOPersisted = facade.createPlayer(username, password);
        } catch (Exception ex) {
            ExceptionDTO edto = new ExceptionDTO(404, ex.getMessage());
            return edto.toString();
        }
        return GSON.toJson(pDTOPersisted);
    }

   @PUT
    @Path("updatepassword/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updatePassword(@PathParam("username")String playerId, String newPassword) {
        //PlayerDTO pDTO = GSON.fromJson(json, PlayerDTO.class);
        PlayerDTO pDTOToBeUpdated;
        try {
            pDTOToBeUpdated = facade.updatePlayer(newPassword, playerId);
        } catch (Exception ex) {
            ExceptionDTO edto = new ExceptionDTO(400, ex.getMessage());
            return edto.toString();
        }
        return GSON.toJson(pDTOToBeUpdated);
    }

     @GET
    @Path("{playerid}")//x - men passwords er stadigvæk vist
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayer(@PathParam("playerid") String playerName) {
        PlayerDTO playerDTO;
        try {
            playerDTO = facade.getPlayerByName(playerName);
        } catch (Exception ex) {
            ExceptionDTO edto = new ExceptionDTO(400, ex.getMessage());
            return edto.toString();
        }
        return GSON.toJson(playerDTO);
    }

    @GET//x - men passwords er stadigvæk vist
    @Produces(MediaType.APPLICATION_JSON)
    public String getPlayers() {
        List<PlayerDTO> playerDTO;
        try {
            playerDTO = facade.getPlayers();
        } catch (Exception ex) {
            ExceptionDTO edto = new ExceptionDTO(400, ex.getMessage());
            return edto.toString();
        }
        return GSON.toJson(playerDTO);
    }

}
