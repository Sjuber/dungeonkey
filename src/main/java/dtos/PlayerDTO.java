
package dtos;

import entities.Player;
import java.util.ArrayList;
import java.util.List;


public class PlayerDTO {

    private String username;
    private String password;
    
    public PlayerDTO(Player player) {
        username = player.getUserName();
        password = player.getPassword();
    }

    public PlayerDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static List<PlayerDTO> getDtos(List<Player> players) {
        List<PlayerDTO> cdtos = new ArrayList();
        players.forEach(player -> {cdtos.add(new PlayerDTO(player));});
        return cdtos;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PlayerDTO{username=").append(username);
        //sb.append(", password=").append(password);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
