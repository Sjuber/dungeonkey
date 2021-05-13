package facades;

import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.EquipmentDTO;
import entities.AbillityScores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Character;
import entities.Equipment;
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

    public static CharacterDTO updateAbillityScores(AbillityScoresDTO aSDTONew, int characterID) {
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

    public CharacterDTO adjustCharactersInventory(int characterID, EquipmentDTO equipmentDTO) {
        EntityManager em = emf.createEntityManager();
        Character character;
        Equipment equipment = new Equipment(equipmentDTO.getName(), equipmentDTO.getWeight());
        try {
            em.getTransaction().begin();
            character = em.find(Character.class, characterID);
            //character.getInventory().adjustEquipmentAndQty(equipment, equipment.getQty());
            em.merge(character);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CharacterDTO(character);
    }

    public List<CharacterDTO> searchByName(String characterName) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> character = em.createQuery("SELECT c FROM Character c WHERE c.name = :name", Character.class);
        character.setParameter("name", characterName);
        List<Character> resultlist = character.getResultList();
        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);

        return resultAsDTO;
    }

    public List<CharacterDTO> searchByRace(String characterRace) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> character = em.createQuery("SELECT c FROM Character c WHERE c.race =:race", Character.class);
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
        TypedQuery<Character> query = em.createQuery("SELECT c FROM Character c JOIN c.player p WHERE p.userName =:playername", Character.class);
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
        equipment = em.find(Equipment.class, equipmentName);
        }finally{
            em.close();
        }
        if(equipment==null){
            throw new Exception("There is no such equipment with the given name");
        }
        return new EquipmentDTO(equipment);
    }

}
