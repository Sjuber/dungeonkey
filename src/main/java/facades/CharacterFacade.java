package facades;

import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.PlayerDTO;
import dtos.SkillsDTO;
import entities.AbillityScores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Character;
import entities.Player;
import entities.Skills;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

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

    public PlayerDTO createPlayer(String usnm, String pswd) {
        EntityManager em = emf.createEntityManager();
        Player pl = new Player(usnm, pswd);
        em.getTransaction().begin();
        em.persist(pl);
        em.getTransaction().commit();
        PlayerDTO plDTO = new PlayerDTO(pl);
        return plDTO;
    }

    public PlayerDTO updatePlayer(PlayerDTO pdtoNeo, String pId) throws Exception {
        EntityManager em = emf.createEntityManager();
        Player dbPlayer;
        try {
            em.getTransaction().begin();
            dbPlayer = em.find(Player.class, pId);
            if (dbPlayer == null) {
                throw new Exception("The player is not in the database");
            }
            dbPlayer.setPassword(pdtoNeo.getPassword());

            em.merge(dbPlayer);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PlayerDTO(dbPlayer);
    }

    public CharacterDTO createCharacter(CharacterDTO chDTO, String playerId) {
        EntityManager em = emf.createEntityManager();
        AbillityScoresDTO abisc = chDTO.getAbilityScoreDTO();
        AbillityScores abiscR = new AbillityScores(abisc.getStrength(), abisc.getDexterity(), abisc.getConstitution(), abisc.getWisdom(), abisc.getIntelligence(), abisc.getCharisma());
        SkillsDTO sksDTO = chDTO.getSkillsDTO();
        Skills skilR = new Skills(sksDTO.getAnimal_Handling(), sksDTO.getArcana(), sksDTO.getAthletics(), sksDTO.getDeception(), sksDTO.getHistory(), sksDTO.getInsight(), sksDTO.getIntimidation(), sksDTO.getInvestigation(), sksDTO.getMedicine(), sksDTO.getNature(), sksDTO.getPerception(), sksDTO.getPerformance(), sksDTO.getPersuasion(), sksDTO.getReligion(), sksDTO.getSleight_of_Hand(), sksDTO.getStealth(), sksDTO.getSurvival());

        Player dbPlayer = em.find(Player.class, playerId);

        Character ch = new Character(chDTO.getLevl(), chDTO.getMaxHp(), chDTO.getCurrentHP(), chDTO.getAc(), chDTO.getSpeed(), chDTO.getName(), chDTO.getBiography(), chDTO.getRace(), chDTO.getClasss(), abiscR, skilR);
        dbPlayer.addCharacter(ch);
        em.getTransaction().begin();
        em.persist(ch);
        em.getTransaction().commit();
        return new CharacterDTO(ch);
    }

    public CharacterDTO updateCharacter(CharacterDTO chaDTONeo, int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character asFromDB;
        try {
            em.getTransaction().begin();
            asFromDB = em.find(Character.class, characterID);
            if (asFromDB == null) {
                throw new Exception("The character is not in the database");
            }
            int str = chaDTONeo.getAbilityScoreDTO().getStrength();
            int dex = chaDTONeo.getAbilityScoreDTO().getDexterity();
            int con = chaDTONeo.getAbilityScoreDTO().getConstitution();
            int wis = chaDTONeo.getAbilityScoreDTO().getWisdom();
            int inte = chaDTONeo.getAbilityScoreDTO().getIntelligence();
            int cha = chaDTONeo.getAbilityScoreDTO().getCharisma();
            AbillityScores abS = new AbillityScores(str, dex, con, wis, inte, cha);
            asFromDB.setAbillityScores(abS);
            asFromDB.setLvl(chaDTONeo.getLevl()); // change
            asFromDB.setMaxHP(chaDTONeo.getMaxHp()); // change
            asFromDB.setCurrentHP(chaDTONeo.getCurrentHP()); // change
            asFromDB.setAc(chaDTONeo.getAc()); // change
            asFromDB.setSpeed(chaDTONeo.getSpeed()); // change
            asFromDB.setName(chaDTONeo.getName());
            asFromDB.setBiography(chaDTONeo.getBiography());
            asFromDB.setRace(chaDTONeo.getRace());
            asFromDB.setClasss(chaDTONeo.getClasss());
            em.merge(asFromDB);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CharacterDTO(asFromDB);
    }

    public String updateBiography(String bio, int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character asFromDB;
        try {
            em.getTransaction().begin();
            asFromDB = em.find(Character.class, characterID);
            if (asFromDB == null) {
                throw new Exception("The character is not in the database");
            }
            asFromDB.setBiography(bio);
            em.merge(asFromDB);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return bio;
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

    public String updateHP(int newHPValue, int CharacterId) {
        EntityManager em = emf.createEntityManager();
        Character characterWithNewHp;
        try {
            em.getTransaction().begin();
            Character character = em.find(Character.class, CharacterId);
            character.setCurrentHP(newHPValue);
            em.merge(character);
            em.getTransaction().commit();
            characterWithNewHp = em.find(Character.class, CharacterId);

        } finally {
            em.close();
        }
        return characterWithNewHp.getCurrentHP() + "";

    }
    
        public Player getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        Player player;
        try {
            player = em.find(Player.class, username);
            if (player == null || !player.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return player;
    }

}
