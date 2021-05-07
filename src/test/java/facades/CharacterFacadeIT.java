package facades;

import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import entities.AbillityScores;
import entities.Player;
import entities.Role;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import utils.EMF_Creator;
import entities.Character;
import org.junit.jupiter.api.Disabled;

public class CharacterFacadeIT {

    private static EntityManagerFactory emf;
    private static CharacterFacade facade;

    public CharacterFacadeIT() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CharacterFacade.getCharacterFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Player player = new Player("Nikolaj", "Hamster16");
        Player DM = new Player("Cathrine", "Portraet11");
        Player both = new Player("Jens", "Skeletor69");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Character ch1 = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1);
        try {
            em.getTransaction().begin();
            Role playerRole = new Role("player");
            Role DMRole = new Role("dungeonmaster");
            player.addRole(playerRole);
            DM.addRole(DMRole);
            both.addRole(playerRole);
            both.addRole(DMRole);
            em.persist(playerRole);
            em.persist(DMRole);
            player.addCharacter(ch1);
            em.persist(player);
            em.persist(DM);
            em.persist(both);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testUpdateAbillityScores() {
        System.out.println("updateAbillityScores");
        AbillityScores aS = new AbillityScores(11, 11, 11, 11, 11, 11);
        AbillityScoresDTO aSDTONew = new AbillityScoresDTO(aS);
        int characterID = 1;
        CharacterFacade instance = new CharacterFacade();
        Character characterNewAS = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", aS);
        CharacterDTO expResult = new CharacterDTO(characterNewAS);
        CharacterDTO result = instance.updateAbillityScores(aSDTONew, characterID);
        assertEquals(expResult.getAbilityScoreDTO().getStrength(), result.getAbilityScoreDTO().getStrength());
    }

    
    @Test
    public void testGetASByCharacter() {
        System.out.println("getASByCharacter");
        int characterID = 1;
        CharacterFacade instance = new CharacterFacade();
        AbillityScoresDTO expResult = new AbillityScoresDTO(new AbillityScores(18, 8, 14, 12, 14, 10));
        AbillityScoresDTO result = instance.getASByCharacter(characterID);
        assertEquals(expResult.getStrength(), result.getStrength());
    }

}
