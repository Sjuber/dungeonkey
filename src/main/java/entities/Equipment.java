package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //makes it possible to have childclasses in one table(? TODO ! Test det lige ) 
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String name;
    private double weight;
    private String category; // To be developed using polyphormism instead
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equipment")
    private List<Inventory> inventories = new ArrayList<>();

    public Equipment(String name, double weight, String category) {
        this.inventories = new ArrayList<>();
        this.name = name;
        this.weight = weight;
        this.category = category;
    }

    public Equipment() {
        this.inventories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCategory() {
        return category;
    }
    
    public void addInventory(Inventory inventory) {
        if (inventory != null) {
            this.inventories.add(inventory);
            inventory.setEquipment(this);
        }
    }

    public List<Inventory> getInventories() {
        return inventories;
    }
    
    


}
