
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.EquipmentDTO;
import facades.CharacterFacade;
import facades.dnd5eapiFacade;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import utils.EMF_Creator;
import utils.JsonReader;

@Path("dnd5eAPIFetch")
public class dnd5eapiRessource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final dnd5eapiFacade facade = dnd5eapiFacade.getdnd5api(EMF);
    private static final JsonReader jsonReader = new JsonReader();
    
    @Path("equipment/{equipmentname}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getASByCharacter(@PathParam("equipmentname") String equipmentname) throws IOException {
        EquipmentDTO edto = facade.getEquipmentDTOFromEksternAPI(equipmentname, jsonReader);
        return GSON.toJson(edto);
    }
    
}
