
package dtos;

import entities.EquipmentCategory;

public class EquipmentCatagoryDTO {
    private String index;
    private String name;
    private String url;

    public EquipmentCatagoryDTO(EquipmentCategory equipmentCatagory) {
        this.index = index;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    
    
}
