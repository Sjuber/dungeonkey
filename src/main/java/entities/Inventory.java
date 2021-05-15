package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

@Entity
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Equipment equipment;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Character character;
    private int qty;

    public Inventory() {
    }

    public Inventory(Equipment equipment, int qty) {
        this.equipment = equipment;
        equipment.addInventory(this);
        this.qty = qty;
    }

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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    
    public Character getCharacter() {
        return character;
    }

    public int getQty() {
        return qty;
    }

}
