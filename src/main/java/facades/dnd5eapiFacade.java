package facades;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.EquipmentCatagoryDTO;
import dtos.EquipmentDTO;
import entities.Equipment;
import entities.EquipmentCategory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
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

    public EquipmentDTO getEquipment(String equipmentName) throws Exception {
        EntityManager em = emf.createEntityManager();
        if (equipmentName == null) {
            throw new Exception("You must send a given equipment name for fetching");
        } else {
            Equipment equipment = em.find(Equipment.class, equipmentName);
            if (equipment == null) {
                throw new Exception("There is no such equipment with the given title");
            }
            return new EquipmentDTO(equipment);
        }
    }

    public EquipmentDTO getEquipmentDTOFromAPI(String equipmentName, JsonReader jsonReader) throws IOException, Exception {
        if (equipmentName == null) {
            throw new Exception("You must send a given equipment name for fetching");
        } else {
            try {
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

            } catch (FileNotFoundException e) {
                throw new Exception("There is no such equipment");
            }
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
                System.out.println("Den her har fejl: " + ec1.getName() + "\n");
                continue;
            }
        }
        return eDTOs;
    }

    public void fillingUpDBWithEquipments(List<EquipmentDTO> equipmentDTOs) throws Exception {
        EntityManager em = emf.createEntityManager();
        Equipment equipment;
        try {
            em.getTransaction().begin();
            TypedQuery<Equipment> eQuery = em.createQuery("SELECT e From Equipment e", Equipment.class);
            List<Equipment> equipments = eQuery.getResultList();
            if (equipments.isEmpty()) {
                for (EquipmentDTO e : equipmentDTOs) {
                    equipment = new Equipment(e.getName(), e.getWeight(), e.getCatergory());
                    em.persist(equipment);
                }
                em.getTransaction().commit();
            }
            else{
                throw new Exception("The database already contains equipments");
            }
        } finally {
            em.close();
        }
    }
    
    public List<EquipmentDTO> getAllEquipments() throws Exception{
        List<EquipmentDTO> edtos = new ArrayList<>();
       EntityManager em = emf.createEntityManager();
       TypedQuery<Equipment> eQuery = em.createQuery("SELECT e From Equipment e", Equipment.class);
            List<Equipment> equipments = eQuery.getResultList();
            if (equipments.isEmpty()) {
                throw new Exception("There is no equipments in the database");
            }else{
            equipments.forEach(e -> edtos.add(new EquipmentDTO(e)));
                    }return edtos;
    }

}
