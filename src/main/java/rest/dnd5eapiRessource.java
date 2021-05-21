package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AbillityScoresDTO;
import dtos.EquipmentDTO;
import errorhandling.ExceptionDTO;
import facades.CharacterFacade;
import facades.dnd5eapiFacade;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import org.json.JSONObject;
import utils.EMF_Creator;
import utils.JsonReader;

@Path("dnd5eapifetch")
public class dnd5eapiRessource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final dnd5eapiFacade facade = dnd5eapiFacade.getdnd5api(EMF);
    private static final JsonReader jsonReader = new JsonReader();

    @Path("equipments/{equipmentname}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEquipment(@PathParam("equipmentname") String equipmentname) {
        EquipmentDTO edto;
        try {
            edto = facade.getEquipment(equipmentname);
        } catch (Exception ex) {
            ExceptionDTO edto1 = new ExceptionDTO(404, ex.getMessage());
            return ex.toString();
        }
        return GSON.toJson(edto);
    }

    //PERSIST ALL EQUIPMENTS
    @Path("equipments/persist")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getEquipmentsForDB() {
        try {
            facade.fillingUpDBWithEquipments(facade.getEquipmentDTOsFromAPI(jsonReader));
        } catch (IOException ex) {
            ExceptionDTO edto = new ExceptionDTO(400, ex.getMessage());
            return edto.toString();
        } catch(Exception e){
            ExceptionDTO edto = new ExceptionDTO(507, e.getMessage());
        }
        return "Succes - The database is now full with equipments";
    }
    
    @Path("equipments")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getEquipments() {
        List<EquipmentDTO> edtos;
        try {
           edtos = facade.getAllEquipments();
        } catch (IOException ex) {
            ExceptionDTO exto = new ExceptionDTO(400, ex.getMessage());
            return exto.toString();
        } catch(Exception e){
            ExceptionDTO exto = new ExceptionDTO(507, e.getMessage());
            return exto.toString();
        }
        return GSON.toJson(edtos);
    }

}
