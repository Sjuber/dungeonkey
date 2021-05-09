package entities;

import java.io.Serializable;
import java.util.TreeMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToMany
    private TreeMap<Equipment, Integer> equipmentsNQty = new TreeMap<>();
    @OneToOne
    private Character character;

    public Inventory() {
    }

    public Inventory(TreeMap<Equipment, Integer> equipmentsQty) {
        this.equipmentsNQty = equipmentsQty;
    }

    public TreeMap<Equipment, Integer> getEquipmentsNQty() {
        return equipmentsNQty;
    }

    //Adds equipment but if it already exist it adjust quantity by adding og removing the amount <---- (TODO - Test this)
    //If character has quantity for 0 or less the character looses the Equipment any longer in the Inventory
    public void adjustEquipmentAndQty(Equipment equipment, int qty) {
        int fullQty;
        if (equipmentsNQty.containsKey(equipment)) {
            fullQty = equipmentsNQty.get(equipment) + qty;
            equipmentsNQty.put(equipment, fullQty);
        } else if (qty >= 0) {
            equipmentsNQty.put(equipment, qty);
        }
        if (equipmentsNQty.get(equipment) < 0) {
            equipmentsNQty.remove(equipment);
        }

    }

    public Character getCharacter() {
        return character;
    }

}
