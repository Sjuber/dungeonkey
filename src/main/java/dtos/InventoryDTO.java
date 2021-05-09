
package dtos;

import entities.Equipment;
import entities.Inventory;
import java.util.Map;
import java.util.TreeMap;


public class InventoryDTO {
    
    private TreeMap<EquipmentDTO, Integer> equipmentsDTOQty = new TreeMap<>();

    public InventoryDTO(Inventory inventory) {
        for (Map.Entry<Equipment, Integer> e : inventory.getEquipmentsNQty().entrySet()) {
            equipmentsDTOQty.put(new EquipmentDTO(e.getKey()),e.getValue());
        }
    }
    
    
}
