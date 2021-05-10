
package dtos;

import entities.Equipment;
import entities.Inventory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class InventoryDTO {
    
    private List<EquipmentDTO> equipmentsDTO;

    public InventoryDTO(Inventory inventory) {
        equipmentsDTO = new ArrayList<>();
        inventory.getEquipments().forEach(e -> 
                {equipmentsDTO.add(new EquipmentDTO(e));
                });
    }

    public List<EquipmentDTO> getEquipmentsDTOQty() {
        return equipmentsDTO;
    }
    
    
}
