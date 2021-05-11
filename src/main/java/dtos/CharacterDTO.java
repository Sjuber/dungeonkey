
package dtos;
import entities.Character;
import java.util.ArrayList;
import java.util.List;

public class CharacterDTO {


    private AbillityScoresDTO abilityScoreDTO;
    private PlayerDTO playerDTO;
    private InventoryDTO inventoryDTO;
    
    public CharacterDTO(Character character) {
        this.abilityScoreDTO = new AbillityScoresDTO(character.getAbillityScores());
        this.playerDTO = new PlayerDTO(character.getPlayer());
        this.inventoryDTO = new InventoryDTO(character.getInventory());
    }  

    public AbillityScoresDTO getAbilityScoreDTO() {
        return abilityScoreDTO;
    }

    public PlayerDTO getPlayerDTO() {
        return playerDTO;
    }
   
    public static List<CharacterDTO> getDtos(List<Character> characters){
        List<CharacterDTO> cdtos = new ArrayList<>();
        characters.forEach(character->cdtos.add(new CharacterDTO(character)));
        return cdtos;
    }

    public InventoryDTO getInventoryDTO() {
        return inventoryDTO;
    }
    
    
    
    
}
