package dtos;

import entities.AbillityScores;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;

public class AbillityScoresDTO {

    private Integer strength;
    private Integer dexterity;
    private Integer constitution;
    private Integer wisdom;
    private Integer intelligence;
    private Integer charisma;

    public AbillityScoresDTO(AbillityScores abilityScores) {
        this.strength = abilityScores.getStrength();
        this.dexterity = abilityScores.getDexterity();
        this.constitution = abilityScores.getConstitution();
        this.wisdom = abilityScores.getWisdom();
        this.intelligence = abilityScores.getIntelligence();
        this.charisma = abilityScores.getCharisma();
    }

    public Integer getStrength() {
        return strength;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public Integer getConstitution() {
        return constitution;
    }

    public Integer getWisdom() {
        return wisdom;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public AbillityScores getDtos(AbillityScoresDTO abiScores) {
        AbillityScores abiScor = new AbillityScores(abiScores.getStrength(), abiScores.getDexterity(), abiScores.getConstitution(), abiScores.getWisdom(), abiScores.getIntelligence(), abiScores.getCharisma());
        return abiScor;
    }

}
