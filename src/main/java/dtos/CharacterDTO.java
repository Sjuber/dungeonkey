
package dtos;
import entities.Character;
import java.util.ArrayList;
import java.util.List;

public class CharacterDTO {

    private Integer lvl;
    private Integer maxHP;
    private Integer currentHP;
    private Integer ac;
    private Integer speed;
    private String name;
    private String biography;
    private String race;
    private String classs;
    private AbillityScoresDTO abilityScoreDTO;
    private PlayerDTO playerDTO;
    private List<InventoryDTO> inventoryDTO;
    
    public CharacterDTO(Character character) {
        this.abilityScoreDTO = new AbillityScoresDTO(character.getAbillityScores());
        this.playerDTO = new PlayerDTO(character.getPlayer());
        inventoryDTO = new ArrayList<>();
        character.getInventories().forEach(inventory -> this.inventoryDTO.add(new InventoryDTO(inventory)));
        this.lvl = character.getLvl();
        this.maxHP = character.getMaxHP();
        this.ac = character.getAc();
        this.speed = character.getSpeed();
        this.name = character.getName();
        this.biography = character.getBiography();
        this.race = character.getRace();
        this.classs = character.getClasss();
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
   
    public static List<CharacterDTO> getDtos(List<Character> characters){
        List<CharacterDTO> cdtos = new ArrayList<>();
        characters.forEach(character->cdtos.add(new CharacterDTO(character)));
        return cdtos;
    }
    
    
}
