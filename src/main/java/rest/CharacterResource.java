package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.EquipmentDTO;
import entities.AbillityScores;
import entities.Equipment;
import errorhandling.ExceptionDTO;
import facades.CharacterFacade;
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

    @Path("{characterid}/abillityscores")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateASSetByCharacter(@PathParam("characterid") String characterID, String abillitySet) {
        AbillityScoresDTO aSDTO = new AbillityScoresDTO(GSON.fromJson(abillitySet, AbillityScores.class));
        CharacterDTO cdtoUpdated = facade.updateAbillityScores(aSDTO, Integer.valueOf(characterID));
        return GSON.toJson(cdtoUpdated);
    }

    @Path("{characterid}/inventory/{extraqtyforequipment}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addEquipmentForCharactersInventory(@PathParam("characterid") int characterID, @PathParam("extraqtyforequipment") int qty, String equipment) {
        CharacterDTO cdtoUpdated = null;
        ExceptionDTO exceptionDTO;
        try {
            EquipmentDTO equipmentDTO = GSON.fromJson(equipment, EquipmentDTO.class);
            cdtoUpdated = facade.updateCharactersInventory(characterID, equipmentDTO, qty);
            return GSON.toJson(cdtoUpdated);
        } catch (Exception ex) {
            exceptionDTO = new ExceptionDTO(404, ex.getMessage());
        }
        return exceptionDTO.getMessage();
    }

    @Path("inventory/{equipmentname}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEquipment(@PathParam("equipmentname") String equipmentName) throws Exception {
        EquipmentDTO equipmentDTO = facade.getEquipment(equipmentName);
        return GSON.toJson(equipmentDTO);
    }

}
