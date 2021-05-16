
package dtos;

import entities.EquipmentCategory;

public class EquipmentCatagoryDTO {
    private String index;
    private String name;
    private String url;

    public EquipmentCatagoryDTO(EquipmentCategory equipmentCatagory) {
        this.index = equipmentCatagory.getIndex();
        this.name = equipmentCatagory.getName();
        this.url = equipmentCatagory.getUrl();
    }

    public String getName() {
        return name;
    }

    
    
}
