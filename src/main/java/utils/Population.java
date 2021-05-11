package utils;

import entities.Role;
import entities.Player;
import entities.Character;
import entities.AbillityScores;
import entities.Equipment;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Population {

    public static void populate() {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Player player1 = new Player("Nikolaj", "Hamster16");
        Player DM = new Player("Cathrine", "Portraet11");
        Player player2 = new Player("Jens", "Skeletor69");
        AbillityScores abiSco1 = new AbillityScores(18, 8, 14, 12, 14, 10);
        Character ch1 = new Character(5, 104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin", abiSco1);
        Equipment equipment = new Equipment("Helm Of Glory", 1, 1.5);
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
            em.persist(equipment);
            player1.addCharacter(ch1);
            em.persist(ch1);
            em.persist(player1);
            em.persist(DM);
            em.persist(player2);
            em.getTransaction().commit();
            System.out.println("");
        } finally {
            em.close();
        }
    }
    
    /*public static void main(String[] args) {
        test();
    }

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

    }*/
}
