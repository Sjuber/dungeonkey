package dtos;

import entities.Character;

import java.util.ArrayList;
import java.util.List;

public class CharacterDTO {
  
    private int levl;
    private int maxHP;
    private int currentHP;
    private int ac;
    private int speed;
    private String name;
    private String biography;
    private String race;
    private String classs;
    private AbillityScoresDTO abilityScoreDTO;
    private SkillsDTO skillsDTO;
    private PlayerDTO playerDTO;
    private List<InventoryDTO> inventoryDTO;

    public CharacterDTO(Character ch) {
        this.levl = ch.getLvl();
        this.abilityScoreDTO = new AbillityScoresDTO(ch.getAbillityScores());
        this.inventoryDTO = new ArrayList<>();
        ch.getInventories().forEach(inventory -> this.inventoryDTO.add(new InventoryDTO(inventory)));
        this.skillsDTO = new SkillsDTO(ch.getSkills());
        this.playerDTO = new PlayerDTO(ch.getPlayer());
        this.maxHP = ch.getMaxHP();
        this.currentHP = ch.getCurrentHP();
        this.ac = ch.getAc();
        this.speed = ch.getSpeed();
        this.name = ch.getName();
        this.biography = ch.getBiography();
        this.race = ch.getRace();
        this.classs = ch.getClasss();
    }

    public CharacterDTO() {
    }

    public SkillsDTO getSkillsDTO() {
        return skillsDTO;
    }

    public AbillityScoresDTO getAbilityScoreDTO() {
        return abilityScoreDTO;
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }

    public List<InventoryDTO> getInventoryDTO() {
        return inventoryDTO;
    }
   
    public Integer getMaxHp() {
        return maxHP;
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
        characters.forEach(character -> {cdtos.add(new CharacterDTO(character));});
        return cdtos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CharacterDTO{levl=").append(levl);
        sb.append(", abilityScoreDTO=").append(abilityScoreDTO);
        sb.append(", skillsDTO=").append(skillsDTO);
        sb.append(", maxHp=").append(maxHP);
        sb.append(", currentHP=").append(currentHP);
        sb.append(", ac=").append(ac);
        sb.append(", speed=").append(speed);
        sb.append(", name=").append(name);
        sb.append(", biography=").append(biography);
        sb.append(", race=").append(race);
        sb.append(", classs=").append(classs);
        sb.append('}');
        return sb.toString();
    }



}
