package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
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

@Path("characters")
public class CharacterResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final CharacterFacade facade = CharacterFacade.getCharacterFacade(EMF);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String greetings() {
        return "Hello \n Welcome to DungeonKey API \n - for Characters :D ";
    }

    @Path("{characterid}/abillityscores")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getASByCharacter(@PathParam("characterid") String characterID) {
        AbillityScoresDTO asdto = facade.getASByCharacter(Integer.valueOf(characterID));
        return GSON.toJson(asdto);
    }

    @Path("createcharacter")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createCharacter(String json) {

        CharacterDTO persistedCharacter = facade.createCharacter(GSON.fromJson(json, CharacterDTO.class));
        return GSON.toJson(persistedCharacter);

    }

    @Path("{characterid}/abillityscores")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateASSetByCharacter(@PathParam("characterid") String characterID, String abillitySet) {
        AbillityScoresDTO aSDTO = new AbillityScoresDTO(GSON.fromJson(abillitySet, AbillityScores.class));
        CharacterDTO cdtoUpdated = facade.updateAbillityScores(aSDTO, Integer.valueOf(characterID));
        return GSON.toJson(cdtoUpdated);
    }

    @Path("searchbyrace/{race}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByRace(@PathParam("race") String race) {
        List<CharacterDTO> chDTO = facade.searchByRace(race);
        return GSON.toJson(chDTO);
    }

    @Path("searchbyname/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByName(@PathParam("name") String name) {
        List<CharacterDTO> chDTO = facade.searchByName(name);
        return GSON.toJson(chDTO);
    }

    @Path("searchbyplayer/{player}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByPlayer(@PathParam("player") String player) {
        List<CharacterDTO> chDTO = facade.searchByPlayer(player);
        return GSON.toJson(chDTO);
    }

}
