/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


/**
 *
 * @author SJUBE
 */
@Entity
public class AbillityScores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer wisdom;
    private Integer intelligence;
    private Integer charisma;
    @OneToOne(mappedBy = "abillityScores")
    private Character character;

        public AbillityScores( Integer strength, Integer dexterity, Integer constitution, Integer wisdom, Integer intelligence, Integer charisma) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.wisdom = wisdom;
        this.intelligence = intelligence;
        this.charisma = charisma;
 
    }

    public AbillityScores() {
    }
        
    
    public Integer getId() {
        return id;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public void setConstitution(Integer constitution) {
        this.constitution = constitution;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public void setWisdom(Integer wisdom) {
        this.wisdom = wisdom;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public void setCharacter(Character character) {
        this.character = character;
        character.setAbillityScores(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AbillityScores{id=").append(id);
        sb.append(", strength=").append(strength);
        sb.append(", dexterity=").append(dexterity);
        sb.append(", constitution=").append(constitution);
        sb.append(", wisdom=").append(wisdom);
        sb.append(", intelligence=").append(intelligence);
        sb.append(", charisma=").append(charisma);
        sb.append('}');
        return sb.toString();
    }
}
