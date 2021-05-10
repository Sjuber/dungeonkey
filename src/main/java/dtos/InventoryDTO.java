
package dtos;

import entities.Equipment;
import entities.Inventory;
import java.util.Map;
import java.util.TreeMap;


public class InventoryDTO {
    
    private TreeMap<String, Integer> equipmentsDTOQty = new TreeMap<>();

    public InventoryDTO(Inventory inventory) {
        for (Map.Entry<String, Integer> e : inventory.getEquipmentsNQty().entrySet()) {
            equipmentsDTOQty.put(e.getKey(),e.getValue());
        }
    }

    public TreeMap<String, Integer> getEquipmentsDTOQty() {
        return equipmentsDTOQty;
    }
    
    
}
