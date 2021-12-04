package airellJmartAK.jmart_android.model;

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
        return "name: " + this.name + "\n" + "Weight: " + weight + "\n" + "ConditionUsed: " + this.conditionUsed + "\n" + "price: " +
                price + "\n" + "Category: " + this.category + "\n" + "Discount: " + discount + "accountId: " + accountId;
    }
}
