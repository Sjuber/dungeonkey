
package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.EquipmentCatagoryDTO;
import dtos.EquipmentDTO;
import entities.Equipment;
import entities.EquipmentCategory;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import org.json.JSONObject;
import utils.JsonReader;

public class dnd5eapiFacade {
    
    public static dnd5eapiFacade instance;
    public static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static dnd5eapiFacade getdnd5api(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new dnd5eapiFacade();
        }
        return instance;
    }
    
    public EquipmentDTO getEquipmentDTOFromEksternAPI(String equipmentName, JsonReader jsonReader) throws IOException{
        JSONObject jSONObject = jsonReader.readJsonFromUrl("https://www.dnd5eapi.co/api/equipment/"+equipmentName);
        String jsonObjectCat = jSONObject.get("equipment_category").toString();
        EquipmentCatagoryDTO equipmentCatagoryDTO = new EquipmentCatagoryDTO(GSON.fromJson(jsonObjectCat, EquipmentCategory.class));
        Object weight = jSONObject.get("weight");
        return new EquipmentDTO(new Equipment(""+jSONObject.get("index"), Double.parseDouble(weight.toString()) ,equipmentCatagoryDTO.getName()));
    }

}
