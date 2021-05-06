package utils;


import entities.Role;
import entities.User;
import entities.Player;
import entities.Character;
import entities.AbillityScores;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class populator {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

// role section
    
    Player player = new Player("Nikolaj", "Hamster16");
    Player DM = new Player("Cathrine", "Portraet11");
    Player both = new Player("Jens", "Skeletor69");

//    if(DM.getPassword().equals("Portraet11")||player.getPassword().equals("Hamster16")||both.getPassword().equals("Skeletor69"))
//      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Role playerRole = new Role("player");
    Role DMRole = new Role("dungeonmaster");
    player.addRole(playerRole);
    DM.addRole(DMRole);
    both.addRole(playerRole);
    both.addRole(DMRole);
    em.persist(playerRole);
    em.persist(DMRole);
    em.persist(player);
    em.persist(DM);
    em.persist(both);
    
    System.out.println("PW: " + player.getPassword());
    System.out.println("Testing user with OK password: " + player.verifyPassword("Hamster16"));
    System.out.println("Testing user with wrong password: " + player.verifyPassword("Nollerik510"));
    System.out.println("Created TEST Users");
    AbillityScores abiSco1 = new AbillityScores(18,8,14,12,14,10);
    Character ch1 = new Character(5,104, 85, 17, 30, "Damascus", "He was a valiant paladin.", "orc", "paladin",abiSco1);
    em.persist(ch1);
    em.getTransaction().commit();
    
   
  }

}
