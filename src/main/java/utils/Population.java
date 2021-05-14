package utils;


import entities.Role;
import entities.User;
import entities.Player;
import entities.Character;
import entities.AbillityScores;
import entities.Skills;
import java.util.Random;

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
            //both.addRole(DMRole);
            em.persist(playerRole);
            em.persist(DMRole);
            em.persist(player1);
            player1.addCharacter(ch1);
            em.merge(player1);
            em.persist(DM);
            em.persist(player2);
            em.getTransaction().commit();
            System.out.println("Jeg er her nu");
        } finally {
            em.close();
        }
  }

}
