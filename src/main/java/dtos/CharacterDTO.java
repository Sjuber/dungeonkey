package dtos;


import entities.Character;

import java.util.ArrayList;
import java.util.List;


public class CharacterDTO {

    //private int id; 
    private Integer levl;
    private AbillityScoresDTO abilityScoreDTO;
    private PlayerDTO playerDTO;
    private Integer maxHp;
    private Integer currentHP;
    private Integer ac;
    private Integer speed;
    private String name;
    private String biography;
    private String race;
    private String classs;

    public CharacterDTO(Character ch) {
        this.abilityScoreDTO = new AbillityScoresDTO(ch.getAbillityScores());
        this.playerDTO = new PlayerDTO(ch.getPlayer());
        this.maxHp = ch.getMaxHP();
        this.currentHP = ch.getCurrentHP();
        this.ac = ch.getAc();
        this.speed = ch.getSpeed();
        this.name = ch.getName();
        this.biography = ch.getBiography();
        this.race = ch.getRace();
        this.classs = ch.getClasss();
    }

    /*public int getId() {
        return id;
    }*/
    public AbillityScoresDTO getAbilityScoreDTO() {
        return abilityScoreDTO;
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public Integer getMaxHp() {
        return maxHp;
    }

    public Integer getCurrentHP() {
        return currentHP;
    }

    public Integer getAc() {
        return ac;
    }

    public Integer getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getRace() {
        return race;
    }

    public String getClasss() {
        return classs;
    }

    public Integer getLevl() {
        return levl;
    }
    
    

    public static List<CharacterDTO> getDtos(List<Character> characters) {
        List<CharacterDTO> cdtos = new ArrayList();
        characters.forEach(character -> cdtos.add(new CharacterDTO(character)));
        return cdtos;
    }
    

}
