
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import entities.AbillityScores;
import facades.CharacterFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
     private static final CharacterFacade facade = new CharacterFacade();

     
    @Path("{characterid}/abillityscores")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getASByCharacter(@PathParam("characterid") int characterID) {
        AbillityScoresDTO asdto = facade.getASByCharacter(characterID);
        return GSON.toJson(asdto);
    }
    
    @Path("{characterid}/abillityscores")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateASSetByCharacter(@PathParam("characterid") int characterID, String abillitySet) {
        AbillityScoresDTO aSDTO = new AbillityScoresDTO(GSON.fromJson(abillitySet, AbillityScores.class));
        CharacterDTO cdtoUpdated = facade.updateAbillityScores(aSDTO,characterID);
        return GSON.toJson(cdtoUpdated);
    }
    
}
