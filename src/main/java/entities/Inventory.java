package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    @JoinColumn(name = "inventories")
    private List<Equipment> equipments;
    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "inventory")
    private Character character;

    
    
    public Inventory() {
        this.equipments = new ArrayList<>();
    }

    //Adds equipment but if it already exist it adjust quantity by adding og removing the amount <---- (TODO - Test this)
    //If character has quantity for 0 or less the character looses the Equipment any longer in the Inventory
    public void adjustEquipmentAndQty(Equipment equipment, int newQty) {
        int fullQty;
        if (equipments.contains(equipment)) {
            equipments.remove(equipment);
            fullQty = equipment.getQty() + newQty;
            equipment.setQty(fullQty);
            equipments.add(equipment);
        } else if (newQty >= 0) {
            equipment.setQty(newQty);
            equipments.add(equipment);
            equipment.setQty(newQty);
        }
        if (equipment.getQty() < 0) {
            equipments.remove(equipment);
        }

    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

}
