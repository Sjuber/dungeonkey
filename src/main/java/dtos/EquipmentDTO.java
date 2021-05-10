package dtos;

import entities.Equipment;

public class EquipmentDTO {

    private String name;
    private int qty;
    private double weight;

    public EquipmentDTO(Equipment equipment) {
        this.name = equipment.getName();
        this.qty = equipment.getQty();
        this.weight = equipment.getWeight();
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public double getWeight() {
        return weight;
    }
    
    
}