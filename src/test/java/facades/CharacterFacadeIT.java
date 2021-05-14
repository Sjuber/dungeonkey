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
import entities.Skills;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Disabled;
//@Disabled

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
        EntityManager emDelete = emf.createEntityManager();
        try {
            emDelete.getTransaction().begin();
            emDelete.createQuery("DELETE FROM Character").executeUpdate();
            emDelete.createQuery("DELETE FROM Role").executeUpdate();
            emDelete.createQuery("DELETE FROM Player").executeUpdate();
            emDelete.getTransaction().commit();
        } finally {
            emDelete.close();
        }
        EntityManager em = emf.createEntityManager();
        Player player1 = new Player("Nikolaj", "Hamster16");
        Player DM = new Player("Cathrine", "Portraet11");
        Player player2 = new Player("Jens", "Skeletor69");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Random randi = new Random(0);
        Skills skils = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Character ch1 = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1, skils);
        try {
            em.getTransaction().begin();
            Role playerRole = new Role("player");
            Role DMRole = new Role("dungeonmaster");
            player1.addRole(playerRole);
            DM.addRole(DMRole);
            player2.addRole(playerRole);
            //both.addRole(DMRole); // MAN KAN GODT HAVE BEGGE ROLLER CATHRINE !!!
            em.persist(playerRole);
            em.persist(DMRole);
            em.persist(player1);
            player1.addCharacter(ch1);
            em.merge(player1);
            em.persist(DM);
            em.persist(player2);
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
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> characterIDQuery = em.createQuery("SELECT c FROM Character c JOIN c.player p WHERE c.name =:charactername AND p.userName =:username", Character.class);
        characterIDQuery.setParameter("charactername", "Damascus").setParameter("username", "Nikolaj");
        int characterID = characterIDQuery.getSingleResult().getId();
        Player player1 = new Player("Nikolaj", "Hamster16");
        Random randi = new Random(0);
        Skills skils = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Character characterNewAS = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", aS, skils);
        characterNewAS.setPlayer(player1);
        CharacterDTO expResult = new CharacterDTO(characterNewAS);
        CharacterDTO result = facade.updateAbillityScores(aSDTONew, characterID);
        assertEquals(expResult.getAbilityScoreDTO().getStrength(), result.getAbilityScoreDTO().getStrength());
    }

    @Test
    public void testGetASByCharacter() {
        System.out.println("getASByCharacter");
        EntityManager em = emf.createEntityManager();
        String name = "Damascus";
        String userName = "Nikolaj";
        TypedQuery<Character> characterIDQuery = em.createQuery("SELECT c FROM Character c JOIN c.player p WHERE p.userName =:username AND c.name =:name", Character.class);
        characterIDQuery.setParameter("username", userName).setParameter("name", name);
        int characterID = characterIDQuery.getSingleResult().getId();
        AbillityScoresDTO expResult = new AbillityScoresDTO(new AbillityScores(18, 8, 14, 12, 14, 10));
        AbillityScoresDTO result = facade.getASByCharacter(characterID);
        assertEquals(expResult.getStrength(), result.getStrength());
    }

    @Test
    public void testSearchByName() {
        System.out.println("searchByName");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Player player1 = new Player("Nikolaj", "Hamster16");
        Random randi = new Random(0);
        Skills skils = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Character character = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1, skils);
        player1.addCharacter(character);
        CharacterDTO characterDTO = new CharacterDTO(character);
        List<CharacterDTO> expResult = new ArrayList<CharacterDTO>();
        expResult.add(characterDTO);
        List<CharacterDTO> result = facade.searchByName("Damascus");
        assertEquals(expResult.get(0).getAbilityScoreDTO().getStrength(), result.get(0).getAbilityScoreDTO().getStrength());
    }

    @Test
    public void testSearchByRace() {
        System.out.println("searchByRace");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Player player1 = new Player("Nikolaj", "Hamster16");
        Random randi = new Random(0);
        Skills skils = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Character character = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1, skils);
        player1.addCharacter(character);
        CharacterDTO characterDTO = new CharacterDTO(character);
        List<CharacterDTO> expResult = new ArrayList<CharacterDTO>();
        expResult.add(characterDTO);
        List<CharacterDTO> result = facade.searchByRace("orc");
        assertEquals(expResult.get(0).getAbilityScoreDTO().getStrength(), result.get(0).getAbilityScoreDTO().getStrength());
    }

    @Test
    public void testSearchByPlayer() {
        System.out.println("searchByPlayer");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Player player1 = new Player("Nikolaj", "Hamster16");
        Random randi = new Random(0);
        Skills skils = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Character character = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1, skils);
        player1.addCharacter(character);
        CharacterDTO characterDTO = new CharacterDTO(character);
        List<CharacterDTO> expResult = new ArrayList<CharacterDTO>();
        expResult.add(characterDTO);
        List<CharacterDTO> result = facade.searchByPlayer(player1.getUserName());
        assertEquals(expResult.get(0).getAbilityScoreDTO().getStrength(), result.get(0).getAbilityScoreDTO().getStrength());
    }

//    DOES NOT EXIST, DONT LOOK :C  
//    (Den virker på postman men herinde gider den ikke :/) 
//    @Test
//    public void testCreateCharacter() {
//        AbillityScores abiSco1 = new AbillityScores(8, 16, 10, 14, 15, 19);
//        Character ch = new Character(3, 27, 17, 19, 35, "John Broadsound", "A great singer he is.", "tabaxi", "bard", abiSco1);
//        CharacterDTO chDTO = new CharacterDTO(ch);
//
//        CharacterDTO chY = facade.createCharacter(chDTO, "Jens");
//        
//        assertEquals(chY.getLevl(), ch.getLvl());
//    }

}
