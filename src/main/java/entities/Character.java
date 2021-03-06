package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;


@Entity
@Table(name = "characters")
public class Character implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer lvl;
    private Integer maxHP;
    private Integer currentHP;
    private Integer ac;
    private Integer speed;
    private String name;
    private String biography;
    private String race;
    private String classs;
    @OneToOne(cascade = CascadeType.ALL)
    private AbillityScores abillityScores;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Skills skills;
    @ManyToOne
    private Player player;
//    @OneToOne(cascade = CascadeType.PERSIST)
//    private Inventory inventory;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "character")
    private List<Inventory> inventory ;

    public Character(Integer lvl, Integer maxHP, Integer currentHP, Integer ac, Integer speed, String name, String biography, String race, String classs, AbillityScores abillityScores, Skills skills) {
        this.lvl = lvl;
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.ac = ac;
        this.speed = speed;
        this.name = name;
        this.biography = biography;
        this.race = race;
        this.classs = classs;
        this.abillityScores = abillityScores;
        abillityScores.setCharacter(this);
        this.inventory = new ArrayList<>();
        this.skills = skills;
        this.skills.setCharacter(this);
    }

    public Character() {
    }

    public Integer getLvl() {
        return lvl;
    }

    public void setLvl(Integer level) {
        this.lvl = level;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(Integer maxHP) {
        this.maxHP = maxHP;
    }

    public Integer getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(Integer currentHP) {
        this.currentHP = currentHP;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getClasss() {
        return classs;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public AbillityScores getAbillityScores() {
        return abillityScores;
    }

    public void setAbillityScores(AbillityScores abillityScores) {
        if (abillityScores != null) {
            this.abillityScores = abillityScores;
            abillityScores.setCharacter(this);
        }
    }

    public void setPlayer(Player player) {
        if (player != null) {
            this.player = player;

        }
    }

    public Player getPlayer() {
        return player;
    }


    public void addInventory(Inventory inventory) {
        if (inventory != null) {
            this.inventory.add(inventory);
            inventory.setCharacter(this);
        }
    }

    public List<Inventory> getInventories() {
        return this.inventory;
    }

    public void setInventory(List<Inventory> inventory) {
        this.inventory = inventory;
    }
    
    

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Character{id=").append(id);
        sb.append(", lvl=").append(lvl);
        sb.append(", maxHP=").append(maxHP);
        sb.append(", currentHP=").append(currentHP);
        sb.append(", ac=").append(ac);
        sb.append(", speed=").append(speed);
        sb.append(", name=").append(name);
        sb.append(", biography=").append(biography);
        sb.append(", race=").append(race);
        sb.append(", classs=").append(classs);
        sb.append(", abillityScores=").append(abillityScores);
        sb.append(", skills=").append(skills);
        sb.append(", player=").append(player);
        sb.append('}');
        return sb.toString();
    }

}
