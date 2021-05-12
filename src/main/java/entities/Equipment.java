package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //makes it possible to have childclasses in one table(? TODO ! Test det lige ) 
public class Equipment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    private String name;
    private int qty;
    private double weight;
//    @ManyToMany(mappedBy = "equipments")
//    private List<Inventory> inventories;
      @ManyToMany(mappedBy = "equipments")
      private List<Character> character = new ArrayList<>();;
    

    public Equipment(String name, int qty, double weight) {
        this.name = name;
        this.qty = qty;
        this.weight = weight;
    }

    public Equipment() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
