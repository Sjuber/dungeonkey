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
        Equipment equipment = new Equipment("Health Potion", 10, 0.3);
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
            //player1.getCharacterList().get(0).getInventory().getEquipmentsNQty().put(equipment,equipment.getQty());
            em.merge(player1);
            em.persist(DM);
            em.persist(player2);
            em.getTransaction().commit();
        
        } finally {
            em.close();
        }
  }

}
