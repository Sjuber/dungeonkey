package dtos;

import entities.Equipment;

public class EquipmentDTO {

    private String name;
    private String catergory;
    private double weight;

    public EquipmentDTO(Equipment equipment) {
        this.name = equipment.getName();
        this.catergory = equipment.getCategory();
        this.weight = equipment.getWeight();
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getCatergory() {
        return catergory;
    }
    
}
