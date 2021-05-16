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
import dtos.PlayerDTO;
import entities.AbillityScores;
import entities.Character;
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

    @POST
    @Path("createcharacter/{playerid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createCharacter(@PathParam("playerid") String playerid, String json) {
        CharacterDTO chaDTO = GSON.fromJson(json, CharacterDTO.class);
        CharacterDTO chaDTOPersisted = facade.createCharacter(chaDTO, playerid);
        return GSON.toJson(chaDTOPersisted);
    }

    @PUT
    @Path("updatecharacter/{characterID}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateCharacter(@PathParam("characterID") int characterID, String json) throws Exception {
        CharacterDTO chaDTO = GSON.fromJson(json, CharacterDTO.class);
        CharacterDTO chaDTOUpdated = facade.updateCharacter(chaDTO, characterID);
        return GSON.toJson(chaDTOUpdated);
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
    @Path("updatehp/{characterid}/{newhpvalue}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateHP(@PathParam("characterid") String characterID, @PathParam("newhpvalue") String newHPValue){
        String hPValue = facade.updateHP(Integer.valueOf(newHPValue), Integer.valueOf(characterID));
        return GSON.toJson(hPValue);
    }
    
        @Path("updatebio/{characterid}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateBiography(@PathParam("characterid") int charc, String bio) throws Exception{
        String bios = facade.updateBiography(bio, charc);
        return GSON.toJson(bios);
    }

    @Path("searchbyrace/{race}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByRace(@PathParam("race") String race) {
        List<CharacterDTO> chDTO;
        try {
            chDTO = facade.searchByRace(race);
        } catch (Exception ex) {
            ExceptionDTO exDto = new ExceptionDTO(404, ex.getMessage());
            return exDto.toString();
        }
        return GSON.toJson(chDTO);
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
            return exceptionDTO.toString();
        }
    }

    @Path("inventory/{equipmentname}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEquipment(@PathParam("equipmentname") String equipmentName) {
        EquipmentDTO equipmentDTO = null;
        ExceptionDTO exceptionDTO;
        try {
            equipmentDTO = facade.getEquipment(equipmentName);
        } catch (Exception e) {
            exceptionDTO = new ExceptionDTO(404, e.getMessage());
            return exceptionDTO.toString();
        }
        return GSON.toJson(equipmentDTO);
    }

    @Path("searchbyname/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByName(@PathParam("name") String name) throws Exception {
        List<CharacterDTO> chDTO;
        try {
            chDTO = facade.searchByName(name);
        } catch (Exception ex) {
            ExceptionDTO exDto = new ExceptionDTO(404, ex.getMessage());
            return exDto.toString();
        }
        return GSON.toJson(chDTO);
    }

    @Path("searchbyplayer/{player}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchByPlayer(@PathParam("player") String player) throws Exception {
        List<CharacterDTO> chDTO;
        try {
            chDTO = facade.searchByPlayer(player);
        } catch (Exception ex) {
            ExceptionDTO exDto = new ExceptionDTO(404, ex.getMessage());
            return exDto.toString();
        }
        return GSON.toJson(chDTO);
    }
    
}
