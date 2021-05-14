package facades;

import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.EquipmentDTO;
import entities.AbillityScores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Character;
import entities.Equipment;
import entities.Inventory;
import java.util.List;
import javax.persistence.TypedQuery;

public class CharacterFacade {

    public static CharacterFacade instance;
    public static EntityManagerFactory emf;

    public static CharacterFacade getCharacterFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CharacterFacade();
        }
        return instance;
    }

    public CharacterDTO updateAbillityScores(AbillityScoresDTO aSDTONew, int characterID) {
        EntityManager em = emf.createEntityManager();
        Character dbCharacter;
        try {
            em.getTransaction().begin();
            dbCharacter = em.find(Character.class, characterID);
            AbillityScores aSFromDB = em.find(AbillityScores.class, dbCharacter.getAbillityScores().getId());
            aSFromDB.setCharisma(aSDTONew.getCharisma());
            aSFromDB.setStrength(aSDTONew.getStrength());
            aSFromDB.setDexterity(aSDTONew.getDexterity());
            aSFromDB.setConstitution(aSDTONew.getConstitution());
            aSFromDB.setIntelligence(aSDTONew.getIntelligence());
            aSFromDB.setWisdom(aSDTONew.getWisdom());
            aSFromDB.setCharacter(dbCharacter);
            em.merge(dbCharacter);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CharacterDTO(dbCharacter);
    }

    public AbillityScoresDTO getASByCharacter(int characterID) {
        EntityManager em = emf.createEntityManager();
        Character character;
        try {
            em.getTransaction().begin();
            character = em.find(Character.class, characterID);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new AbillityScoresDTO(character.getAbillityScores());
    }

    public CharacterDTO updateCharactersInventory(int characterID, EquipmentDTO edto, int qty) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityManager emPersist = emf.createEntityManager();
        Character character = null;
        Equipment equipment = null;
        Equipment equipmentNew;
        Inventory inventory;
        List<Inventory> inventories;
        int qtyTotal;
        if (edto == null) {
            throw new Exception("The given equipment title must not be empty");
        } else {
            try {
                em.getTransaction().begin();
                equipment = em.find(Equipment.class, edto.getName());
                if (equipment == null) {
                    character = em.find(Character.class, characterID);
                    equipmentNew = new Equipment(edto.getName(), edto.getWeight());

                    try {
                        emPersist.getTransaction().begin();
                       // emPersist.persist(equipmentNew);
                       inventory = new Inventory(equipmentNew,qty);
                        character.addInventory(inventory);
                        emPersist.merge(inventory);
                        emPersist.merge(character);
                        emPersist.getTransaction().commit();
                    } finally {
                        emPersist.close();
                    }
                    
                } else {
                    TypedQuery<Inventory> inventoryQ = em.createQuery("SELECT i FROM Equipment e JOIN e.inventories i JOIN i.character c WHERE e.name =:equipmentname AND c.id =:characterid", Inventory.class);
                    inventoryQ.setParameter("equipmentname", edto.getName());
                    inventoryQ.setParameter("characterid", characterID);
                    //Iventory has only one match of equipment and character, therefor we can only have the first inventory from db
                    inventories = inventoryQ.getResultList();
                    inventory = inventories.get(0);
                    character = inventory.getCharacter();
                    qtyTotal = inventory.getQty() + qty;
                    if (qtyTotal <= 0) {
                        em.remove(inventory);
                        //inventory.getEquipment().getInventories().remove(inventory);
                        equipment.getInventories().remove(inventory);
                    } else {
                        inventory.setQty(qtyTotal);
                        inventory.setCharacter(character);
                        em.merge(character);
                    }
                }
                if (equipment!=null && equipment.getInventories().isEmpty()) {
                    em.remove(equipment);
                }
                character = em.find(Character.class, characterID);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new CharacterDTO(character);
    }

    public List<CharacterDTO> searchByName(String characterName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> character = em.createQuery("SELECT c FROM Character c WHERE c.name = :name", Character.class
        );
        character.setParameter("name", characterName);
        List<Character> resultlist = character.getResultList();
        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);

        return resultAsDTO;
    }

    public List<CharacterDTO> searchByRace(String characterRace) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> character = em.createQuery("SELECT c FROM Character c WHERE c.race =:race", Character.class
        );
        character.setParameter("race", characterRace);
        List<Character> resultlist = character.getResultList();
        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);

        return resultAsDTO;
    }

    //TODO//WORK IN PROGRESS, SEARCH BY ITEM
//    public List<CharacterDTO> searchByItem(String itemName){
//        EntityManager em = emf.createEntityManager();
//        TypedQuery<Character> query = em.createQuery("SELECT c FROM Character c JOIN c.items item WHERE item.name =:itemname", Character.class);
//        query.setParameter("itemname", itemName);
//        List<Character> resultlist = query.getResultList();
//        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);
//        return resultAsDTO;
//    }
    public List<CharacterDTO> searchByPlayer(String playerName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> query = em.createQuery("SELECT c FROM Character c JOIN c.player p WHERE p.userName =:playername", Character.class
        );
        query.setParameter("playername", playerName);
        List<Character> resultlist = query.getResultList();
        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);
        return resultAsDTO;
    }

    public EquipmentDTO getEquipment(String equipmentName) throws Exception {
        EntityManager em = emf.createEntityManager();
        Equipment equipment = null;
        try {
            em.getTransaction().begin();
            equipment = em.find(Equipment.class,
                    equipmentName);
        } finally {
            em.close();
        }
        if (equipment == null) {
            throw new Exception("There is no such equipment with the given name");
        }
        return new EquipmentDTO(equipment);
    }

}
