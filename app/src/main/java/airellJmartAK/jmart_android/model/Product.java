package airellJmartAK.jmart_android.model;

/**
 * model as a Product similar to backend
 * @author Airell Ramadhan B
 */

public class Product extends Serializable{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    public String toString(){
        return name;
    }
}
