//package entities;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToOne;
//
//@Entity
//public class Inventory implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
//    private int id;
////    @ManyToMany
////    @JoinColumn(name = "inventories_equipments")
////    private List<Equipment> equipments = new ArrayList<>();
//    @OneToOne(mappedBy = "inventory")
//    private Character character;
//
//    public Inventory() {
//    }
//
//    //Adds equipment but if it already exist it adjust quantity by adding og removing the amount <---- (TODO - Test this)
//    //If character has quantity for 0 or less the character looses the Equipment any longer in the Inventory
//    public void adjustEquipmentAndQty(Equipment equipment) {
//        int fullQty;
//        List<Equipment> tmpList = new ArrayList<>();
//        List<Equipment> tmpList2 = new ArrayList<>();
//        Equipment equipmentNew = equipment;
//        tmpList.addAll(equipments);
//        for (Equipment e : tmpList) {
//            tmpList2.remove(e);
//            if (e.getName().equals(equipmentNew.getName())) {
//                this.equipments.remove(equipmentNew);
//                fullQty = e.getQty() + equipmentNew.getQty();
//                equipmentNew.setQty(fullQty);
//                this.equipments.add(equipmentNew);
//            } else if (equipment.getQty() >= 0 && tmpList2.isEmpty()) {
//                this.equipments.add(equipmentNew);
//            }
//        }
//        if (equipment.getQty() <= 0) {
//            equipments.remove(equipmentNew);
//        }
//     
//
//    }
//
//    public List<Equipment> getEquipments() {
//        return equipments;
//    }
//
//    public Character getCharacter() {
//        return character;
//    }
//
//    public void setCharacter(Character character) {
//        this.character = character;
//    }
//
//}
