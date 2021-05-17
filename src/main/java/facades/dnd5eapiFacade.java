package facades;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.EquipmentCatagoryDTO;
import dtos.EquipmentDTO;
import entities.Equipment;
import entities.EquipmentCategory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public EquipmentDTO getEquipmentDTOFromAPI(String equipmentName, JsonReader jsonReader) throws IOException, Exception {
        if (equipmentName == null) {
            throw new Exception("You must send a given equipment name for fetching");
        } else {
        JSONObject jSONObject = jsonReader.readJsonFromUrl("https://www.dnd5eapi.co/api/equipment/" + equipmentName);
        String jsonObjectCat = jSONObject.get("equipment_category").toString();
        EquipmentCatagoryDTO equipmentCatagoryDTO = new EquipmentCatagoryDTO(GSON.fromJson(jsonObjectCat, EquipmentCategory.class));
        Object weight;
        try {
             weight = jSONObject.get("weight");
        } catch (Exception e) {
            return new EquipmentDTO(new Equipment("" + jSONObject.get("index"), 0.0, equipmentCatagoryDTO.getName()));
        }
        return new EquipmentDTO(new Equipment("" + jSONObject.get("index"), Double.parseDouble(weight.toString()), equipmentCatagoryDTO.getName()));
        }
    }

    public List<EquipmentDTO> getEquipmentDTOsFromAPI(JsonReader jsonReader) throws IOException {
        List<EquipmentDTO> eDTOs = new ArrayList<>();
        JSONObject jSONObject = jsonReader.readJsonFromUrl("https://www.dnd5eapi.co/api/equipment/");
        ObjectMapper objectMapper = new ObjectMapper();
        EquipmentCategory[] ec = objectMapper.readValue(jSONObject.get("results").toString(), EquipmentCategory[].class);
        List<EquipmentCatagoryDTO> ecs = new ArrayList<>();
        
        for (int i = 0; i < ec.length; i++) {
            ecs.add(new EquipmentCatagoryDTO(ec[i]));
        }
        for (EquipmentCatagoryDTO ec1 : ecs) {
            try {
                eDTOs.add(getEquipmentDTOFromAPI(ec1.getIndex(), jsonReader));
            } catch (Exception e) {
                //For test
                System.out.println("Den her har fejl: "+ ec1.getName() + "\n");
                continue;
            }
        }
        return eDTOs;
    }

}
