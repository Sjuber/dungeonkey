package facades;

import dtos.AbillityScoresDTO;
import dtos.CharacterDTO;
import dtos.EquipmentDTO;
import dtos.PlayerDTO;
import dtos.SkillsDTO;
import entities.AbillityScores;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import entities.Character;
import entities.Equipment;
import entities.Inventory;
import entities.Player;
import entities.Skills;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.glassfish.jersey.server.wadl.internal.generators.resourcedoc.xhtml.Elements;
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

    //Needs negative tests
    public CharacterDTO updateAbillityScores(AbillityScoresDTO aSDTONew, int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character dbCharacter;
        if (aSDTONew == null || characterID <= 0) {
            throw new Exception("You must send a given abilityscore to change to and the characters ID must be 1 or higher");
        } else {
            try {
                em.getTransaction().begin();
                dbCharacter = em.find(Character.class, characterID);
                if (dbCharacter == null) {
                    throw new Exception("The character ID: " + characterID + " couldn't find any character in the database");
                } else {
                    AbillityScores aSFromDB = em.find(AbillityScores.class, dbCharacter.getAbillityScores().getId());
                    em.remove(aSFromDB);
                    aSFromDB.setCharisma(aSDTONew.getCharisma());
                    aSFromDB.setStrength(aSDTONew.getStrength());
                    aSFromDB.setDexterity(aSDTONew.getDexterity());
                    aSFromDB.setConstitution(aSDTONew.getConstitution());
                    aSFromDB.setIntelligence(aSDTONew.getIntelligence());
                    aSFromDB.setWisdom(aSDTONew.getWisdom());
                    aSFromDB.setCharacter(dbCharacter);
                    em.persist(aSFromDB);
                    em.merge(dbCharacter);
                    em.getTransaction().commit();
                }
            } finally {
                em.close();
            }

        }
        return new CharacterDTO(dbCharacter);
    }

    public PlayerDTO createPlayer(String usnm, String pswd) throws Exception {
        Player pl = new Player(usnm, pswd);
        PlayerDTO plDTO;
        if (usnm == null) {
            throw new Exception("You must send a given username for a player to create character");
        } else {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            em.persist(pl);
            em.getTransaction().commit();
            plDTO = new PlayerDTO(pl);
        }
        return plDTO;
    }

    public PlayerDTO updatePlayer(String newPlayerPassword, String pId) throws Exception {
        EntityManager em = emf.createEntityManager();
        Player dbPlayer;
        if (newPlayerPassword == null || pId == null) {
            throw new Exception("You must send the given username to change and the player to change to");
        } else {
            try {
                em.getTransaction().begin();
                dbPlayer = em.find(Player.class, pId);
                if (dbPlayer == null) {
                    throw new Exception("The player is not in the database");
                }
                dbPlayer.setPassword(newPlayerPassword);

                em.merge(dbPlayer);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new PlayerDTO(dbPlayer);
    }

    //Need nagative tests
    public CharacterDTO createCharacter(CharacterDTO chDTO, String playerId) throws Exception {
        if (chDTO == null) {
            throw new Exception("There must be given a character to create");
        }
        if (playerId == null) {
            throw new Exception("You must give a playername to create character");
        } else {
            EntityManager em = emf.createEntityManager();
            AbillityScoresDTO abisc = chDTO.getAbilityScoreDTO();
            AbillityScores abiscR = new AbillityScores(abisc.getStrength(), abisc.getDexterity(), abisc.getConstitution(), abisc.getWisdom(), abisc.getIntelligence(), abisc.getCharisma());
            SkillsDTO sksDTO = chDTO.getSkillsDTO();
            Skills skilR = new Skills(sksDTO.getAnimal_Handling(), sksDTO.getArcana(), sksDTO.getAthletics(), sksDTO.getDeception(), sksDTO.getHistory(), sksDTO.getInsight(), sksDTO.getIntimidation(), sksDTO.getInvestigation(), sksDTO.getMedicine(), sksDTO.getNature(), sksDTO.getPerception(), sksDTO.getPerformance(), sksDTO.getPersuasion(), sksDTO.getReligion(), sksDTO.getSleight_of_Hand(), sksDTO.getStealth(), sksDTO.getSurvival());
            Player dbPlayer = em.find(Player.class, playerId);
            Character ch = new Character(chDTO.getLevl(), chDTO.getMaxHp(), chDTO.getCurrentHP(), chDTO.getAc(), chDTO.getSpeed(), chDTO.getName(), chDTO.getBiography(), chDTO.getRace(), chDTO.getClasss(), abiscR, skilR);
            dbPlayer.addCharacter(ch);
            if (ch.getAbillityScores() == null || ch.getAc() == null || ch.getClasss() == null || ch.getMaxHP() < 0 || ch.getSkills() == null
                    || ch.getRace() == null || ch.getLvl() == null || ch.getName() == null) {
                throw new Exception("Character must contain all this elements:\n"
                        + " - Name"
                        + " - Race"
                        + " - Level"
                        + " - AC value"
                        + " - Class"
                        + " - Max value for healthpoints (Max HP)"
                        + " - Skills"
                        + " - Abilityscores values"
                );
            } else {
                em.getTransaction().begin();
                em.persist(ch);
                em.getTransaction().commit();
            }
            return new CharacterDTO(ch);
        }
    }

    //Needs negative testing
    public CharacterDTO updateCharacterByDM(CharacterDTO chaDTONeo, int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character asFromDB = null;
        if (chaDTONeo == null || characterID <= 0) {
            throw new Exception("You must send a given character to change to and the old characters ID must be 1 or higher");
        } else {
            try {
                em.getTransaction().begin();
                asFromDB = em.find(Character.class, characterID);
                if (asFromDB == null) {
                    throw new Exception("The character is not in the database");
                } else {
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
                }
            } finally {
                em.close();
            }
        }
        return new CharacterDTO(asFromDB);
    }

    //Needs Negative testing
    public String updateBiography(String bio, int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character asFromDB;
        try {
            em.getTransaction().begin();
            asFromDB = em.find(Character.class, characterID);
            if (asFromDB == null) {
                throw new Exception("The character is not in the database by that given ID");
            } else {
                asFromDB.setBiography(bio);
                em.merge(asFromDB);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return bio;
    }

    public AbillityScoresDTO getASByCharacter(int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character character = null;
        if (characterID > 0) {
            try {
                em.getTransaction().begin();
                character = em.find(Character.class, characterID);
                if (character == null) {
                    throw new Exception("There is no such character in the databse by that given ID");
                }
                em.getTransaction().commit();
            } finally {
                em.close();
            }

        } else {
            throw new Exception("The given ID for character must be 1 or higher");
        }
        return new AbillityScoresDTO(character.getAbillityScores());
    }

    public CharacterDTO updateCharactersInventory(int characterID, EquipmentDTO edto, int qty) throws Exception {
        EntityManager em = emf.createEntityManager();
        EntityManager emPersist = emf.createEntityManager();
        Character character = null;
        Character characterNew = null;
        Equipment equipment = new Equipment(edto.getName(), edto.getWeight(), edto.getCatergory());
        Equipment equipmentNew = equipment;
        Inventory inventory;
        List<Inventory> inventories;
        int qtyTotal;
        if (edto == null || characterID <= 0) {
            throw new Exception("The given equipment title must not be empty and the character ID should be 1 or higher");
        } else {
            try {
                em.getTransaction().begin();
                character = em.find(Character.class, characterID);
                equipment = em.find(Equipment.class, edto.getName());

                if (equipment == null) {
                    throw new Exception("No such equipment exist in database");
                    /*try {
                        emPersist.getTransaction().begin();
                        // emPersist.persist(equipmentNew);
                        inventory = new Inventory(equipmentNew, qty);
                        character.addInventory(inventory);
                        emPersist.merge(inventory);
                        emPersist.merge(character);
                        emPersist.getTransaction().commit();
                    } finally {
                        emPersist.close();
                    }
                } else*/
                } else if (equipment.getInventories().isEmpty()) {
                    inventory = new Inventory(equipmentNew, qty);
                    equipmentNew.addInventory(inventory);
                    character.addInventory(inventory);
                    em.merge(character);
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
                        TypedQuery<Inventory> inventoryQ1 = em.createQuery("SELECT i FROM Equipment e JOIN e.inventories i JOIN i.character c WHERE e.name =:equipmentname AND c.id =:characterid", Inventory.class);
                        inventoryQ1.setParameter("equipmentname", equipment.getName());
                        inventoryQ1.setParameter("characterid", characterID);
                        inventories = inventoryQ.getResultList();
                        inventory = inventories.get(0);
                        character.getInventories().remove(inventory);
                        equipment.getInventories().remove(inventory);
                        em.remove(inventory);
                        //em.persist(equipmentNew);
                    } else {
//                        TypedQuery<Inventory> inventoryQ1 = em.createQuery("SELECT i FROM Equipment e JOIN e.inventories i JOIN i.character c WHERE e.name =:equipmentname AND c.id =:characterid", Inventory.class);
//                        inventoryQ1.setParameter("equipmentname", equipment.getName());
//                        inventoryQ1.setParameter("characterid", characterID);
//                        inventories = inventoryQ.getResultList();
//                        inventory = inventories.get(0);
                        character.getInventories().remove(inventory);
                        inventory.setQty(qtyTotal);
                        character.addInventory(inventory);

                    }
                }
                em.merge(character);
                characterNew = em.find(Character.class, characterID);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new CharacterDTO(characterNew);
    }

    public List<CharacterDTO> searchByName(String characterName) throws Exception {

        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> character = em.createQuery("SELECT c FROM Character c WHERE c.name = :name", Character.class
        );
        character.setParameter("name", characterName);

        List<Character> resultlist = character.getResultList();

        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);
        if (resultlist.isEmpty()) {
            throw new Exception("No characters with that name were found");
        }

        return resultAsDTO;
    }

    public List<CharacterDTO> searchByRace(String characterRace) throws Exception {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Character> character = em.createQuery("SELECT c FROM Character c WHERE c.race =:race", Character.class
        );
        character.setParameter("race", characterRace);
        List<Character> resultlist = character.getResultList();
        List<CharacterDTO> resultAsDTO = CharacterDTO.getDtos(resultlist);
        if (resultlist.isEmpty()) {
            throw new Exception("No characters with that race were found");
        }

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
    public List<CharacterDTO> searchByPlayer(String playerName) throws Exception {
        EntityManager em = emf.createEntityManager();
        List<CharacterDTO> resultAsDTO = null;
        Player p = em.find(Player.class, playerName);
        if (p == null) {
            throw new Exception("No players with that name were found");
        } else {
            TypedQuery<Character> query = em.createQuery("SELECT c FROM Character c JOIN c.player p WHERE p.userName =:playername", Character.class
            );
            query.setParameter("playername", playerName);
            List<Character> resultlist = query.getResultList();

            resultAsDTO = CharacterDTO.getDtos(resultlist);
        }
        return resultAsDTO;
    }

    public List<EquipmentDTO> getEquipmentsForCharacter(int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character character = null;
        List<EquipmentDTO> edtos = new ArrayList<>();
        if (characterID <= 0) {
            throw new Exception("The given character ID should be 1 or higher");
        } else {
            try {
                em.getTransaction().begin();
                character = em.find(Character.class, characterID);
            } finally {
                em.close();
            }
            if (character == null) {
                throw new Exception("There is no such character in the database with the given ID");
            }
        }
        for (Inventory inventory : character.getInventories()) {
            edtos.add(new EquipmentDTO(inventory.getEquipment()));
        }
        return edtos;
    }

    //Needs negative testing
    public String updateHP(int newHPValue, int CharacterId) throws NullPointerException, IllegalArgumentException {
        EntityManager em = emf.createEntityManager();
        Character characterWithNewHp;
        Character character = null;
        character = em.find(Character.class, CharacterId);
        if (!(CharacterId > 0)) {
            throw new NullPointerException("Character ID can not be 0 or less - and it should be a number");
        } else if (character.getMaxHP() < newHPValue) {
            throw new IllegalArgumentException("Characters new HP value must be lower than the Max HP");
        } else {
            try {
                em.getTransaction().begin();

                if (character == null) {
                    throw new NullPointerException("Could not find a character with the given ID number");
                }
                character.setCurrentHP(newHPValue);
                em.merge(character);
                em.getTransaction().commit();
                characterWithNewHp = em.find(Character.class, CharacterId);

            } finally {
                em.close();
            }
        }
        return characterWithNewHp.getCurrentHP() + "";

    }

    public Player getVeryfiedUser(String username, String password) throws AuthenticationException, Exception {
        EntityManager em = emf.createEntityManager();
        Player player;
        if (username == null || password == null) {
            throw new Exception("You must send a given username and password to be verified");
        } else {
            try {
                player = em.find(Player.class, username);
                if (player == null || !player.verifyPassword(password)) {
                    throw new AuthenticationException("Invalid user name or password");
                }
            } finally {
                em.close();
            }
        }
        return player;
    }

    public PlayerDTO getPlayerByName(String playerName) throws Exception {

        EntityManager em = emf.createEntityManager();
        if (playerName == null) {
            throw new Exception("You must send a given username to get a player");
        } else {
            TypedQuery<Player> playerQuery = em.createQuery("SELECT p FROM Player p WHERE p.userName =:name", Player.class
            );
            playerQuery.setParameter("name", playerName);
            Player player = playerQuery.getSingleResult();
            if (player == null) {
                throw new Exception("No characters with that name were found");
            }

            return new PlayerDTO(player);
        }
    }

    public List<PlayerDTO> getPlayers() throws Exception {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Player> playerQuery = em.createQuery("SELECT p FROM Player p", Player.class);
        List<Player> players = playerQuery.getResultList();
        if (players.isEmpty()) {
            throw new Exception("There was no player in the database");
        }
        List<PlayerDTO> playerDTOs = PlayerDTO.getDtos(players);
        return playerDTOs;
    }

    public SkillsDTO getSkillsFromACharacter(int characterID) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character character = null;
        if (characterID <= 0) {
            throw new Exception("ID for character should be 1 or more");
        } else {
            //em.getTransaction().begin();
            character = em.find(Character.class, characterID);

            if (character == null) {
                throw new Exception("Can not find the characters skills by the given ID");
            }
            return new SkillsDTO(character.getSkills());
        }
    }

    public SkillsDTO updateSkillsForCharacter(int characterID, SkillsDTO skillsDTO) throws Exception {
        EntityManager em = emf.createEntityManager();
        Character character = null;
        Character characterNew = null;
        if (characterID <= 0) {
            throw new Exception("ID for character should be 1 or higher");
        } else {

            //em.getTransaction().begin();
            character = em.find(Character.class, characterID);
            if (character == null) {
                throw new Exception("Can not find the characters skills by the given ID");
            }
            character.setSkills(new Skills(skillsDTO.getAnimal_Handling(),
                    skillsDTO.getArcana(), skillsDTO.getAthletics(), skillsDTO.getDeception(),
                    skillsDTO.getHistory(), skillsDTO.getInsight(), skillsDTO.getIntimidation(),
                    skillsDTO.getInvestigation(), skillsDTO.getMedicine(), skillsDTO.getMedicine(),
                    skillsDTO.getPerception(), skillsDTO.getPerformance(), skillsDTO.getPersuasion(),
                    skillsDTO.getReligion(), skillsDTO.getSleight_of_Hand(), skillsDTO.getStealth(),
                    skillsDTO.getSurvival()));
            try {
                em.getTransaction().begin();
                em.merge(character);
                em.getTransaction().commit();
                characterNew = em.find(Character.class, characterID);
            } finally {
                em.close();
            }
            return new SkillsDTO(characterNew.getSkills());
        }
    }

}
