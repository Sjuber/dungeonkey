
package facades;

import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import entities.AbillityScores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Character;

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
    
    public static CharacterDTO updateAbillityScores(AbillityScoresDTO aSDTONew, int characterID){
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
    
}
