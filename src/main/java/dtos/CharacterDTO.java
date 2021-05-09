
package dtos;
import entities.Character;
import java.util.ArrayList;
import java.util.List;

public class CharacterDTO {

    //private int id; 
    private AbillityScoresDTO abilityScoreDTO;
    
    public CharacterDTO(Character character) {
    //    this.id = character.getId();
        this.abilityScoreDTO = new AbillityScoresDTO(character.getAbillityScores());
    }  

    /*public int getId() {
        return id;
    }*/

    public AbillityScoresDTO getAbilityScoreDTO() {
        return abilityScoreDTO;
    }
    public static List<CharacterDTO> getDtos(List<Character> characters){
        List<CharacterDTO> cdtos = new ArrayList();
        characters.forEach(character->cdtos.add(new CharacterDTO(character)));
        return cdtos;
    }
    
}
