
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
        AbillityScores aSNew = new AbillityScores(aSDTONew.getStrength(), aSDTONew.getDexterity(),aSDTONew.getConstitution(),aSDTONew.getWisdom(),aSDTONew.getIntelligence(),aSDTONew.getCharisma());
        try {
            em.getTransaction().begin();
            dbCharacter = em.find(Character.class, characterID);
            dbCharacter.setAbillityScores(aSNew);
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
    
    public static void main(String[] args) {
      
        updateAbillityScores(new AbillityScoresDTO(new AbillityScores(2,3,4,5,56,2)),1);
    }
}
