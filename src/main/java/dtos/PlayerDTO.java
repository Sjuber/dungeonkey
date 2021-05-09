
package dtos;

import entities.Player;


class PlayerDTO {

    private String username;
    
    public PlayerDTO(Player player) {
        username = player.getUserName();
    }
    
    
}
