
package dtos;
import entities.Character;

public class CharacterDTO {

    private int id; 
    private AbillityScoresDTO abilityScoreDTO;
    
    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.abilityScoreDTO = new AbillityScoresDTO(character.getAbillityScores());
    }  

    public int getId() {
        return id;
    }
    
}
