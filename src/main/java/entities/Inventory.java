package entities;

import java.io.Serializable;
import java.util.TreeMap;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;

@Entity
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ElementCollection
    @CollectionTable(name = "equipment_quantity",
            joinColumns = {
                @JoinColumn(name = "equipment.id")})
    @MapKeyColumn(name = "equipment")
    @Column(name = "quantity")
     @ManyToMany
    private TreeMap<String, Integer> equipmentsNQty = new TreeMap<>();
    @OneToOne
    private Character character;

    public Inventory() {
    }

    public Inventory(TreeMap<String, Integer> equipmentsQty) {
        this.equipmentsNQty = equipmentsQty;
    }

    public TreeMap<String, Integer> getEquipmentsNQty() {
        return equipmentsNQty;
    }

    //Adds equipment but if it already exist it adjust quantity by adding og removing the amount <---- (TODO - Test this)
    //If character has quantity for 0 or less the character looses the Equipment any longer in the Inventory
    public void adjustEquipmentAndQty(Equipment equipment, int qty) {
        int fullQty;
        if (equipmentsNQty.containsKey(equipment.getName())) {
            fullQty = equipmentsNQty.get(equipment.getName()) + qty;
            equipmentsNQty.put(equipment.getName(), fullQty);
        } else if (qty >= 0) {
            equipmentsNQty.put(equipment.getName(), qty);
        }
        if (equipmentsNQty.get(equipment.getName()) < 0) {
            equipmentsNQty.remove(equipment.getName());
        }

    }

    public Character getCharacter() {
        return character;
    }

}
