package dtos;

import entities.Inventory;

public class InventoryDTO {

    private EquipmentDTO equipmentsDTO;
    private int qty;

    public InventoryDTO(Inventory inventory) {
        this.equipmentsDTO = new EquipmentDTO(inventory.getEquipment());
        this.qty = inventory.getQty();
    }

}
