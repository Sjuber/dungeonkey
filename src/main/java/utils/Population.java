package utils;

import entities.Role;
import entities.Player;
import entities.Character;
import entities.AbillityScores;
import entities.Equipment;
import entities.Inventory;
import entities.Skills;
import facades.CharacterFacade;
import facades.dnd5eapiFacade;
import java.io.IOException;
import java.util.Random;
import utils.JsonReader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Population {

    private static final JsonReader jsonReader = new JsonReader();
    static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    private static final dnd5eapiFacade facade = dnd5eapiFacade.getdnd5api(emf);
//    CharacterFacade facade = CharacterFacade.getCharacterFacade(emf);

    public static void populate() throws IOException, Exception {

        EntityManager em = emf.createEntityManager();
        facade.fillingUpDBWithEquipments(facade.getEquipmentDTOsFromAPI(jsonReader));
        Player player1 = new Player("Nikolaj", "Hamster16");
        Player DM = new Player("Cathrine", "Portraet11");
        Player player2 = new Player("Jens", "Skeletor69");
        AbillityScores abiSco11 = new AbillityScores(18, 8, 14, 12, 14, 10);
        AbillityScores abiSco12 = new AbillityScores(18, 8, 14, 12, 14, 10);
        AbillityScores abiSco13 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Random randi = new Random(0);
        Skills skils1 = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Skills skils2 = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Skills skils3 = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
        Character ch1 = new Character(5, 150, 85, 10, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco11, skils1);
        Character ch2 = new Character(5, 104, 85, 17, 60, "Heidi", "He was a valiant druid.", "human", "druid", abiSco12, skils2);
        Character ch3 = new Character(5, 160, 85, 17, 30, "Varanoth", "He was a valiant fighter.", "orc", "fighter", abiSco13, skils3);
        Equipment e1 = em.find(Equipment.class, "club");
        Equipment e2 = em.find(Equipment.class, "candle");
        Equipment e3 = em.find(Equipment.class, "camel");
        Equipment e4 = em.find(Equipment.class, "bucket");
        ch1.addInventory(new Inventory(e1, 1));
        ch2.addInventory(new Inventory(e2, 1));
        ch2.addInventory(new Inventory(e3, 1));
        ch3.addInventory(new Inventory(e4, 1));
        e1.addInventory(ch1.getInventories().get(0));
        e2.addInventory(ch2.getInventories().get(0));
        e3.addInventory(ch2.getInventories().get(1));
        e4.addInventory(ch3.getInventories().get(0));
        //ch1.getInventory().addEquipmentAndQty(equipment, 1);
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
            //em.persist(equipment);
            player1.addCharacter(ch1);
            player1.addCharacter(ch2);
            player2.addCharacter(ch3);
            //em.persist(ch1);
            em.persist(player1);
            em.persist(DM);
            em.persist(player2);
            em.getTransaction().commit();
            System.out.println("");
        } finally {
            em.close();
        }
    }

    /* public static void populateWithEquipment() {


    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
        Player player1 = new Player("Nikolaj", "Hamster16");
        Player DM = new Player("Cathrine", "Portraet11");
        Player player2 = new Player("Jens", "Skeletor69");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Random randi = new Random(0);
        Skills skils = new Skills(randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5), randi.nextInt(5));
       Character ch1 = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1, skils);
        Equipment equipment = new Equipment("Helm Of Glory", 1.5);
        ch1.addInventory(new Inventory(equipment, 1));
        equipment.addInventory(ch1.getInventories().get(0));

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
//            em.persist(equipment);
            em.persist(player1);
            player1.addCharacter(ch1);
            //em.persist(ch1);
            em.merge(player1);
            em.persist(DM);
            em.persist(player2);
            em.getTransaction().commit();
            System.out.println("");
        } finally {
            em.close();
        }
    }*/
    public static void main(String[] args) throws Exception {
        // populateWithEquipment();
        populate();

    }
    /*
    public static void test() {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager emDelete = emf.createEntityManager();
        try {
            emDelete.getTransaction().begin();
            emDelete.createQuery("DELETE FROM Character").executeUpdate();
            emDelete.createQuery("DELETE FROM Player").executeUpdate();
            emDelete.createQuery("DELETE FROM Equipment").executeUpdate();
            emDelete.createQuery("DELETE FROM Role").executeUpdate();
            emDelete.getTransaction().commit();
        } finally {
            emDelete.close();
        }
        EntityManager em = emf.createEntityManager();
        Player player1 = new Player("Nikolaj", "Hamster16");
        //Player DM = new Player("Cathrine", "Portraet11");
        //Player player2 = new Player("Jens", "Skeletor69");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Character ch1 = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1);
        //Equipment equipment = new Equipment("Helm Of Glory", 1, 1.5);
        try {
            em.getTransaction().begin();
            //Role playerRole = new Role("player");
            //   Role DMRole = new Role("dungeonmaster");
           // player1.addRole(playerRole);
            //   DM.addRole(DMRole);
            //   player2.addRole(playerRole);
            //both.addRole(DMRole); // MAN KAN GODT HAVE BEGGE ROLLER CATHRINE !!!
           // em.persist(playerRole);
            //  em.persist(DMRole);
            //  em.persist(equipment);
            player1.addCharacter(ch1);
            em.persist(ch1);
            em.persist(player1);
            // em.persist(DM);
            // em.persist(player2);
            em.getTransaction().commit();
            System.out.println("");
        } finally {
            em.close();
        }
     */

}
