package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "inventory")
    private Equipment equipment;
    @OneToMany(mappedBy = "inventory")
    private Character character;
    private int qty;

    public Inventory() {
    }

    //Adds equipment but if it already exist it adjust quantity by adding og removing the amount <---- (TODO - Test this)
    //If character has quantity for 0 or less the character looses the Equipment any longer in the Inventory
    public void addEquipmentAndQty(Equipment equipment, int qnty) {
        this.equipment = equipment;
        this.qty = qnty;    
        }
       /* int fullQty;
        List<Equipment> tmpList = new ArrayList<>();
        List<Equipment> tmpList2 = new ArrayList<>();
        Equipment equipmentNew = equipment;
        tmpList.addAll(equipments);
        for (Equipment e : tmpList) {
            tmpList2.remove(e);
            if (e.getName().equals(equipmentNew.getName())) {
                this.equipments.remove(equipmentNew);
                fullQty = e.getQty() + equipmentNew.getQty();
                equipmentNew.setQty(fullQty);
                this.equipments.add(equipmentNew);
            } else if (equipment.getQty() >= 0 && tmpList2.isEmpty()) {
                this.equipments.add(equipmentNew);
            }
        }
        if (equipment.getQty() <= 0) {
            equipments.remove(equipmentNew);
        }*/

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
     
  
}
